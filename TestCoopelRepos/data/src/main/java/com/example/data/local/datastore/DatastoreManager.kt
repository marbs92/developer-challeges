package com.example.data.local.datastore
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
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

    private val myVariableKey = stringPreferencesKey("local_pref")

    suspend fun saveMyVariable(myVariable: String) {
        dataStore.edit { preferences ->
            preferences[myVariableKey] = myVariable
        }
    }

    val myVariableFlow = dataStore.data
        .map { preferences ->
            preferences[myVariableKey] ?: ""
        }
}