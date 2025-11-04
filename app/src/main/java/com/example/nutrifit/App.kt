package com.example.nutrifit

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class NutriFitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Khởi tạo ThreeTen AndroidBackport
        AndroidThreeTen.init(this)
    }
}