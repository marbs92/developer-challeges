package com.example.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "general_data_user"
    )

    object PreferencesKeys {
        val name = stringPreferencesKey("name")
        val username = stringPreferencesKey("username")
        val urlPhoto = stringPreferencesKey("urlPhoto")
    }


    suspend fun setPreferencesData(name: String?, username: String?, urlPhoto: String?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.name] = name ?:""
            preferences[PreferencesKeys.username] = username ?:""
            preferences[PreferencesKeys.urlPhoto] = urlPhoto ?:""
        }
    }

    suspend fun getPreferencesData(): PreferencesData {
        val preferences = dataStore.data.first()
        return PreferencesData(
            preferences[PreferencesKeys.name] ?: "",
            preferences[PreferencesKeys.username] ?: "",
            preferences[PreferencesKeys.urlPhoto] ?: ""
        )
    }
}