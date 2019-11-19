package com.example.rossy.Adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Objetos.Foods
import com.example.rossy.R
import kotlinx.android.synthetic.main.add_food_dialog.view.*
import kotlinx.android.synthetic.main.add_food_service_record.view.*

class FoodsServiceAddAdapter(val foods: MutableList<Foods>): RecyclerView.Adapter<FoodsServiceAddAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return  FoodViewHolder(LayoutInflater.from(parent.context).inflate
            (R.layout.add_food_service_record,parent,false))
    }

    override fun getItemCount() = foods.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]

        holder.view.foodNameAddService.text = food.nombre
        holder.view.foodPriceAddService.text = food.precio.toString()
        holder.view.foodCategoryAddService.text = food.categoria
        holder.view.foodIDAddService.text = food.id

    }


    class FoodViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.add_food_dialog,null)
                val mBuilder = AlertDialog.Builder(view.context).setView(mDialogView).setTitle("Agregar Comida")
                val mAlertDialog = mBuilder.show()

                var nombre = view.foodNameAddService.text.toString()
                mDialogView.dialogNameFood.setText(nombre)

                mDialogView.dialogAddFood.setOnClickListener {

                    nombre = mDialogView.dialogNameFood.text.toString()

                    if (mDialogView.dialogSC.isChecked) {
                        nombre = "$nombre /Sin Cebolla"
                    }
                    if (mDialogView.dialogNatural.isChecked) {
                        nombre = "$nombre /Natural"
                    }
                    if (mDialogView.dialogTodo.isChecked) {
                        nombre = "$nombre /Con Todo"
                    }

                    Toast.makeText(view.context,"$nombre : Agregado al pedido",Toast.LENGTH_LONG).show()

                    mAlertDialog.dismiss()
                }

                //cancelar
                mDialogView.dialogCancelFood.setOnClickListener {
                    mAlertDialog.dismiss()
                    Toast.makeText(view.context,"Alimento no agregado",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}