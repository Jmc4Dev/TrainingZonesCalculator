package com.training.trainingzonescalculator

import android.app.Application
import com.google.android.gms.ads.MobileAds

class TrainingZonesCalculatorapp:Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}