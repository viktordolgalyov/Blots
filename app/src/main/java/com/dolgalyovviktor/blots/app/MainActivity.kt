package com.dolgalyovviktor.blots.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dolgalyovviktor.blots.ui.BlotsApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    content = { AppContainer() }
                )
            }
        }
    }

    @Composable
    private fun AppContainer(modifier: Modifier = Modifier) {
        val insetsPadding = WindowInsets.systemGestures.asPaddingValues()
        Scaffold(modifier = Modifier.padding(insetsPadding)) {
            BlotsApp(modifier.padding(it))
        }
    }
}