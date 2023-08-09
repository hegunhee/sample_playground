package com.hegunhee.sample_playground.feature.secretkeypad

sealed class KeypadType(val name : String) {

    object Register : KeypadType("인증번호 등록")

    data class Check(val currentPassword : String) : KeypadType("인증번호 확인")
}
