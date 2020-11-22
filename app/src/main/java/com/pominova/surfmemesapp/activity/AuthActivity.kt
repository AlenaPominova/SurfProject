package com.pominova.surfmemesapp.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pominova.surfmemesapp.R
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes

const val EMPTY_FIELD_ERROR = "Поле не может быть пустым"

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        val signInBtn = findViewById<Button>(R.id.sign_in_btn)
        val loginTextField = findViewById<TextFieldBoxes>(R.id.login_tfb)
        val passwordTextField = findViewById<TextFieldBoxes>(R.id.password_tfb)

        signInBtn.setOnClickListener { v -> run {
            validateLogin(loginTextField.text, loginTextField!!)
            validatePassword(passwordTextField.text, passwordTextField!!)
        } }
    }

    private fun validateLogin(login: String, tfb: TextFieldBoxes) {
        if (login.isEmpty()) {
            tfb.setError(EMPTY_FIELD_ERROR)
        }
    }

    private fun validatePassword(password: String, tfb: TextFieldBoxes) {
        if (password.isEmpty()) {
            tfb.setError(EMPTY_FIELD_ERROR)
        }
    }
}
