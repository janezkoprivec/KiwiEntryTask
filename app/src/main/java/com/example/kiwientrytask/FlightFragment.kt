package com.example.kiwientrytask

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_flight.*


class FlightFragment(flight: Flight) : Fragment() {
    private var flight: Flight = flight
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight, container, false)
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
/*
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }*/

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(flight: Flight) : FlightFragment {
            val fragment = FlightFragment(flight)
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageView = view.findViewById<ImageView>(R.id.destination_image)
        val fromTV = view.findViewById<TextView>(R.id.from)
        val toTV = view.findViewById<TextView>(R.id.to)
        val priceTV = view.findViewById<TextView>(R.id.price)

        fromTV.text = flight.cityFrom
        toTV.text = flight.cityTo
        priceTV.text = flight.price+"€"
        val url = "https://images.kiwi.com/photos/472x600/${flight.mapIdto}.jpg"
        val queue = Volley.newRequestQueue(context)

        val request: ImageRequest = ImageRequest(url,
            Response.Listener<Bitmap> {response ->  
                imageView.setImageBitmap(response)
            }, 0, 0, null,
            Response.ErrorListener {  }
            )
        queue.add(request)
    }
}
