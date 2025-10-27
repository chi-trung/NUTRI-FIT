package com.example.nutrifit

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Khởi tạo ThreeTenABP cho toàn bộ ứng dụng
        AndroidThreeTen.init(this)
    }
}