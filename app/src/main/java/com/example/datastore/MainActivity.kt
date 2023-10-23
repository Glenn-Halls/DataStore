package com.example.datastore

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datastore.ui.theme.DatastoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatastoreTheme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "int = ", fontSize = 20.sp)
                        Text(text = "numberInt = ", fontSize = 20.sp)
                        Spacer(modifier = Modifier.size(12.dp))
                        Row {
                            Button(onClick = { /*TODO*/ }) {
                                Text("int")
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Button(onClick = { /*TODO*/ }) {
                                Text("numberInt")
                            }
                        }
                    }
                }
            }
        }
    }
}