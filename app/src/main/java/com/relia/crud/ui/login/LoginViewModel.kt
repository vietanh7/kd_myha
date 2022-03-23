package com.relia.crud.ui.login

import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.relia.crud.R
import com.relia.crud.data.auth.AuthRepository
import com.relia.crud.data.remote.NetworkErrorResponse
import com.relia.crud.utils.Constant.TOKEN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel

class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences, private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

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
                sharedPreferences.edit().putString(TOKEN_KEY, result.token).apply()
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

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
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
}