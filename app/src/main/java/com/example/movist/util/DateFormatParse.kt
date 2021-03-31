package com.example.movist.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatParse {
    private val locale = Locale("en", "UK")

    private val dateDayWithMonth = SimpleDateFormat("MMMM, dd yyyy", locale)
    private val dateOnly = SimpleDateFormat("yyyy-MM-dd", locale)
    private val dateReview = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale)

    fun dateWithDay(time: String) : String {
        val dateParse = dateOnly.parse(time)
        return dateDayWithMonth.format(dateParse!!)
    }

    fun dateWithDayReview(time: String) : String {
        val dateParse = dateReview.parse(time)
        return dateDayWithMonth.format(dateParse!!)
    }
}