package com.pplog.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    private object Keys {
        val CLOUD_BACKUP = booleanPreferencesKey("cloud_backup_enabled")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    val cloudBackupEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.CLOUD_BACKUP] ?: false
    }

    suspend fun setCloudBackupEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.CLOUD_BACKUP] = enabled
        }
    }

    val darkThemeEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.DARK_THEME] ?: false
    }

    suspend fun setDarkThemeEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.DARK_THEME] = enabled
        }
    }
}
