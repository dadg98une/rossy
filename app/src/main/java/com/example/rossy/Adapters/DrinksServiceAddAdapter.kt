package com.example.rossy.Adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Objetos.Drinks
import com.example.rossy.Objetos.FragmentData
import com.example.rossy.R
import kotlinx.android.synthetic.main.add_drink_dialog.view.*
import kotlinx.android.synthetic.main.add_drink_service_record.view.*

class DrinksServiceAddAdapter (val drinks: MutableList<Drinks>): RecyclerView.Adapter<DrinksServiceAddAdapter.DrinkViewHolder>(), FragmentData{
    override val idService: String
        get() = ""

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
                val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.add_drink_dialog,null)
                val mBuilder = AlertDialog.Builder(view.context).setView(mDialogView).setTitle("Agregar Bebida")
                val mAlertDialog = mBuilder.show()
                //val db = FirebaseFirestore.getInstance()

                var idFood = view.drinkIDAddService.text.toString()
                var nombre = view.drinkNameAddService.text.toString()

                mDialogView.dialogNameDrink.setText(nombre)

                mDialogView.dialogAddDrink.setOnClickListener {

                    nombre = mDialogView.dialogNameDrink.text.toString()

                    if (mDialogView.dialogSH.isChecked) {
                        nombre = "$nombre /Sin Hielo"
                    }
                    if (mDialogView.dialogCH.isChecked) {
                        nombre = "$nombre /Con Hielo"
                    }

                    Toast.makeText(view.context,"$nombre : Agregado al pedido : ID $idFood", Toast.LENGTH_LONG).show()

//                    val washingtonRef = db?.collection("ordenes").document("ID")
//
//                    // Atomically add a new region to the "regions" array field.
//                    washingtonRef.update("regions", FieldValue.arrayUnion("greater_virginia"))



                    mAlertDialog.dismiss()
                }

                //cancelar
                mDialogView.dialogCancelDrink.setOnClickListener {
                    mAlertDialog.dismiss()
                    Toast.makeText(view.context,"Bebida no agregada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}