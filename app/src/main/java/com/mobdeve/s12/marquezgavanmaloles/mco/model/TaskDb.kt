package com.mobdeve.s12.marquezgavanmaloles.mco.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: String,
    val dueDate: String,
    val dueTime: String,
    val reward: String
){
    constructor(title: String, description: String, priority: String, dueDate: String, dueTime: String, reward: String): this (-1, title, description, priority, dueDate, dueTime, reward)
}
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
        Log.d("TAG", "Database created")
        db.execSQL(sqlCreateEntries)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TaskEntry.TABLE_NAME)
        onCreate(db)
    }

    fun insertTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_TITLE, task.title)
            put(DatabaseContract.TaskEntry.COLUMN_DESCRIPTION, task.description)
            put(DatabaseContract.TaskEntry.COLUMN_PRIORITY, task.priority)
            put(DatabaseContract.TaskEntry.COLUMN_DUE_DATE, task.dueDate)
            put(DatabaseContract.TaskEntry.COLUMN_DUE_TIME, task.dueTime)
            put(DatabaseContract.TaskEntry.COLUMN_REWARD, task.reward)
        }
        Log.d("TAG", "Added Task")
        db.insert(DatabaseContract.TaskEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTasks(): MutableList<Task> {
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
        val tasksList = mutableListOf<Task>()
            while(cursor.moveToNext()){
                val taskId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_ID))
                val taskTitle = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_TITLE))
                val taskDescription = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_DESCRIPTION))
                val taskPriority = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_PRIORITY))
                val taskDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_DUE_DATE))
                val taskTime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_DUE_TIME))
                val taskReward = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_REWARD))
                tasksList += Task(taskId ,taskTitle, taskDescription, taskPriority, taskDate, taskTime, taskReward)
            }
        cursor.close()
        db.close()
        return tasksList
    }
}