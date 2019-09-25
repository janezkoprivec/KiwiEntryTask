package com.example.kiwientrytask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readData()
    }

    fun readData(){
        val queue = Volley.newRequestQueue(this)

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        val dateFrom = sdf.format(cal.time)
        cal.add(Calendar.DAY_OF_MONTH, 2)
        val dateTo = sdf.format(cal.time)
        val url = "https://api.skypicker.com/flights?v=2&sort=popularity&asc=0&locale=en&affilid=&children=0&infants=0&flyFrom=49.2-16.61-250km&to=anywhere&featureName=aggregateResults&dateFrom=$dateFrom&dateTo=$dateTo&typeFlight=oneway&returnFrom=&returnTo=&one_per_date=0&oneforcity=1&wait_for_refresh=0&adults=1&limit=7&partner=janezkoprivec"

        val request = StringRequest(Request.Method.GET, url,
            Response.Listener<String> {response ->
                val result = response.toString()
                var jo = JSONObject(result)
                Log.e("LENGHT", jo.getJSONArray("data").length().toString())
                start(jo)
            }, Response.ErrorListener {  })

        queue.add(request)
    }

    fun start(jo: JSONObject){
        val ja = jo.getJSONArray("data")
        val flights = flightsFromJA(ja)

        Log.e("flights", flights.size.toString())
        val pager = findViewById<ViewPager>(R.id.viewpager)
        val manager = supportFragmentManager

        for(flight in flights){
            Log.e("flight", "from ${flight.cityFrom} to ${flight.cityTo}")
        }

        val flightsAdapter = FlightsPagerAdapter(manager, flights)
        pager.adapter = flightsAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(pager)


    }

    fun flightsFromJA(ja: JSONArray): ArrayList<Flight>{
        val flights = ArrayList<Flight>()

        for (i in 0 until (5)){
            val jo = ja.getJSONObject(i)
            val cityTo = jo.getString("cityTo")
            val cityFrom = jo.getString("cityFrom")
            val mapIdto = jo.getJSONArray("route").getJSONObject(0).getString("mapIdto")
            val price = jo.getString("price")
            val flight = Flight(cityFrom, cityTo, mapIdto, price)
            flights.add(flight)
        }

        return flights
    }
}
