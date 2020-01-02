package Services

import Models.Channel
import Models.Message
import Utilities.GET_CHANNELS_URL
import Utilities.GET_MESSAGES_URL
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import joe.creative.smackapp.Controller.App
import org.json.JSONException

object MessageService {
    val channels = ArrayList<Channel>()
    val messages = ArrayList<Message>()

    fun getChannels(complete: (Boolean) -> Unit) {
        val channelsRequest = object: JsonArrayRequest(Method.GET, GET_CHANNELS_URL, null, Response.Listener {

            try {

                for (index in 0 until it.length()) {
                    val channel = it.getJSONObject(index)
                    val channelName = channel.getString("name")
                    val channelDescription = channel.getString("description")
                    val channelID = channel.getString("_id")

                    val newChannel = Channel(channelName, channelDescription, channelID)
                    this.channels.add(0, newChannel)
                }

                complete(true)

            } catch (e: JSONException) {
                Log.d("JSON", "EXC: ${e.localizedMessage}")
                complete(false)
            }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "An error occured ${error.localizedMessage}")
            complete(false)

        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")

                return headers
            }
        }

        App.prefs.requestQueue.add(channelsRequest)
    }

    fun getMessages(channelID: String, complete: (Boolean) -> Unit) {
        this.messagesClear()
        val getMessagesRequest = object : JsonArrayRequest(Method.GET, "${GET_MESSAGES_URL}$channelID", null, Response.Listener {
            response ->

            try {

                for (index in 0 until response.length()) {
                    val message = response.getJSONObject(index)
                    val messageBody = message.getString("messageBody")
                    val userName = message.getString("userName")
                    val channelID = message.getString("channelId")
                    val userAvatar = message.getString("userAvatar")
                    val userAvatarColor = message.getString("userAvatarColor")
                    val id = message.getString("_id")
                    val timeStamp = message.getString("timeStamp")

                    val newMessage = Message(messageBody, userName, channelID, userAvatar, userAvatarColor, id, timeStamp)

                    this.messages.add(0, newMessage)

                    complete(true)
                }

            } catch(e : JSONException) {
                Log.d("JSON", "EXC: ${e.localizedMessage}")
                complete(false)
            }

        }, Response.ErrorListener {
            error ->

            Log.d("ERROR", "An error occured ${error.localizedMessage}")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")

                return headers
            }
        }

        App.prefs.requestQueue.add(getMessagesRequest)
    }

    fun messagesClear() {
        messages.clear()
    }

    fun channelsClear() {
        channels.clear()
    }
}