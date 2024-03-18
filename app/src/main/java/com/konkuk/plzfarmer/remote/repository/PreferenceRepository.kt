package com.konkuk.plzfarmer.remote.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.konkuk.plzfarmer.presentation.login.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class PreferenceRepository(context: Context) {
    private val dataStore = context.dataStore

    private object PreferenceKeys {
        val MEMBER_NICKNAME = stringPreferencesKey("member_nickname")
        val MEMBER_IMAGE = stringPreferencesKey("member_image")
        val FIRST_DATE = stringPreferencesKey("first_date")
        val IS_FIRST_INSTALL = booleanPreferencesKey("is_first_install")
    }

    suspend fun saveIsFirstInstall() {
        dataStore.edit {
            it[PreferenceKeys.IS_FIRST_INSTALL] = false
        }
    }

    suspend fun getIsFirstInstall() = dataStore.data.map {
        it[PreferenceKeys.IS_FIRST_INSTALL]
    }.firstOrNull()

    suspend fun saveFirstDate(date: String){
        dataStore.edit {
            Log.d("Pref","saveFirstDate")
            it[PreferenceKeys.FIRST_DATE] = date
        }
    }

    suspend fun getFirstDate() = dataStore.data.map {
        Log.d("Pref","getFirstDate")
        it[PreferenceKeys.FIRST_DATE]
    }.firstOrNull()

    suspend fun saveMemberNickname(nickname: String) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.MEMBER_NICKNAME] = nickname
        }
    }

    suspend fun getMemberNickname() = dataStore.data.map { pref ->
        pref[PreferenceKeys.MEMBER_NICKNAME]
    }.first()
}