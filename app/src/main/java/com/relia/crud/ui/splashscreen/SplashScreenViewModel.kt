package com.relia.crud.ui.splashscreen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.relia.crud.Constant.TOKEN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    fun getToken(): String? = sharedPreferences.getString(TOKEN_KEY, "")
}