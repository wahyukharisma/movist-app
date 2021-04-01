package com.example.movist.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateFormatParseTest{
    @Test
    fun whenInputIsValid(){
        val date = "2021-03-28"
        val result = DateFormatParse.dateWithDay(date)

        assertThat(result).isEqualTo("March, 28 2021")
    }

    @Test
    fun whenInputIsValidReview(){
        val date = "2021-03-07T15:46:34.342Z"
        val result = DateFormatParse.dateWithDayReview(date)

        assertThat(result).isEqualTo("March, 07 2021")
    }
}