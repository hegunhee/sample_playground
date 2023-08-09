package com.hegunhee.sample_playground.feature.secretkeypad.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.sample_playground.feature.secretkeypad.KeypadType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeypadViewModel @Inject constructor() : ViewModel(), KeypadActionHandler {

    private val keypadDelIndex = 9
    private val keypadDel = "del"

    private val _keypadType : MutableStateFlow<KeypadType> = MutableStateFlow(KeypadType.Register)
    val keypadType : StateFlow<KeypadType> = _keypadType.asStateFlow()

    private val _keypad : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val keypad : StateFlow<List<String>> = _keypad.asStateFlow()

    private val _password : MutableStateFlow<String> = MutableStateFlow("")
    val password : StateFlow<String> = _password.asStateFlow()

    private val isPasswordTextSealed : MutableStateFlow<Boolean> = MutableStateFlow(false)

    var passwordText : StateFlow<String> = MutableStateFlow("")

    private val _matchedPassword : MutableSharedFlow<String> = MutableSharedFlow()
    val matchedPassword : SharedFlow<String> = _matchedPassword.asSharedFlow()

    private val _toastMessage : MutableSharedFlow<String> = MutableSharedFlow()
    val toastMessage : SharedFlow<String> = _toastMessage

    fun fetchData(type : KeypadType) {
        _keypadType.value = type
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
                "*".repeat(pw.length)
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
            if((currentPassword + keypad).length == 6){
                setMatchedPassword(currentPassword + keypad)
            }

        }
    }

    private fun setMatchedPassword(password : String) {
        val type = keypadType.value
        viewModelScope.launch {
            when(type) {
                is KeypadType.Register -> {
                    _toastMessage.emit("비밀번호가 ${password}로 설정되었습니다")
                    _matchedPassword.emit(password)
                }
                is KeypadType.Check -> {
                    if(password == type.currentPassword){
                        _toastMessage.emit("인증번호가 맞습니다.")
                        _matchedPassword.emit(password)
                    }else{
                        _password.value = ""
                        _toastMessage.emit("인증번호가 틀립니다.")
                    }
                }
            }
        }

    }

    fun onClickSealedPassword() {
        isPasswordTextSealed.value = !isPasswordTextSealed.value
    }

    fun onClickPasswordClear() {
        _password.value = ""
    }
}