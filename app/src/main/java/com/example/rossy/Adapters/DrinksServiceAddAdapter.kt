package com.example.rossy.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Forms.ServiceAdd
import com.example.rossy.Objetos.Drinks
import com.example.rossy.R
import kotlinx.android.synthetic.main.add_drink_service_record.view.*

class DrinksServiceAddAdapter (val drinks: MutableList<Drinks>): RecyclerView.Adapter<DrinksServiceAddAdapter.DrinkViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.add_drink_service_record,parent,false
        ))
    }

    override fun getItemCount() = drinks.size

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinks[position]

        holder.view.drinkNameAddService.text = drink.nombre
        holder.view.drinkPriceAddService.text = drink.precio.toString()
        holder.view.drinkCategoryAddService.text = drink.tamaño.toString()
        holder.view.drinkIDAddService.text = drink.id
    }

    class DrinkViewHolder(val view: View):RecyclerView.ViewHolder(view){
        init{
            view.setOnClickListener {
                val intent = Intent(view.context,ServiceAdd::class.java)
                intent.putExtra("nombre",view.drinkNameAddService.text)
                intent.putExtra("size", view.drinkCategoryAddService.text)
                intent.putExtra("price", view.drinkPriceAddService.text)
                intent.putExtra("id", view.drinkIDAddService.text)
                view.context.startActivity(intent)
            }
        }
    }
}