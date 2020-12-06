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
import com.pominova.surfmemesapp.util.AppConstant.EMPTY_FIELD_ERROR
import com.pominova.surfmemesapp.util.AppConstant.EMPTY_HELPER_MESSAGE
import com.pominova.surfmemesapp.util.AppConstant.ENTER_MESSAGE
import com.pominova.surfmemesapp.util.AppConstant.FIRST_NAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.ID_FIELD
import com.pominova.surfmemesapp.util.AppConstant.LAST_NAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.PASSWORD_LENGTH_HELPER_MESSAGE
import com.pominova.surfmemesapp.util.AppConstant.PROGRESS_BUTTON_DELAY
import com.pominova.surfmemesapp.util.AppConstant.TOKEN_FIELD
import com.pominova.surfmemesapp.util.AppConstant.USERNAME_FIELD
import com.pominova.surfmemesapp.util.AppConstant.USER_DESCRIPTION_FIELD
import com.pominova.surfmemesapp.util.AppConstant.WRONG_AUTH_DATA_ERROR
import com.pominova.surfmemesapp.util.ProgressButtonUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes
import java.util.*
import kotlin.concurrent.timerTask


class AuthActivity : AppCompatActivity() {
    private val loginTextField: TextFieldBoxes = findViewById(R.id.login_tfb)
    private val passwordTextField: TextFieldBoxes = findViewById(R.id.password_tfb)
    private val loginEditText: ExtendedEditText = findViewById(R.id.login_et)
    private val passwordEditText: ExtendedEditText = findViewById(R.id.password_et)
    private val signInBtn: View = findViewById(R.id.enter_button)
    private val progressButtonHelper = ProgressButtonUtil(signInBtn, ENTER_MESSAGE)

    private var login: String = ""
    private var password: String = ""
    private var isLoginValid: Boolean = true
    private var isPasswordValid: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        setChangeListeners(loginTextField, passwordTextField, passwordEditText)

        signInBtn.setOnClickListener { view -> run {
            login = loginEditText.text.toString()
            password = passwordEditText.text.toString()
            isLoginValid = validateInput(login, loginTextField)
            isPasswordValid = validateInput(password, passwordTextField)

            if (isLoginValid && isPasswordValid) {
                loginUser(this, progressButtonHelper, AuthRequest(login, password))
            }

        } }
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
            textBox.helperText = PASSWORD_LENGTH_HELPER_MESSAGE
        } else {
            textBox.helperText = EMPTY_HELPER_MESSAGE
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
            tfb.setError(EMPTY_FIELD_ERROR, true)
            return false
        }
        return true
    }

    private fun loginUser(activity: Activity,
                          progressButtonUtil: ProgressButtonUtil,
                          authRequest: AuthRequest) {
        val authLayout: LinearLayout = findViewById(R.id.auth_layout)
        progressButtonUtil.activateButton()

        Timer().schedule(timerTask {
            NetworkService.getInstance()
                .authAPI
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
                            WRONG_AUTH_DATA_ERROR,
                            Snackbar.LENGTH_LONG
                        )
                        val sbView = wrongCredsSB.view
                        sbView.setBackgroundResource(R.color.colorError);
                        wrongCredsSB.show()
                    }
                })
            runOnUiThread(progressButtonUtil::finishButton)
        }, PROGRESS_BUTTON_DELAY)

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
