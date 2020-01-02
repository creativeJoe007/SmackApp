package Services

import android.graphics.Color
import joe.creative.smackapp.Controller.App
import java.util.*

object UserDataService {
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun returnAvatarColor(components: String):Int {
        // we want to convert the color string we saved in the db to a color int
        val stripedColor = components
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")

        var r = 0; var g = 0; var b = 0
        val scanner = Scanner(stripedColor)
        if(scanner.hasNext()) {
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
        }

        return Color.rgb(r, g, b)
    }

    fun logout() {
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        App.prefs.authToken = ""
        App.prefs.userEmail = ""
        App.prefs.isLoggedIn = false

        MessageService.messagesClear()
        MessageService.channelsClear()
    }
}