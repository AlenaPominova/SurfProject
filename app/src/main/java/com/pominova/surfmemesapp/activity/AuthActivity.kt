package com.pominova.surfmemesapp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.pominova.surfmemesapp.R
import com.pominova.surfmemesapp.model.AuthRequest
import com.pominova.surfmemesapp.model.AuthResponse
import com.pominova.surfmemesapp.service.NetworkService
import com.pominova.surfmemesapp.util.AppConstant.APP_REFERENCES
import com.pominova.surfmemesapp.util.AppConstant.EMPTY_FIELD_ERROR
import com.pominova.surfmemesapp.util.AppConstant.FIRST_NAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.ID_FIELD
import com.pominova.surfmemesapp.util.AppConstant.LAST_NAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.TOKEN_FIELD
import com.pominova.surfmemesapp.util.AppConstant.USERNAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.USER_DESCRIPTION_FIELD
import com.pominova.surfmemesapp.util.AppConstant.WRONG_AUTH_DATA_ERROR
import kotlinx.android.synthetic.main.auth_activity.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes


class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        val signInBtn: Button = findViewById(R.id.sign_in_btn)
        val loginTextField: TextFieldBoxes = findViewById(R.id.login_tfb)
        val passwordTextField: TextFieldBoxes = findViewById(R.id.password_tfb)
        var login: String
        var password: String
        var isLoginCorrect = true
        var isPasswordCorrect = true

        signInBtn.setOnClickListener { v -> run {
            login = loginTextField.extended_edit_text_login.text.toString()
            password = passwordTextField.extended_edit_text_password.text.toString()
            isLoginCorrect = validateLogin(login, loginTextField)
            isPasswordCorrect = validatePassword(password, passwordTextField)
            if (isLoginCorrect && isPasswordCorrect) {
                loginUser(this, AuthRequest(login, password))
            }

        } }
    }

    private fun validateLogin(login: String, tfb: TextFieldBoxes): Boolean {
        if (login.isEmpty()) {
            tfb.setError(EMPTY_FIELD_ERROR, true)
            return false
        }
        return true
    }

    private fun validatePassword(password: String, tfb: TextFieldBoxes): Boolean {
        if (password.isEmpty()) {
            tfb.setError(EMPTY_FIELD_ERROR, true)
            return false
        }
        return true
    }

    private fun loginUser(activity: Activity, authRequest: AuthRequest) {
        val authLayout: LinearLayout = findViewById(R.id.auth_layout)
        NetworkService.getInstance()
            .jsonApi
            .login(authRequest)
            .enqueue(object : Callback<AuthResponse> {

                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    saveResponseToSHaredPref(activity, response.body()!!)

                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    val wrongCredsSB = Snackbar.make(
                        authLayout,
                        WRONG_AUTH_DATA_ERROR,
                        Snackbar.LENGTH_LONG
                    )
                    val sbView = wrongCredsSB.view
                    sbView.setBackgroundColor(ContextCompat.getColor(activity.applicationContext, R.color.colorError));
                    wrongCredsSB.show()
                }
            })
    }

    private fun saveResponseToSHaredPref(activity: Activity, response: AuthResponse) {
        val sharedPref = activity.getSharedPreferences(APP_REFERENCES, Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(TOKEN_FIELD, response.accessToken)
            putInt(ID_FIELD, response.userInfo!!.id)
            putString(USERNAME_FIELD, response.userInfo!!.username)
            putString(FIRST_NAME_FIELD, response.userInfo!!.firstName)
            putString(LAST_NAME_FIELD, response.userInfo!!.lastName)
            putString(USER_DESCRIPTION_FIELD, response.userInfo!!.userDescription)
            commit()
        }
    }
}
