package com.mobdeve.s12.marquezgavanmaloles.mco

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DatabaseHelper
import com.mobdeve.s12.marquezgavanmaloles.mco.model.Task


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(dbHelper: DatabaseHelper){
    val viewModel = TasksViewModel(dbHelper)
    viewModel.getAllTasks()
    val tasks = viewModel.tasks
    Log.d("TAG", "${viewModel.tasks.size}")
    Row(modifier = Modifier
        .height(660.dp)
        .border(border = BorderStroke(2.dp, Color.Black))) {
        LazyColumn{
            items(tasks.size){
                Text(text = "Task: ${tasks[it].title}")
            }
        }
    }
//    Button(onClick = {
//        val task = Task("test", "test", "test", "test", "test", "reward")
//        viewModel.insertTask(task)
//        tasks + task
//    }) {
//        Icon(Icons.Filled.Add, contentDescription = "Add Task")
//    }
}

@Composable
fun TaskCard(task: Task){
    // TODO: make task card 
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun HomePreview(){
//    Surface{
//        Home()
//    }
//}