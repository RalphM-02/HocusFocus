package com.mobdeve.s12.marquezgavanmaloles.mco

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: String,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val rewardTitle: String
)
