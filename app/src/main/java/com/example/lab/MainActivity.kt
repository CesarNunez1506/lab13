package com.example.lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab.ui.theme.LabTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.material3.MaterialTheme
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //AnimatedVisibilityExample()
                    //AnimateColorExample()
                    //AnimateSizeAndPositionExample()
                    //AnimatedContentExample()
                    CombinedAnimationsExample()
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Composable
fun AnimatedVisibilityExample() {
    var isVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { isVisible = !isVisible }) {
            Text("Activar Visibilidad")
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Blue)
            )
        }
    }
}
@Composable
fun AnimateColorExample() {
    var isBlue by remember { mutableStateOf(true) }
    val color by animateColorAsState(
        targetValue = if (isBlue) Color.Blue else Color.Green,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color)
            .clickable { isBlue = !isBlue },
        contentAlignment = Alignment.Center
    ) {
        Text("Clic para cambiar el color", color = Color.White)
    }
}@Composable
fun AnimateSizeAndPositionExample() {
    var isExpanded by remember { mutableStateOf(false) }
    val size by animateDpAsState(targetValue = if (isExpanded) 200.dp else 100.dp)
    val offset by animateDpAsState(targetValue = if (isExpanded) 100.dp else 0.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { isExpanded = !isExpanded }) {
            Text("Cambiar tamaño y posicion")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset, y = offset)
                .background(Color.Red)
        )
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentExample() {
    var currentState by remember { mutableStateOf("Cargando") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            currentState = when (currentState) {
                "Cargando" -> "Contenido"
                "Contenido" -> "Error"
                else -> "Cargando"
            }
        }) {
            Text("Change State")
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(targetState = currentState, transitionSpec = {
            fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
        }) { targetState ->
            Text(
                text = when (targetState) {
                    "Cargando" -> "Cargando..."
                    "Contenido" -> "¡Contenido cargado!"
                    "Error" -> "Se produjo un error."
                    else -> ""
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
@Composable
fun CombinedAnimationsExample() {
    var isExpanded by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(true) }
    var isLightMode by remember { mutableStateOf(true) }

    val size by animateDpAsState(targetValue = if (isExpanded) 150.dp else 100.dp)
    val color by animateColorAsState(
        targetValue = if (isExpanded) Color.Magenta else Color.Cyan,
        animationSpec = tween(durationMillis = 1000)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isLightMode) Color.White else Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(color)
                .clickable { isExpanded = !isExpanded }
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = isButtonVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Button(onClick = { isButtonVisible = !isButtonVisible }) {
                Text("Desaparecer Boton")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        AnimatedContent(targetState = isLightMode) { targetMode ->
            Text(
                text = if (targetMode) "Modo Claro" else "Modo Oscuro",
                color = if (isLightMode) Color.Black else Color.White,
                modifier = Modifier.clickable { isLightMode = !isLightMode }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabTheme {
        Greeting("Android")
    }
}