package com.mobdeve.s12.marquezgavanmaloles.mco.model

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import java.util.TimeZone

class CalendarHelper(private val contentResolver: ContentResolver) {
    fun getEvents(): List<String> {
        val events : MutableList<String> = mutableListOf()

        val projection = arrayOf(
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART
        )

        val uri: Uri = CalendarContract.Events.CONTENT_URI
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)

        while (cursor!!.moveToNext()){
            val eventId = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.Events.CALENDAR_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.TITLE))
            val startTime = cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Events.DTSTART))
            events.add("$title - $startTime")
        }
        cursor.close()
        return events
    }

    fun addEvent(contentResolver: ContentResolver, title: String, description: String, startTime: Long, endTime: Long): Long? {
        val eventValues = ContentValues().apply {
            put(CalendarContract.Events.TITLE, title)
            put(CalendarContract.Events.TITLE, description)
            put(CalendarContract.Events.DTSTART, startTime)
            put(CalendarContract.Events.DTEND, endTime)
            put(CalendarContract.Events.CALENDAR_ID, 1)
            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        }

        val uri: Uri = CalendarContract.Events.CONTENT_URI
        uri.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "your_account_name") // You may need to change this
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
            .build()

        val calendarUri: Uri? = contentResolver.insert(uri, eventValues)
        return calendarUri?.lastPathSegment?.toLong()
    }
}