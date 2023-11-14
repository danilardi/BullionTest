package com.crackearth.bulliontest.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.crackearth.bulliontest.model.DataLoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val EMAIL = stringPreferencesKey("email")
    private val NAME = stringPreferencesKey("name")
    private val TOKEN = stringPreferencesKey("token")

    suspend fun setAuth(dataLoginResponse: DataLoginResponse) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = dataLoginResponse.email
            preferences[NAME] = dataLoginResponse.name
            preferences[TOKEN] = dataLoginResponse.token
        }
    }

    fun getAuth(): Flow<DataLoginResponse> {
        return dataStore.data.map { preferences ->
            DataLoginResponse(
                preferences[EMAIL].toString(),
                preferences[NAME].toString(),
                preferences[TOKEN].toString()
            )
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}