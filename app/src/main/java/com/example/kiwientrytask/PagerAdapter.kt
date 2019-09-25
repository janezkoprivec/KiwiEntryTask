package com.example.kiwientrytask

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FlightsPagerAdapter(fragmentManager: FragmentManager, private val flights: ArrayList<Flight>) : FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        return FlightFragment.newInstance(flights[position])
    }

    override fun getCount(): Int {
        return flights.size
    }
}