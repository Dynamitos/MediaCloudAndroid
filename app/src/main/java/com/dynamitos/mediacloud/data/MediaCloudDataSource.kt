package com.dynamitos.mediacloud.data

import com.dynamitos.mediacloud.data.model.LoggedInUser
import com.dynamitos.mediacloud.data.model.UserImageList
import com.dynamitos.mediacloud.network.MediaCloudAPI
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class MediaCloudDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val authToken = MediaCloudAPI.apiService.login(username, password);
            val user = LoggedInUser(authToken, username)
            Result.Success(user)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun getImageList(username: String): Result<UserImageList> {
        return try {
            val images = MediaCloudAPI.apiService.getImages(username)
            Result.Success(images)
        } catch (e: Throwable) {
            Result.Error(IOException("Error retrieving images", e))
        }
    }
}