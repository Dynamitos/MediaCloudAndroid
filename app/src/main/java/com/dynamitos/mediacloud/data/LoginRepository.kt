package com.dynamitos.mediacloud.data

import android.content.Context
import android.content.SharedPreferences
import com.dynamitos.mediacloud.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository private constructor(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    fun attemptLoginFromPrefs(prefs: SharedPreferences): Boolean{
        val authToken = prefs.getString("auth_token", "")
        val displayName = prefs.getString("display_name", "")
        if(authToken != "" && displayName != ""){
            setLoggedInUser(LoggedInUser(authToken!!, displayName!!))
            return true
        }
        return false
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    companion object {
        @Volatile
        private var instance: LoginRepository? = null

        fun getInstance(): LoginRepository {
            return instance ?: synchronized(this) {
                instance ?: LoginRepository(LoginDataSource()).also { instance = it }
            }
        }
    }
}