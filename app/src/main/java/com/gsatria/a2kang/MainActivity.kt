package com.gsatria.a2kang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gsatria.a2kang.screen.welcome.WelcomeScreen
import com.gsatria.a2kang.ui.theme._2KangTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2KangTheme {
                WelcomeScreen(
                    onStartClick = {
                        // nanti pindah ke screen lain
                        // contoh: navigate to Login
                    }
                )
            }
        }
    }
}
