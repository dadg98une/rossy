package com.example.rossy.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Forms.FoodsEdit
import com.example.rossy.Objetos.Foods
import com.example.rossy.R
import kotlinx.android.synthetic.main.card_food.view.*

class FoodsAdapter(val foods: MutableList<Foods>): RecyclerView.Adapter<FoodsAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return  FoodViewHolder(LayoutInflater.from(parent.context).inflate
            (R.layout.card_food,parent,false))
    }

    override fun getItemCount() = foods.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]

        holder.view.nameFoodText.text = food.nombre
        holder.view.priceFoodText.text = food.precio.toString()
        holder.view.categoryFoodText.text = food.categoria
        holder.view.idFoodText.text = food.id

    }


    class FoodViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        init {
            view.editFood.setOnClickListener {
                val intent = Intent(view.context, FoodsEdit::class.java)
                intent.putExtra("nombre", view.nameFoodText.text)
                intent.putExtra("price",view.priceFoodText.text)
                intent.putExtra("categoria",view.categoryFoodText.text)
                intent.putExtra("id",view.idFoodText.text)
                view.context.startActivity(intent)
            }
        }
    }
}