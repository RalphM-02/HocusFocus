package com.mobdeve.s12.marquezgavanmaloles.mco

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(){
    val currentMonth = YearMonth.now()
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value

    val calendarDays = (1..daysInMonth).toList()
    val paddingDays = (1 until firstDayOfMonth).toList()

    Row(
        modifier = Modifier
            .height(660.dp)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO: Display number of tasks per day
            itemsIndexed(paddingDays + calendarDays) { index, day ->
                CalendarDay(day)
            }
        }
    }
}

@Composable
fun CalendarDay(day: Int) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .padding(2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (day != 0) day.toString() else "",
            color = if (day != 0) Color.Black else Color.Transparent
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PrevCalendar(){
    Surface {
        CalendarScreen()
    }
}