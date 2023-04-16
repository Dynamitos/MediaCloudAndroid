package com.dynamitos.mediacloud.data

import com.dynamitos.mediacloud.data.model.LoggedInUser
import com.dynamitos.mediacloud.data.model.UserImageList
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource() {
    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val authToken = apiService.login(username, password);
            val user = LoggedInUser(authToken, username)
            Result.Success(user)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

}