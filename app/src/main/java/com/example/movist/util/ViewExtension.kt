package com.example.movist.util

import android.app.Activity
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.movist.R
import www.sanju.motiontoast.MotionToast

/*
 * This extension used for show view
 */
fun View.show(){
    this.visibility = View.VISIBLE
}

/*
 * This extension used for remove view
 */
fun View.remove(){
    this.visibility = View.GONE
}


fun Activity.showMotionSuccess(title: String, subtitle: String){
    MotionToast.createColorToast(this,
        title,
        subtitle,
        MotionToast.TOAST_SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.poppins_medium))
}

fun Activity.showMotionWarning(title: String, subtitle: String){
    MotionToast.createColorToast(this,
        title,
        subtitle,
        MotionToast.TOAST_WARNING,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.poppins_medium))
}