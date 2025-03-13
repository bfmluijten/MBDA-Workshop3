package com.example.workshop3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workshop3.ui.theme.Workshop3Theme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()

            Workshop3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var showDialog by remember { mutableStateOf(false) }
                    if (showDialog) {
                        AlertDialog(
                            icon = { Icon(Icons.Default.Info, contentDescription = "") },
                            title = { Text(text = "Alert dialog example") },
                            text = { Text(text = "This is an example of an alert.") },
                            onDismissRequest = { showDialog = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                }) { Text("Confirm") }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                }) { Text("Dismiss") }
                            })
                    }
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(10.dp)
                    ) {
                        var showRed by remember { mutableStateOf(false) }
                        var showGreen by remember { mutableStateOf(false) }
                        Row {
                            Button(onClick = {
                                showDialog = true
                            }) {
                                Text("Show Dialog")
                            }
                            Spacer(modifier = Modifier.weight(1.0f))
                            var expanded by remember { mutableStateOf(false) }
                            Box {
                                IconButton(onClick = {
                                    expanded = !expanded
                                }) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = ""
                                    )
                                }
                                DropdownMenu(expanded = expanded, onDismissRequest = {
                                    expanded = false
                                }) {
                                    DropdownMenuItem(text = {
                                        Text(if (showRed) "Hide Red" else "Show Red")
                                    }, onClick = {
                                        expanded = false
                                        showRed = !showRed
                                    })
                                    DropdownMenuItem(text = {
                                        Text(if (showGreen) "Hide Green" else "Show Green")
                                    }, onClick = {
                                        expanded = false
                                        showGreen = !showGreen
                                    })
                                }
                            }
                        }
                        NavHost(
                            modifier = Modifier
                                .height(300.dp)
                                .padding(top = 10.dp),
                            navController = navController,
                            startDestination = "items/home"
                        ) {
                            composable(
                                route = "items/{uri}",
                                arguments = listOf(navArgument("uri") {
                                    type = NavType.StringType
                                })
                            ) { navBackStackEntry ->
                                val uri = navBackStackEntry.arguments?.getString("uri") ?: "home"
                                NavigationContent(uri)
                            }
                        }
                        AnimatedVisibility(
                            showRed
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(Color.Red)
                            ) {}
                        }
                        AnimatedVisibility(
                            showGreen,
                            enter = scaleIn(),
                            exit = scaleOut()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(Color.Green)
                            ) {}
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun NavigationContent(uri: String) {
        val items = mapOf(
            "Pikachu" to R.drawable.pikachu,
            "Dragonite" to R.drawable.dragonite,
            "Bulbasaur" to R.drawable.bulbasaur
        )
        if (uri == "home") {
            Column {
                items.map {
                    Row(modifier = Modifier.clickable(onClick = {
                        navController.navigate("items/${it.key}")
                    }), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(it.value), contentDescription = "")
                        Text(it.key)
                    }
                }
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp),
                        painter = painterResource(items[uri] ?: 0),
                        contentDescription = ""
                    )
                    Text(uri, fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Back")
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Workshop3Theme {
        Greeting("Android")
    }
}