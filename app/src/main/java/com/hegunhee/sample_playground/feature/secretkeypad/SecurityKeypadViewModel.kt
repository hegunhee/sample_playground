package com.hegunhee.sample_playground.feature.secretkeypad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SecurityKeypadViewModel @Inject constructor() : ViewModel() {

    private val _keypadNavigation : MutableSharedFlow<KeypadType> = MutableSharedFlow<KeypadType>()
    val keypadNavigation : SharedFlow<KeypadType> = _keypadNavigation.asSharedFlow()

    private val _passwordState : MutableStateFlow<PasswordState> = MutableStateFlow<PasswordState>(PasswordState.Init)
    val passwordState : StateFlow<PasswordState> = _passwordState.asStateFlow()

    private val _toastMessage : MutableSharedFlow<String> = MutableSharedFlow()
    val toastMessage : SharedFlow<String> = _toastMessage.asSharedFlow()

    private val _logList : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val logList : StateFlow<List<String>> = _logList.asStateFlow()

    private val _navigateLogUi : MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateLogUi : SharedFlow<Unit> = _navigateLogUi.asSharedFlow()



    fun onPasswordRegisterButtonClick() {
        viewModelScope.launch {
            _keypadNavigation.emit(KeypadType.Register)
        }
    }

    fun onPasswordCheckButtonClick() = viewModelScope.launch{
        passwordState.value.let { state ->
            when(state){
                is PasswordState.Init -> {
                    _toastMessage.emit("비밀번호가 설정되있지 않습니다")
                }
                is PasswordState.Setting -> {
                    _keypadNavigation.emit(KeypadType.Check(currentPassword = state.password))
                }
            }
        }
    }

    fun onPasswordShowClick() = viewModelScope.launch {
        passwordState.value.let { state ->
            when(state) {
                is PasswordState.Init -> {
                    _toastMessage.emit("비밀번호가 설정되있지 않습니다")
                }
                is PasswordState.Setting -> {
                    _toastMessage.emit("비밀번호는 ${state.password}입니다.")
                }
            }
        }
    }

    fun onNavigateLogClick() = viewModelScope.launch {
        _navigateLogUi.emit(Unit)
    }

    fun setPassword(password : String) {
        _passwordState.value = PasswordState.Setting(password)
    }

    fun addRegisterPasswordLog(password : String) {
        val log = "등록 ${getCurrentDate()} $password"
        _logList.value = _logList.value + log
    }

    fun addCheckPasswordLog(password : String) {
        val log = "확인 ${getCurrentDate()} $password"
        _logList.value = _logList.value + log
    }
    private fun getCurrentDate() : String {
        val simpleDateFormat = SimpleDateFormat("MM:dd HH:mm:ss")
        return simpleDateFormat.format(Date(System.currentTimeMillis()))
    }



}