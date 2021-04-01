package com.example.movist.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TimeParseTest{

    @Test
    fun whenInputIsValid(){
        val data = 100
        val result = TimeParse.minToHours(data)

        assertThat(result).isEqualTo("1 h 40 min")
    }

    @Test
    fun whenInputIsValidOnlyMinutes(){
        val data = 50
        val result = TimeParse.minToHours(data)

        assertThat(result).isEqualTo("50 min")
    }

    @Test
    fun whenInputIsValidMinus(){
        val data = -1
        val result = TimeParse.minToHours(data)

        assertThat(result).isEqualTo("0 min")
    }
}