package com.pominova.surfmemesapp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pominova.surfmemesapp.R
import com.pominova.surfmemesapp.model.AuthRequest
import com.pominova.surfmemesapp.model.AuthResponse
import com.pominova.surfmemesapp.service.NetworkService
import com.pominova.surfmemesapp.util.AppConstant.APP_REFERENCES
import com.pominova.surfmemesapp.util.AppConstant.FIRST_NAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.ID_FIELD
import com.pominova.surfmemesapp.util.AppConstant.LAST_NAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.PROGRESS_BUTTON_DELAY
import com.pominova.surfmemesapp.util.AppConstant.TOKEN_FIELD
import com.pominova.surfmemesapp.util.AppConstant.USERNAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.USER_DESCRIPTION_FIELD
import com.pominova.surfmemesapp.util.ProgressButtonUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes
import java.util.*
import kotlin.concurrent.schedule


class AuthActivity : AppCompatActivity() {
    private lateinit var loginTextField: TextFieldBoxes
    private lateinit var passwordTextField: TextFieldBoxes
    private lateinit var loginEditText: ExtendedEditText
    private lateinit var passwordEditText: ExtendedEditText
    private lateinit var progressButtonHelper: ProgressButtonUtil
    private lateinit var signInBtn: View
    private lateinit var login: String
    private lateinit var password: String
    private var isLoginValid = true
    private var isPasswordValid = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        loginTextField = findViewById(R.id.login_tfb)
        passwordTextField = findViewById(R.id.password_tfb)

        loginEditText = findViewById(R.id.login_et)
        passwordEditText = findViewById(R.id.password_et)

        setChangeListeners(loginTextField, passwordTextField, passwordEditText)

        signInBtn = findViewById(R.id.enter_button)
        progressButtonHelper = ProgressButtonUtil(signInBtn, getText(R.string.enter_btn_text).toString())

        isLoginValid = true
        isPasswordValid = true

        signInBtn.setOnClickListener {
            run {
                login = loginEditText.text.toString()
                password = passwordEditText.text.toString()
                isLoginValid = validateInput(login, loginTextField)
                isPasswordValid = validateInput(password, passwordTextField)

                if (isLoginValid && isPasswordValid) {
                    loginUser(this, AuthRequest(login, password))
                }

            }
        }
    }

    private fun setChangeListeners(loginTextField: TextFieldBoxes, passwordTextField: TextFieldBoxes,
                                   passwordEditText: ExtendedEditText) {
        loginTextField.setSimpleTextChangeWatcher { newText: String, isError: Boolean ->
            onTextChanged(loginTextField, newText, isError) }

        passwordTextField.setSimpleTextChangeWatcher { newText: String, isError: Boolean ->
            onTextChanged(passwordTextField, newText, isError) }
        passwordEditText.setOnFocusChangeListener { _, focused ->
            onFocusChanged(passwordTextField, focused) }

        passwordTextField.endIconImageButton.setOnClickListener {
            onShowHidePasswordClick(passwordTextField, passwordEditText)
        }
    }

    private fun onTextChanged(textFieldBoxes: TextFieldBoxes, theNewText: String, isError: Boolean) {
        if (isError && (theNewText.isNotEmpty())) {
            textFieldBoxes.removeError()
        }
    }

    private fun onFocusChanged(textBox: TextFieldBoxes, focused: Boolean) {
        if (focused) {
            textBox.helperText = getText(R.string.password_length_hint).toString()
        } else {
            textBox.helperText = getText(R.string.empty_helper_message).toString()
        }
    }

    private fun onShowHidePasswordClick(textFieldBoxes: TextFieldBoxes,
                                        extendedEditText: ExtendedEditText) {
        if (textFieldBoxes.endIconResourceId == R.drawable.ic_show) {
            textFieldBoxes.setEndIcon(R.drawable.ic_hide)
            extendedEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            textFieldBoxes.setEndIcon(R.drawable.ic_show)
            extendedEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    private fun validateInput(input: String, tfb: TextFieldBoxes): Boolean {
        if (input.isEmpty()) {
            tfb.setError(getText(R.string.empty_filed_error).toString(), true)
            return false
        }
        return true
    }

    private fun loginUser(activity: Activity,
                          authRequest: AuthRequest) {
        val authLayout: LinearLayout = findViewById(R.id.auth_layout)
        progressButtonHelper.activateButton()

        Timer().schedule(PROGRESS_BUTTON_DELAY) {
            NetworkService.getInstance()
                .networkAPI
                .login(authRequest)
                .enqueue(object : Callback<AuthResponse> {

                    override fun onResponse(call: Call<AuthResponse>,
                                            response: Response<AuthResponse>) {
                        saveResponseToSharedPref(activity, response.body()!!)

                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        val wrongCredsSB = Snackbar.make(
                            authLayout,
                            getText(R.string.wrong_auth_data_error).toString(),
                            Snackbar.LENGTH_LONG
                        )
                        val sbView = wrongCredsSB.view
                        sbView.setBackgroundResource(R.color.colorError);
                        wrongCredsSB.show()
                    }
                })
            runOnUiThread(progressButtonHelper::finishButton)
        }
    }

    private fun saveResponseToSharedPref(activity: Activity, response: AuthResponse) {
        val sharedPref =
            activity.getSharedPreferences(APP_REFERENCES, Context.MODE_PRIVATE) ?: return
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
