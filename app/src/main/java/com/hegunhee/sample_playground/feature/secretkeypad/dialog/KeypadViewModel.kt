package com.hegunhee.sample_playground.feature.secretkeypad.dialog

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KeypadViewModel @Inject constructor() : ViewModel() {

    private val _keypad : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val keypad : StateFlow<List<String>> = _keypad.asStateFlow()

    private val delIndex = 9


    fun fetchKeypad() {
        val keypadList = mutableListOf<String>().apply {
            addAll((0..9).map { it.toString() })
            shuffle()
            add(delIndex,"del")
        }
        _keypad.value = keypadList.toList()
    }
}