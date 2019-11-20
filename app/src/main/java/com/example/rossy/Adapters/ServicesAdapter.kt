package com.example.rossy.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Forms.ServiceEdit
import com.example.rossy.Forms.ServiceFinish
import com.example.rossy.Objetos.Services
import com.example.rossy.R
import kotlinx.android.synthetic.main.card_service.view.*

class ServicesAdapter (val services: MutableList<Services>): RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        return ServicesViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.card_service,parent,false
        ))
    }

    override fun getItemCount() = services.size

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        val services =  services[position]

        holder.view.mesaName.text = services.mesa
        holder.view.mesaArea.text = services.area
        holder.view.serverName.text = services.servidor
        holder.view.serviceID.text = services.id
    }

    class ServicesViewHolder(val view: View): RecyclerView.ViewHolder(view){
        init {
            view.entregarOrden.setOnClickListener {
                val intent = Intent (view.context, ServiceFinish::class.java)
                intent.putExtra("id", view.serviceID.text)
                intent.putExtra("mesa", view.mesaName.text)
                intent.putExtra("area", view.mesaArea.text)
                intent.putExtra("servidor", view.serverName.text)
                view.context.startActivity(intent)
            }
            view.verOrden.setOnClickListener {
                val intent = Intent (view.context, ServiceEdit::class.java)
                intent.putExtra("id", view.serviceID.text)
                intent.putExtra("mesa", view.mesaName.text)
                intent.putExtra("area", view.mesaArea.text)
                intent.putExtra("servidor", view.serverName.text)
                view.context.startActivity(intent)
            }
        }
    }
}