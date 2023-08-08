package com.hegunhee.sample_playground.feature.secretkeypad.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeypadViewModel @Inject constructor() : ViewModel(), KeypadActionHandler {

    private val keypadDelIndex = 9
    private val keypadDel = "del"

    private val _keypad : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val keypad : StateFlow<List<String>> = _keypad.asStateFlow()

    private val _password : MutableStateFlow<String> = MutableStateFlow("")
    val password : StateFlow<String> = _password.asStateFlow()

    private val isPasswordTextSealed : MutableStateFlow<Boolean> = MutableStateFlow(false)

    var passwordText : StateFlow<String> = MutableStateFlow("")

    fun fetchData() {
        fetchKeypad()
        combineData()
    }

    private fun fetchKeypad() {
        _keypad.value = getKeypad()
    }

    private fun getKeypad() : List<String> {
        return mutableListOf<String>().apply {
            addAll((0..9).map { it.toString() })
            shuffle()
            add(keypadDelIndex,"del")
        }
    }

    private fun combineData() = viewModelScope.launch {
        passwordText = password.combine(isPasswordTextSealed) { pw, sealed ->
            return@combine if (sealed) {
                pw.map { '*' }.toString()
            } else {
                pw
            }
        }.stateIn(viewModelScope)
    }
    override fun onClickKeypad(keypad: String) {
        val currentPassword = password.value
        if(keypad == keypadDel) {
            if(currentPassword.isNotBlank()){
                _password.value = currentPassword.substring(0,password.value.length-1)
            }
        }else{
            _password.value = currentPassword + keypad
        }
    }
}