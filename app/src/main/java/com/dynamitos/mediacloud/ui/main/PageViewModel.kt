package com.dynamitos.mediacloud.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel(private val mediacloudRepository: MediaCloudRe) {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }
}