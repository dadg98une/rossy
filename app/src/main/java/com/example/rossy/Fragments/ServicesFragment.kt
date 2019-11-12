package com.example.rossy.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rossy.Forms.ServiceAdd
import com.example.rossy.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ServicesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_services, container, false)
        val addServiceButton = v.findViewById<FloatingActionButton>(R.id.addService)
        addServiceButton.setOnClickListener {
            addService()
        }
        return v
    }

    private fun addService (){
        startActivity(Intent(this@ServicesFragment.context, ServiceAdd::class.java))
    }
}
