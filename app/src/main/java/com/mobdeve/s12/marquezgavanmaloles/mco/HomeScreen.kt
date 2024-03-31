package com.mobdeve.s12.marquezgavanmaloles.mco

import android.annotation.SuppressLint
import android.app.ActivityManager.TaskDescription
import android.content.res.Resources.Theme
import android.os.Build
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.mobdeve.s12.marquezgavanmaloles.mco.ui.theme.darkGray
import com.mobdeve.s12.marquezgavanmaloles.mco.ui.theme.lightGreen
import com.mobdeve.s12.marquezgavanmaloles.mco.ui.theme.midtoneGray
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

data class Task(
    val title: String,
    val description: String,
    val priority: String,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val rewardTitle: String,
)

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

var taskList: List<Task> = listOf()

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(650.dp)
            .background(darkGray)
            ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        var showAddTask by remember {
            mutableStateOf(false)
        }
        if (showAddTask){
            AddTask(onDismiss = {showAddTask = false})
        }
        ExtendedFloatingActionButton(
            onClick = { showAddTask = true },
            modifier = Modifier
                .padding(20.dp)
                .width(400.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
            Text(text = "Add a Task")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddTask(onDismiss: () -> Unit){
    Dialog(
        onDismissRequest =  onDismiss
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ){
            /*val calendarState = rememberUseCaseState()
            val clockState = rememberUseCaseState()
            var selectedDate: LocalDate? = null
            var selectedHours: Int? = null
            var selectedMinutes: Int? = null
            CalendarDialog(
                state = calendarState,
                config = CalendarConfig(monthSelection = true, yearSelection = true, style = CalendarStyle.WEEK),
                selection = CalendarSelection.Date{date ->
                    selectedDate = date
                }
            )
            ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes{hours, minutes->
                selectedHours = hours
                selectedMinutes = minutes
            })
            Button(onClick = { calendarState.show() }) {
                Text(text = "Add Date")
            }
            Button(onClick = { calendarState.show() }) {
                Text(text = "Add Time")
            }
            Row {
                Text(text = "Due Date")
                Text(text = "$selectedDate $selectedHours:$selectedMinutes")
            }*/
            var title by remember { mutableStateOf("Untitled") }
            var description by remember { mutableStateOf("None") }
            var priority by remember { mutableStateOf("Low")}
            var dueDate: LocalDate? by remember { mutableStateOf(null) }
            var dueTime: LocalTime? by remember { mutableStateOf(null) }
            var rewardTitle by remember { mutableStateOf("None") }


            TextField(value = title, onValueChange = {title = it}, label= { Text(text = "Title")})

            TextField(value = description, onValueChange = {description = it}, label= { Text(text = "Description")})
            
            Text(text = "Priority: ", modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Button(onClick = { priority = "Low" }, modifier = Modifier.padding(5.dp)) {
                        Text(text = "Low", modifier = if (priority == "Low") Modifier.background(
                            midtoneGray) else Modifier)
                    }
                }
                Column {
                    Button(onClick = { priority = "Medium" },modifier = Modifier.padding(5.dp)) {
                        Text(text = "Medium", modifier = if (priority == "Medium") Modifier.background(
                            midtoneGray) else Modifier)
                    }
                }
                Column {
                    Button(onClick = { priority = "High" }, modifier = Modifier.padding(5.dp)) {
                        Text(text = "High", modifier = if (priority == "High") Modifier.background(
                            midtoneGray) else Modifier)
                    }
                }
            }
            val calendarState = rememberUseCaseState()
            CalendarDialog(state = calendarState, selection = CalendarSelection.Date{date ->
                dueDate = date
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
                Text(text = if (dueDate != null) "Due: $dueDate" else "", modifier = Modifier.padding(start = 10.dp))
            }
            val clockState = rememberUseCaseState()
            ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes{hours, minutes ->
                dueTime = LocalTime.of(hours, minutes)
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
                Text(text = if (dueTime != null) "At $dueTime" else "", modifier = Modifier.padding(start = 10.dp))
            }
            val rewardList = listOf(
                "1 Hour Free Time",
                "2 Hours Free Time",
                "3 Hours Free Time",
            )

            val pagerState = rememberPagerState(pageCount = {
                rewardList.size
            })
            Text(text = "Swipe to Select Reward:")
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
                Button(onClick = { onDismiss() }, modifier = Modifier.padding(end = 10.dp)) {
                    Text(text = "Cancel")
                }
                Button(onClick = {
                    if(dueDate == null || dueTime == null){
                        showToast = true
                    }
                    else{
                        val newTask = Task(title, description, priority, dueDate!!, dueTime!!, rewardTitle)
                        taskList += newTask
                        onDismiss()
                    }
                }) {
                    Text(text = "Submit")

                }
            }
        }
    }
}

@Composable
fun TaskCard(){
    // TODO: Task Item Layout
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomePreview(){
    Surface (
    ){
        Home()
    }
}