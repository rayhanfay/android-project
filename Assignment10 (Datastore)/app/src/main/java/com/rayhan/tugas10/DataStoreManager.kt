package com.rayhan.tugas10

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class DataStoreManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(name = "settings")

    val dataStore = context.dataStore
}