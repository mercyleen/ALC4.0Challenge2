package com.example.travelmantics

import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class TripAdapter(val caller: AppCompatActivity) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    var mTrips: ArrayList<Trip>
    var mFirebaseDatabase: FirebaseDatabase
    var mDatabaseReference: DatabaseReference
    var mChildEventListener: ChildEventListener

    init {
        FirebaseUtil.openDB("trips", caller)
        mFirebaseDatabase = FirebaseUtil.firebaseDb
        mDatabaseReference = FirebaseUtil.databaseReference

        mTrips = FirebaseUtil.mTrips

        mChildEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                var trip = dataSnapshot.getValue(Trip::class.java)
                trip!!.id = dataSnapshot.key!!
                mTrips.add(trip)
                notifyItemInserted(mTrips.size - 1)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        mDatabaseReference.addChildEventListener(mChildEventListener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent)
        )
    }

    override fun getItemCount(): Int {
        return mTrips.size
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = mTrips[position]
        holder.bind(trip)
    }

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val image: ImageView = itemView.findViewById(R.id.image)

        fun bind(trip: Trip) {
            title.setText(trip.title)
            description.setText(trip.description)
            amount.setText(trip.amount)

            Glide.with(itemView.context)
                .load(trip.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.fui_ic_mail_white_24dp)
                .into(image);
        }
    }

}