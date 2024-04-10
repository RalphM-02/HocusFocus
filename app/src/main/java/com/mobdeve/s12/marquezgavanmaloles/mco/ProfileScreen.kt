package com.mobdeve.s12.marquezgavanmaloles.mco

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DataStoreRepository

@Composable
fun Profile(navController: NavController, dataStore: DataStoreRepository, registered: Boolean){
    if(!registered){
        navController.navigate(Routes.WELCOME_SCREEN)
    }
    val name = dataStore.getUserName.collectAsState(initial = "").value
    val completed = dataStore.getCompleted.collectAsState(initial = 0).value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(660.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .border(BorderStroke(2.dp, Color.Black)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.AccountBox, contentDescription = "user", modifier = Modifier.size(80.dp))
            Text(text = "Hi $name!", style = MaterialTheme.typography.titleLarge)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "You Have Completed $completed Tasks!")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { /*TODO: edit rewards list*/ }) {
                Text(text = "Edit Rewards")
            }
        }
        Text(text = "Available Rewards: ", modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp), textAlign = TextAlign.Center)

        // TODO: Rewards List
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item{
                Text(text = "1 hour free time")
            }
            item{
                Text(text = "2 hours free time")
            }
            item{
                Text(text = "1 hour play time")
            }
            item {
                Text(text = "2 hours play time")
            }
        }
    }
}
