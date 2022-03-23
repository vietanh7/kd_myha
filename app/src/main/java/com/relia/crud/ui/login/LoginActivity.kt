package com.relia.crud.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.relia.crud.databinding.ActivityLoginBinding
import com.relia.crud.ui.main.MainActivity
import com.relia.crud.ui.register.RegisterActivity
import com.relia.crud.utils.ViewUtils.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import android.graphics.Paint




@AndroidEntryPoint

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading


        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, {
            loading.visibility = View.GONE
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        })
        loginViewModel.loginResultFailed.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            showLoginFailed(loginResult)
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
        binding.btnRegister.paintFlags = binding.btnRegister.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

