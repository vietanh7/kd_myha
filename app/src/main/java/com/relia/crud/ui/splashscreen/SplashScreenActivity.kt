package com.relia.crud.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.relia.crud.ui.login.LoginActivity
import com.relia.crud.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (TextUtils.isEmpty(viewModel.getToken()))
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
        else startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        finish()
    }
}