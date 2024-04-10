package com.mobdeve.s12.marquezgavanmaloles.mco

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DataStoreRepository
import kotlinx.coroutines.launch

@Composable
fun Welcome(navController: NavController, dataStore: DataStoreRepository){
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("Enter Your name") }
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to", style = MaterialTheme.typography.headlineSmall)
        Text(text = "HocusFocus", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(20.dp))
        Text(text = "Enter your name to start", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(10.dp))
        TextField(value = name, onValueChange = { name = it }, modifier = Modifier.padding(10.dp))
        Button(onClick = {
            scope.launch {
                dataStore.saveUserName(name)
                dataStore.saveRegistered(true)
            }
            navController.navigate(Routes.HOME_SCREEN)
        }) {
            Text(text = "Submit")
        }
    }
}