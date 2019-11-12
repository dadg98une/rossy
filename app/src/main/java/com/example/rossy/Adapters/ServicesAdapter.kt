package com.example.rossy.Adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Objetos.Services
import kotlinx.android.synthetic.main.card_service.view.*

class ServicesAdapter (val services: MutableList<Services>): RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = services.size

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        val services =  services[position]

        holder.view.mesaName.text = services.mesa

    }

    class ServicesViewHolder(val view: View): RecyclerView.ViewHolder(view){
        init {

        }
    }
}