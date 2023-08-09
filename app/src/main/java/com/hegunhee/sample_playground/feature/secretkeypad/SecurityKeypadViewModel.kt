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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityKeypadViewModel @Inject constructor() : ViewModel() {

    private val _keypadNavigation : MutableSharedFlow<KeypadType> = MutableSharedFlow<KeypadType>()
    val keypadNavigation : SharedFlow<KeypadType> = _keypadNavigation.asSharedFlow()

    private val _passwordState : MutableStateFlow<PasswordState> = MutableStateFlow<PasswordState>(PasswordState.Init)
    val passwordState : StateFlow<PasswordState> = _passwordState.asStateFlow()

    fun onPasswordRegisterButtonClick() {
        viewModelScope.launch {
            _keypadNavigation.emit(KeypadType.Register)
        }
    }

    fun onPasswordCheckButtonClick() = viewModelScope.launch{
        passwordState.value.let { state ->
            when(state){
                is PasswordState.Init -> {

                }
                is PasswordState.Setting -> {
                    _keypadNavigation.emit(KeypadType.Check(currentPassword = state.password))
                }
            }
        }
    }

    fun setPassword(password : String) {
        _passwordState.value = PasswordState.Setting(password)
    }

}