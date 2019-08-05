package com.example.travelmantics

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class Travelamntics:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}