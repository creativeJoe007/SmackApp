package Services

import Models.Channel
import Models.Message
import Utilities.GET_CHANNELS_URL
import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
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
}