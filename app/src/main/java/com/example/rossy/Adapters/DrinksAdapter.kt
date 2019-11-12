package com.example.rossy.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Forms.DrinkEdit
import com.example.rossy.Objetos.Drinks
import com.example.rossy.R
import kotlinx.android.synthetic.main.card_drink.view.*

class DrinksAdapter (val drinks: MutableList<Drinks>): RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.card_drink,parent,false
        ))
    }

    override fun getItemCount() = drinks.size

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinks[position]

        holder.view.nameDrinkText.text = drink.nombre
        holder.view.priceDrinkText.text = drink.precio.toString()
        holder.view.sizeDrinkText.text = drink.tamaño.toString()
        holder.view.idDrinkText.text = drink.id
    }

    class DrinkViewHolder(val view: View):RecyclerView.ViewHolder(view){
        init{
            view.editDrink.setOnClickListener {
                val intent = Intent(view.context,DrinkEdit::class.java)
                intent.putExtra("nombre",view.nameDrinkText.text)
                intent.putExtra("size", view.sizeDrinkText.text)
                intent.putExtra("price", view.priceDrinkText.text)
                intent.putExtra("id", view.idDrinkText.text)
                view.context.startActivity(intent)
            }
        }
    }
}