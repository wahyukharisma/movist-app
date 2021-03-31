package com.example.movist.util

object TimeParse {
    fun minToHours(data : Int) : String{
        val hours = data/60
        val minutes = data%60

        return if(hours > 0){
            "$hours h $minutes min"
        }else{
            "$minutes min"
        }
    }
}