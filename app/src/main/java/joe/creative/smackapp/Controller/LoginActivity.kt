package joe.creative.smackapp.Controller

import Services.AuthService
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import joe.creative.smackapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginSpinner.visibility = View.INVISIBLE
    }

    fun loginCreateUserBtnClicked(view: View) {
        val createAccountIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createAccountIntent)
        finish()
    }

    fun loginLoginBtnClicked(view: View) {
        hideKeyboard()
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()) {
            enableSpinner(true)
            AuthService.loginUser(this, email, password) {
                if(it) {
                    AuthService.findUserByEmail(this) { isSuccess ->
                        if(isSuccess) {
                            enableSpinner(false)
                            finish()
                        }
                        else errorToast()
                    }
                } else errorToast()
            }
        }
        else {
            Toast.makeText(this, "Please fill in an email and password", Toast.LENGTH_LONG)
                .show()
        }

    }

    fun errorToast() {
        Toast.makeText(this, "Something went wrong please try again", Toast.LENGTH_LONG)
            .show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean) {
        if(enable === true) {
            loginSpinner.visibility = View.VISIBLE
        } else {
            loginSpinner.visibility = View.INVISIBLE
        }
        loginLoginBtn.isEnabled = !enable
        loginCreateUserBtn.isEnabled = !enable
    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if(inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}
