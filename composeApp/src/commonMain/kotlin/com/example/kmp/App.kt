package com.example.kmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // 1. **Theming**: Apply Material 3 theme for a consistent, modern look.
    // The MaterialTheme defines colors, typography, and shapes.
    MaterialTheme (
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE), // Primary color for app
            onPrimary = Color.White,
            background = Color.White
        )
    ) {
        // 2. **State Management**: Use 'remember' to store a mutable value
        // that survives recompositions, and 'mutableStateOf' to make it
        // observable (triggering a recomposition when changed).
        var clickCount by remember { mutableStateOf(0) } // Initial state is 0

        // Surface is a Material design container that applies a theme elevation/color
        Surface(
            modifier = Modifier.fillMaxSize(), // Fill the entire screen/window
            color = MaterialTheme.colorScheme.background // Use background color from theme
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 3. **Composable Basics**: Simple Text component
                Text(
                    text = "Welcome to Compose Multiplatform!",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 4. **Interaction**: Button component with an event handler
                Button(
                    onClick = {
                        // **State Update**: Clicking the button changes the state,
                        // which automatically triggers a UI recomposition.
                        clickCount++
                    },
                    modifier = Modifier.width(200.dp)
                ) {
                    // Display the current state value
                    Text(
                        text = if (clickCount == 0) "Click Me!" else "Clicked $clickCount times"
                    )
                }
            }
        }
    }
}