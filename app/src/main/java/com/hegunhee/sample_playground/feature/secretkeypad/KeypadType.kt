package com.hegunhee.sample_playground.feature.secretkeypad

sealed class KeypadType() {

    data class Register(val name : String = "인증번호 등록") : KeypadType()

    data class Check(val name : String = "인증번호 확인", val currentPassword : String) : KeypadType()
}
