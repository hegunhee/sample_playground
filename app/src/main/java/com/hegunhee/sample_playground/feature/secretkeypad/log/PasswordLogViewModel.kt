package com.hegunhee.sample_playground.feature.secretkeypad.log

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PasswordLogViewModel @Inject constructor() : ViewModel(){

    private val _logList : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val logList : StateFlow<List<String>> = _logList.asStateFlow()

    fun fetchData(logList : List<String>) {
        _logList.value = logList
    }
}