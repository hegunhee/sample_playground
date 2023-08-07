package com.hegunhee.sample_playground.feature.secretkeypad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityKeypadViewModel @Inject constructor() : ViewModel() {

    private val _keypadNavigation : MutableSharedFlow<KeypadType> = MutableSharedFlow<KeypadType>()
    val keypadNavigation : SharedFlow<KeypadType> = _keypadNavigation.asSharedFlow()

    fun onPasswordRegisterButtonClick() {
        viewModelScope.launch {
            _keypadNavigation.emit(KeypadType.Register())
        }
    }

}