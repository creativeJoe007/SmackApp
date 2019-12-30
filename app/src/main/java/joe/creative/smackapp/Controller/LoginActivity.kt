package joe.creative.smackapp.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import joe.creative.smackapp.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginCreateUserBtnClicked(view: View) {
        val createAccountIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createAccountIntent)
    }

    fun loginLoginBtnClicked(view: View) {

    }
}
