package com.mobdeve.s12.marquezgavanmaloles.mco.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val context: Context) {
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserData")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val COMPLETED_COUNT = intPreferencesKey("completed")
        val REGISTERED_KEY = booleanPreferencesKey("registered")
    }

    val getUserName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    suspend fun saveUserName (name: String){
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
        Log.d("TAG", "Name saved as $name")
    }

    val getCompleted: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[COMPLETED_COUNT] ?: 0
        }

    suspend fun saveCompleted (completed: Int){
        context.dataStore.edit { preferences->
            preferences[COMPLETED_COUNT] = completed
        }
    }

    val getRegistered: Flow<Boolean?> = context.dataStore.data
        .map {preferences ->
            preferences[REGISTERED_KEY] ?: false
        }

    suspend fun saveRegistered (registered: Boolean){
        context.dataStore.edit { preferences ->
            preferences[REGISTERED_KEY] = registered
        }
    }
}