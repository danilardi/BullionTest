package com.crackearth.bulliontest.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crackearth.bulliontest.data.local.AuthPreferences
import com.crackearth.bulliontest.repository.Repository
import com.crackearth.bulliontest.utils.Injection


class ViewModelFactory private constructor(
    private val pref: AuthPreferences,
    private val repository: Repository
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(
                pref,
                repository
            ) as T

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                pref,
                repository
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(dataStore: DataStore<Preferences>): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideAuthPref(dataStore),
                    Injection.provideRepository()
                )
            }.also { instance = it }
    }
}