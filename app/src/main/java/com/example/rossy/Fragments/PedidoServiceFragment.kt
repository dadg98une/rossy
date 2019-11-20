package com.example.rossy.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rossy.Objetos.FragmentData
import com.example.rossy.R

class PedidoServiceFragment : Fragment(), FragmentData {

    override val idService: String
        get() = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pedido_services, container, false)
    }
}