package com.example.agorademo.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val userName = stringPreferencesKey("user_name")
    private val nickname = stringPreferencesKey("nick_name")
    private val userTokenKey = stringPreferencesKey("token")

    suspend fun setUserName(userid: String){
        dataStore.edit { pref -> pref[userName] = userid }
    }

    fun getUserName(): Flow<String?>{
       return dataStore.data.map { prefs -> prefs[userName] }
    }

    suspend fun removeUserName() {
        dataStore.edit { prefs -> prefs.remove(userName) }
    }

    suspend fun setUserToken(token:String){
        dataStore.edit { pref -> pref[userTokenKey] = token }
    }

    fun getUserToken() : Flow<String?> {
        return dataStore.data.map { prefs -> prefs[userTokenKey] }
    }

    suspend fun removeUserToken() {
        dataStore.edit { prefs -> prefs.remove(userTokenKey) }
    }

    suspend fun setNickName(userid: String){
        dataStore.edit { pref -> pref[nickname] = userid }
    }

    fun getNickName(): Flow<String?>{
        return dataStore.data.map { prefs -> prefs[nickname] }
    }

    suspend fun removeNickName() {
        dataStore.edit { prefs -> prefs.remove(nickname) }
    }

    suspend fun removeAll() {
        removeUserName()
        removeUserToken()
        removeNickName()
    }

    suspend fun isLoggedIn(): Boolean {
        return  getUserName().firstOrNull() != null
                && getUserToken().firstOrNull() != null
    }
}