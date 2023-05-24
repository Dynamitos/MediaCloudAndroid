package com.dynamitos.mediacloud.data

import com.dynamitos.mediacloud.data.model.LoggedInUser
import com.dynamitos.mediacloud.network.APIClient
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource() {
    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val authToken = APIClient.getInstance().apiService.login(username, password)
            //val authToken = "271bc5b649181d96cb7d871a3e30eb5cbad7b91e526be9bcefddda6b5dc5b9c56706cf5152143ffcc49753566e6052968e90e4ddb44ff4a8500f1a38b1d6e4e2";
            val user = LoggedInUser(authToken, username)
            Result.Success(user)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

}