package com.crackearth.bulliontest.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.crackearth.bulliontest.data.local.AuthPreferences
import com.crackearth.bulliontest.data.remote.ApiConfig
import com.crackearth.bulliontest.repository.Repository

object Injection {
    fun provideAuthPref(dataStore: DataStore<Preferences>): AuthPreferences {
        return AuthPreferences.getInstance(dataStore)
    }

    fun provideRepository(): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }
}