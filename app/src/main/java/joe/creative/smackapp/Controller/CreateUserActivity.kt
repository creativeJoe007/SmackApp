package joe.creative.smackapp.Controller

import Services.AuthService
import Services.UserDataService
import Utilities.BROADCAST_USER_DATA_CHANGE
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import joe.creative.smackapp.R
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {
    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        userAvatar = if(color === 0) {
            "light$avatar"
        } else {
            "dark$avatar"
        }
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View) {
        val random = Random()
        var r = random.nextInt(255)
        var g = random.nextInt(255)
        var b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"

    }

    fun createUserClicked(view: View) {
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()
        val userName = createUserNameText.text.toString()

        enableSpinner(true)

        if(userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.registerUser(this, email, password) {
                if(it) {
                    AuthService.loginUser(this, email, password) { loginSucess ->
                        if(loginSucess) AuthService.createUser(this, userName, email, userAvatar, avatarColor) {
                            if(it) {
                                // Create a broadcast message
                                val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)

                                enableSpinner(false)
                                finish()
                            } else errorToast()
                        } else errorToast()
                    }
                } else errorToast()
            }
        } else {
            Toast.makeText(this, "Make sure username, email and password are filled in", Toast.LENGTH_LONG)
                .show()

            enableSpinner(false)
        }

    }

    fun errorToast() {
        Toast.makeText(this, "Something went wrong please try again", Toast.LENGTH_LONG)
            .show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean) {
        if(enable === true) {
            createSpinner.visibility = View.VISIBLE
        } else {
            createSpinner.visibility = View.INVISIBLE
        }
        createUserBtn.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }
}
