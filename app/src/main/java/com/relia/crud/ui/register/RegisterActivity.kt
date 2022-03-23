package com.relia.crud.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.relia.crud.R
import com.relia.crud.databinding.ActivityLoginBinding
import com.relia.crud.ui.main.MainActivity
import com.relia.crud.utils.ViewUtils.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val register = binding.login
        val loading = binding.loading

        register.text = getString(R.string.register)
        binding.btnRegister.visibility = GONE

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            // disable register button unless both username / password is valid
            register.isEnabled = registerState.isDataValid

            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, {
            loading.visibility = GONE
            it.email?.let { it1 -> registerViewModel.login(it1, password.text.toString()) }
        })

        registerViewModel.registerResultFailed.observe(this, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = GONE
            Toast.makeText(applicationContext, loginResult, Toast.LENGTH_SHORT).show()
        })

        registerViewModel.loginResult.observe(this@RegisterActivity, {
            loading.visibility = GONE
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        })
        registerViewModel.loginResultFailed.observe(this@RegisterActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = GONE
            Toast.makeText(applicationContext, loginResult, Toast.LENGTH_SHORT).show()
        })
        username.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                registerViewModel.register(username.text.toString(), password.text.toString())
            }
        }
    }
}

