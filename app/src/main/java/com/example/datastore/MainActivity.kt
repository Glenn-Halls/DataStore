package com.example.datastore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datastore.model.DatastoreViewModel
import com.example.datastore.ui.theme.DatastoreTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatastoreTheme {
                val viewModel = DatastoreViewModel(context = this)
                val int = viewModel.currentInt.collectAsState(initial = 0)
                val stringInt = viewModel.currentStringInt.collectAsState(initial = "ZERO")
                val number = viewModel.currentNumber.collectAsState(initial = "")
                val coroutineScope = rememberCoroutineScope()
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "int = ${int.value}", fontSize = 20.sp)
                        Text(text = "numberInt = ${stringInt.value}", fontSize = 20.sp)
                        Spacer(modifier = Modifier.size(12.dp))
                        Row {
                            Button(onClick = { coroutineScope.launch {
                                viewModel.updateInt()
                            }
                            }) {
                                Text("int")
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Button(onClick = { coroutineScope.launch {
                                viewModel.updateStringInt()
                            } }) {
                                Text("numberInt")
                            }
                        }
                        Button(onClick = {
                            coroutineScope.launch {
                                viewModel.updateNumber()
                            }
                        }) {
                            Text("ENUM")
                        }
                        Text("${number.value}")
                        Text(text = "${SavedNumbers.Number.entries.toList()}")
                    }
                }
            }
        }
    }
}
