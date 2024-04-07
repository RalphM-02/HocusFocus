package com.mobdeve.s12.marquezgavanmaloles.mco

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DatabaseHelper
import com.mobdeve.s12.marquezgavanmaloles.mco.model.Task
import com.mobdeve.s12.marquezgavanmaloles.mco.ui.theme.lightGreen
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddTask(dbHelper: DatabaseHelper){
    val viewModel = TasksViewModel(dbHelper)
    var title by remember { mutableStateOf("Untitled") }
    var description by remember { mutableStateOf("None") }
    var priority by remember { mutableStateOf("Low")}
    var dueDate: String by remember { mutableStateOf("") }
    var dueTime: String by remember { mutableStateOf("") }
    var rewardTitle by remember { mutableStateOf("None") }

    Row(
        modifier = Modifier.height(660.dp)
    ) {
        Column {
            TextField(value = title, onValueChange = {title = it}, label= { Text(text = "Title")}, modifier = Modifier.fillMaxWidth().padding(5.dp))
            TextField(value = description, onValueChange = {description = it}, label= { Text(text = "Description")}, modifier = Modifier.fillMaxWidth().padding(5.dp))
            Text(text = "Priority: ", modifier = Modifier.padding(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Button(onClick = { priority = "Low" }, modifier = if (priority == "Low") Modifier.background(Color.Gray).padding(5.dp) else Modifier.padding(5.dp)) {
                        Text(text = "Low")
                    }
                }
                Column {
                    Button(onClick = { priority = "Mid" }, modifier = if (priority == "Mid") Modifier.background(Color.Gray).padding(5.dp) else Modifier.padding(5.dp)) {
                        Text(text = "Mid")
                    }
                }
                Column {
                    Button(onClick = { priority = "High" }, modifier = if (priority == "High") Modifier.background(Color.Gray).padding(5.dp) else Modifier.padding(5.dp)) {
                        Text(text = "High")
                    }
                }
            }
            val calendarState = rememberUseCaseState()
            CalendarDialog(state = calendarState, selection = CalendarSelection.Date{ date ->
                dueDate = date.toString()
            })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Button(onClick = { calendarState.show() }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "due date")
                    Text(text = "Add date")
                }
                Text(text = "Due: $dueDate", modifier = Modifier.padding(start = 10.dp))
            }
            val clockState = rememberUseCaseState()
            ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes{ hours, minutes ->
                dueTime = LocalTime.of(hours, minutes).toString()
            })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Button(onClick = { clockState.show() }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "due time")
                    Text(text = "Add Time")
                }
                Text(text = "At: $dueTime", modifier = Modifier.padding(start = 10.dp))
            }
            val rewardList = listOf(
                "1 Hour Free Time",
                "2 Hours Free Time",
                "1 Hour Play Time",
                "2 Hours Play Time",
            )

            val pagerState = rememberPagerState(pageCount = {
                rewardList.size
            })
            Text(text = "Swipe to Select Reward:", modifier = Modifier.padding(20.dp))
            HorizontalPager(state = pagerState) { page->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(60.dp)
                        .background(lightGreen),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = rewardList[page])
                    rewardTitle = rewardList[page]
                }
            }
            var showToast by remember { mutableStateOf(false) }
            if (showToast) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        Button(onClick = { showToast = false }) {
                            Text("Dismiss")
                        }
                    },
                ) {
                    Text("Please Set a Date and/or Time")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Button(onClick = {
                    if(dueDate == "" || dueTime == ""){
                        showToast = true
                    }
                    else{
                        val newTask = Task(0, title, description, priority, dueDate, dueTime, rewardTitle)
                        viewModel.insertTask(newTask)
                    }
                }) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun AddPrev(){
//    Surface {
//        AddTask()
//    }
//}