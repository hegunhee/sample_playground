package com.hegunhee.sample_playground.util

import java.time.LocalDateTime
import java.time.ZoneId

object Time {

    private val currentTime = LocalDateTime.now()

    fun toTimeMills(second : Long) : Long{
        return currentTime.plusSeconds(second).atZone(ZoneId.systemDefault()).toInstant().epochSecond
    }
}