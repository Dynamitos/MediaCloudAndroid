package com.dynamitos.musiccloudandroid.data

import android.util.Log
import com.dynamitos.musiccloudandroid.data.model.LoggedInUser
import khttp.post
import khttp.responses.Response
import khttp.structures.authorization.Authorization
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class MediaCloudSource {
    private val _hostname = "http://10.0.2.2:30000";
    private val TAG : String = "MediaCloudSource";
    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val response : Response = post(
                    url = "$_hostname/signin",
                    json = mapOf("username" to username, "password" to password))
            if(response.text == "28P01")
            {
                return Result.Error(IOException("Wrong username/password combination"))
            }
            val loggedIn = LoggedInUser(response.text, username)
            return Result.Success(loggedIn)
        } catch (e: Throwable) {
            Log.e(TAG, "Io Exception")
            e.printStackTrace()
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    fun register(username: String, password: String): Result<LoggedInUser> {
        try {
            val response : Response = post(
                    url = "$_hostname/signup",
                    json = mapOf("username" to username, "password" to password))
            if(response.text == "23505")
            {
                Log.v(TAG, "Failed to sign up")
                return Result.Error(IOException("User already exists"))
            }
            val loggedIn = LoggedInUser(response.text, username)
            Log.v(TAG, "Successfully registered $username")
            return Result.Success(loggedIn)
        } catch (e: Throwable) {
            Log.e(TAG, "Io Exception")
            e.printStackTrace()
            return Result.Error(IOException("Error logging in", e))
        }
    }
}