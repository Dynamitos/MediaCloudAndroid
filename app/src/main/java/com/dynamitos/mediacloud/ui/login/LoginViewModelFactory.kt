package com.dynamitos.mediacloud.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dynamitos.mediacloud.data.MediaCloudDataSource
import com.dynamitos.mediacloud.data.MediaCloudRepository
import com.dynamitos.mediacloud.network.APIClient
import com.dynamitos.mediacloud.network.MediaCloudAPIService

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                mediaCloudRepository = MediaCloudRepository(
                    dataSource = MediaCloudDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}