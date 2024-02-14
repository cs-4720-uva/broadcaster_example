package edu.virginia.cs.broadcastsender

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import edu.virginia.cs.broadcastsender.ui.theme.BroadcastSenderTheme

class MainActivity : ComponentActivity() {
    private var textToDisplay = mutableStateOf("No results from API yet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTextStringFromJSON()

        setContent {
            BroadcastSenderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text(textToDisplay.value)
                        Button(onClick = {
                            sendBroadcast(
                                Intent("TEST_ACTION")
                            )
                        }) {
                            Text(text = "Send broadcast")
                        }
                    }
                }
            }
        }
    }

    private fun getTextStringFromJSON() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://raw.githubusercontent.com/cs-4720-uva/broadcaster_example/main/example.json"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> {response -> textToDisplay.value = response },
            Response.ErrorListener {
                textToDisplay.value = "Something bad happened"
            })
        queue.add(stringRequest)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BroadcastSenderTheme {
        Greeting("Android")
    }
}