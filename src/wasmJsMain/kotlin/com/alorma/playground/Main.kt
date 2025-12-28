package com.alorma.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.CanvasBasedWindow

/**
 * Main entry point for the web application using Compose Multiplatform with WASM
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        MaterialTheme {
            App()
        }
    }
}

@Composable
fun App() {
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF667eea),
            Color(0xFF764ba2)
        )
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(40.dp)
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.large
                )
                .padding(40.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                val greeting = remember { Greeting() }
                val message = greeting.greet()
                
                Text(
                    text = message,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Text(
                    text = "This is a Kotlin Multiplatform web application using Compose!",
                    fontSize = 24.sp,
                    color = Color.White
                )
                
                Text(
                    text = "Built with Compose Multiplatform + WebAssembly 🚀",
                    fontSize = 20.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}
