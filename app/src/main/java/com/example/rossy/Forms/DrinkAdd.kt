package com.example.rossy.Forms

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_drink_add.*

class DrinkAdd : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var nombre: String? = null
    private var tamaño: Int? = null
    private var precio: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_add)
        db = FirebaseFirestore.getInstance()
    }

    fun addDrink(view: View) {
        if (TextUtils.isEmpty(nameAddDrink.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un nombre a la bebida", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(priceAddDrink.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa el precio", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(sizeAddDrink.toString())) {
            Toast.makeText(applicationContext, "Ingrese un tamaño en mL de la bebida", Toast.LENGTH_SHORT).show()
            return
        }


        nombre = nameAddDrink.text.toString()
        precio = priceAddDrink.text.toString().toDoubleOrNull()
        tamaño = sizeAddDrink.text.toString().toIntOrNull()


        val table = hashMapOf(
            "Nombre" to nombre,
            "Precio" to precio!!.toDouble(),
            "Tamaño" to tamaño!!.toInt()
        )

        db?.collection("menu")?.document("alimentos")?.collection("bebida")?.add(table)?.addOnSuccessListener {
                documentReference ->
            Log.d(TAG,"Bebida añadida con la ID: ${documentReference.id}")
            Toast.makeText(this,"Mesa añadidad correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }?.addOnFailureListener { e ->
            Log.w(TAG,"Error al añadir Bebida",e)
        }


    }

    fun cancel(view: View){
        finish()
    }

    companion object {
        private val TAG = DrinkAdd::class.java.getSimpleName()
    }
}
