package com.relia.crud.ui.register

import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.relia.crud.R
import com.relia.crud.data.auth.AuthRepository
import com.relia.crud.data.auth.User
import com.relia.crud.data.remote.NetworkErrorResponse
import com.relia.crud.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel

class RegisterViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences, private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<User>()
    val registerResult: LiveData<User> = _registerResult
    private val _registerResultFailed = MutableLiveData<String>()
    val registerResultFailed: LiveData<String> = _registerResultFailed

    fun register(email: String, password: String) {
        authRepository.register(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.data != null)
                    _registerResult.value = result.data!!
            }) { error ->
                if (error is HttpException) {
                    val errorMessage: NetworkErrorResponse = Gson().fromJson(
                        error.response()?.errorBody()?.string(),
                        object : TypeToken<NetworkErrorResponse>() {}.type
                    )
                    _registerResultFailed.value = errorMessage.email?.get(0)
                }
            }
    }

    fun registerDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult
    private val _loginResultFailed = MutableLiveData<String>()
    val loginResultFailed: LiveData<String> = _loginResultFailed

    fun login(email: String, password: String) {
        authRepository.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                _loginResult.value = result.token
                sharedPreferences.edit().putString(Constant.TOKEN_KEY, result.token).apply()
            }) { error ->
                if (error is HttpException) {
                    val errorMessage: NetworkErrorResponse = Gson().fromJson(
                        error.response()?.errorBody()?.string(),
                        object : TypeToken<NetworkErrorResponse>() {}.type
                    )
                    _loginResultFailed.value = errorMessage.error.toString()
                }
            }
    }
}