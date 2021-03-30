package com.example.movist.util

import android.view.View

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

/*
 * This extension used for hide view
 */
fun View.hide(){
    this.visibility = View.INVISIBLE
}