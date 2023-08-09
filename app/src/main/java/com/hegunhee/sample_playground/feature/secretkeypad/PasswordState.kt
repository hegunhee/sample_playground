package com.hegunhee.sample_playground.feature.secretkeypad

sealed class PasswordState {

    object Init : PasswordState()

    data class Setting(val password : String) : PasswordState()
}
