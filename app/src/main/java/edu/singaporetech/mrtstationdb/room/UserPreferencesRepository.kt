package edu.singaporetech.mrtstationdb.room

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

data class UserPreferences(
    val stationCode: String,
    val stationName: String,
    val stationLine: Int
)

class UserPreferencesRepository(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = USER_PREFERENCES_NAME)

    private object PreferencesKeys {
        val STATION_CODE = stringPreferencesKey("station_code")
        val STATION_NAME = stringPreferencesKey("station_name")
        val STATION_LINE = intPreferencesKey("station_line")
    }

    val preferencesFlow = dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { preferences ->
            val stationCode = preferences[PreferencesKeys.STATION_CODE] ?: ""
            val stationName = preferences[PreferencesKeys.STATION_NAME] ?: ""
            val stationLine = preferences[PreferencesKeys.STATION_LINE] ?: 0
            UserPreferences(stationCode, stationName, stationLine)
        }

    suspend fun updateStation(model: UserPreferences) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.STATION_CODE] = model.stationCode
            preferences[PreferencesKeys.STATION_NAME] = model.stationName
            preferences[PreferencesKeys.STATION_LINE] = model.stationLine
        }
    }

    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"

        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository =
            INSTANCE ?: synchronized(this) {
                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
    }
}