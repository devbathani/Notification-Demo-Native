package com.example.push_notification_flutter

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.push_notification_flutter.ui.theme.NotificationDemoTheme
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NotificationDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Pass token state as argument to the Greeting composable
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    // Token state
    val tokenState = remember { mutableStateOf("") }

    // Fetch FCM token and update state
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("FCM", "Fetching FCM registration token failed", task.exception)
            return@addOnCompleteListener
        }
        val token = task.result
        Log.d("FCM", "FCM Token: $token")
        tokenState.value = token
    }

    // Display FCM Token if available
    if (tokenState.value.isNotEmpty()) {
        SelectionContainer {
            Text(
                text = "FCM Token: ${tokenState.value}",
                modifier = modifier,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    } else {
        Text(
            text = "Fetching FCM Token...",
            modifier = modifier
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotificationDemoTheme {
        Greeting()
    }
}