package com.happyadda.jaleb.data.game.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.happyadda.jaleb.utils.NIG_LINK_PREFERENCES
import kotlinx.coroutines.flow.map


class NigLinkPreferences(private val context: Context) {
    companion object {
        val Context.nigLinkPreferences by preferencesDataStore(name = NIG_LINK_PREFERENCES)
        val NIG_LINK_KEY = stringPreferencesKey("NIG_LINK_KEY")
    }

    val nigGetLink = context.nigLinkPreferences.data.map { preferences ->
        preferences[NIG_LINK_KEY] ?: ""
    }

    suspend fun nigSaveLink(value: String) {
        context.nigLinkPreferences.edit { preferences ->
            preferences[NIG_LINK_KEY] = value
        }
    }
}