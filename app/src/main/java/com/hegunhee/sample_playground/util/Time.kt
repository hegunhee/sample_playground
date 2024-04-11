package com.hegunhee.sample_playground.util

import java.time.LocalDateTime
import java.time.ZoneId

object Time {

    private val currentTime = LocalDateTime.now()

    fun toTimeMills(second : Long) : Long{
        return currentTime.plusSeconds(second).atZone(ZoneId.systemDefault()).toInstant().epochSecond
    }

    fun toTimeMills(hour : Int,minute : Int) : Long {
        return LocalDateTime.now().withHour(hour).withMinute(minute).atZone(ZoneId.systemDefault()).toInstant().epochSecond
    }

    fun getTimeList(): List<String> {
        return (0..23).flatMap { hour ->
            listOf("%d:00".format(hour), "%d:15".format(hour), "%d:30".format(hour), "%d:45".format(hour))
        }
    }
}