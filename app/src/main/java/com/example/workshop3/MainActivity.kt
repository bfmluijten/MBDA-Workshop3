package com.example.workshop3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workshop3.ui.theme.Workshop3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                                    showDialog = false/* handle confirmation */
                                }) { Text("Confirm") }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                }) { Text("Dismiss") }
                            })
                    }
                    Row(modifier = Modifier.padding(innerPadding)) {
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
                                    Text("Option 1")
                                }, onClick = {
                                    expanded = false
                                    /* Do something... */
                                })
                                DropdownMenuItem(text = {
                                    Text("Option 2")
                                }, onClick = {
                                    expanded = false
                                    /* Do something... */
                                })
                            }
                        }
                    }
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