package com.mobdeve.s12.marquezgavanmaloles.mco

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.marquezgavanmaloles.mco.model.CalendarHelper
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DataStoreRepository
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DatabaseHelper
import com.mobdeve.s12.marquezgavanmaloles.mco.model.Task
import kotlinx.coroutines.launch


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(dbHelper: DatabaseHelper, calendarHelper: CalendarHelper, dataStore: DataStoreRepository){
    val scope = rememberCoroutineScope()
    var completed = dataStore.getCompleted.collectAsState(initial = 0).value
    val viewModel = TasksViewModel(dbHelper, calendarHelper)
    viewModel.getAllTasks()
    val taskList = viewModel.tasks
    val tasks = remember { mutableStateListOf<Task>() }
    tasks.clear()
    tasks += taskList
    Log.d("TAG", "${viewModel.tasks.size}")
    Row(modifier = Modifier
        .height(660.dp)
        ) {
        LazyColumn{
            items(tasks.size){
                if (completed != null) {
                    TaskCard(tasks[it], onDeleteTask = {id ->
                        viewModel.deleteTask(id)
                        tasks -= tasks[it]
                        completed += 1
                        scope.launch {
                            dataStore.saveCompleted(completed)
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, onDeleteTask: (Int) -> Unit){
    // TODO: make task card
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .shadow(elevation = 5.dp)
            .padding(10.dp)
    ) {
        Column {
            Text(text = task.title, style = MaterialTheme.typography.titleLarge, modifier = Modifier
                .padding(5.dp))
            Text(text = task.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(5.dp))
            Text(text = "Priority: ${task.priority}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(5.dp))
            Text(text = "Due: ${task.dueDate} at ${task.dueTime}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(5.dp))
            Row(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End){
                Text(
                    text = "Reward: ${task.reward}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(5.dp)
                )
                Button(onClick = { onDeleteTask(task.id) }) {
                    Icon(Icons.Outlined.Check, contentDescription = "Finish")
                }
            }
        }
    }
}
