package com.dynamitos.mediacloud.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    // not really UI data, but needs to be passed to Activity for storing it in SharedPreferences
    val authToken: String
    //... other data fields that may be accessible to the UI
)