package joe.creative.smackapp.Controller

import Services.AuthService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import joe.creative.smackapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginCreateUserBtnClicked(view: View) {
        val createAccountIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createAccountIntent)
        finish()
    }

    fun loginLoginBtnClicked(view: View) {
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()
        AuthService.loginUser(this, email, password) {
            if(it) {
                AuthService.findUserByEmail(this) { isSuccess ->
                    if(isSuccess) finish()

                }
            } else {

            }
        }
    }
}
