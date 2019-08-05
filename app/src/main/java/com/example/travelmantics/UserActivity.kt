package com.example.travelmantics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val recyclerView = findViewById<RecyclerView>(R.id.trips)
        val tripAdapter = TripAdapter(this)
        recyclerView.adapter = tripAdapter

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        recyclerView.layoutManager = linearLayoutManager
    }

    override fun onStart() {
        super.onStart()
        FirebaseUtil.attachListener()
    }

    override fun onStop() {
        super.onStop()
        FirebaseUtil.detachListener()
    }

    fun addTrip(view: View) {
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }

}
