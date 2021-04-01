package com.example.movist.presentation.view.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.movist.databinding.ActivitySplashScreenBinding
import com.example.movist.presentation.view.dashboard.DashboardActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var _binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        val r = Runnable {
            finish()
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        Handler(Looper.getMainLooper()).postDelayed(r, 3000)

    }
}