package com.example.movist.util

object TimeParse {
    /**
     * Converting minutes to hour
     *
     * Example input : 100
     *
     * Example output : 1 h 40 min
     *
     * @param data variable represent minute
     */
    fun minToHours(data : Int) : String{
        var time = data

        if(data < 0){
            time = 0
        }

        val hours = time/60
        val minutes = time%60

        return if(hours > 0){
            "$hours h $minutes min"
        }else{
            "$minutes min"
        }
    }
}