package com.dynamitos.mediacloud.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val authToken: String,
    val displayName: String
)