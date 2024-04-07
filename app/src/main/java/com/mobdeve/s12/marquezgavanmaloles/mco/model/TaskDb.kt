package com.mobdeve.s12.marquezgavanmaloles.mco.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class Task(
    val title: String,
    val description: String,
    val priority: String,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val reward: String
)
object DatabaseContract {
    object TaskEntry {
        const val COLUMN_ID = "id"
        const val TABLE_NAME = "tasks"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PRIORITY = "priority"
        const val COLUMN_DUE_DATE = "due_date"
        const val COLUMN_DUE_TIME = "due_time"
        const val COLUMN_REWARD = "reward"
    }
}
@RequiresApi(Build.VERSION_CODES.O)
class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private  const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TaskDatabase.db"
        private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreateEntries =
            "CREATE TABLE ${DatabaseContract.TaskEntry.TABLE_NAME} (" +
                    "${DatabaseContract.TaskEntry.COLUMN_ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContract.TaskEntry.COLUMN_TITLE} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_DESCRIPTION} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_PRIORITY} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_DUE_DATE} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_DUE_TIME} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_REWARD} TEXT)"

        db.execSQL(sqlCreateEntries)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertTask(task: Task): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_TITLE, task.title)
            put(DatabaseContract.TaskEntry.COLUMN_DESCRIPTION, task.description)
            put(DatabaseContract.TaskEntry.COLUMN_PRIORITY, task.priority)
            put(DatabaseContract.TaskEntry.COLUMN_DUE_DATE, task.dueDate.format(dateFormatter))
            put(DatabaseContract.TaskEntry.COLUMN_DUE_TIME, task.dueTime.format(timeFormatter))
            put(DatabaseContract.TaskEntry.COLUMN_REWARD, task.reward)
        }
        return db.insert(DatabaseContract.TaskEntry.TABLE_NAME, null, values)
    }

    fun getAllTasks(): List<Task> {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            DatabaseContract.TaskEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val tasksList = emptyList<Task>()
        cursor.use {
            while (it.moveToNext()) {
                // val taskId = it.getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ID)
                val taskTitle = it.getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ID).toString()
                val taskDescription = it.getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ID).toString()
                val taskPriority = it.getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ID).toString()
                val taskDate = it.getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ID).toString()
                val taskTime = it.getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ID).toString()
                val taskReward = it.getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ID).toString()
                // Create Task object and add it to the list
                val task = Task(taskTitle, taskDescription, taskPriority, LocalDate.parse(taskDate, dateFormatter), LocalTime.parse(taskTime, timeFormatter), taskReward)
                tasksList + task
            }
        }
        return tasksList
    }
}