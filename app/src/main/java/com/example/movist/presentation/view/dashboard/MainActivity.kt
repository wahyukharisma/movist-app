package com.example.movist.presentation.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        with(_binding){

        }
    }
}