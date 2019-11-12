package com.example.rossy.Forms

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_food_add.*

class FoodsAdd : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var nombre: String? = null
    private var categoria: String? = null
    private var precio: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_add)

        db = FirebaseFirestore.getInstance()

        val spinner = findViewById<Spinner>(R.id.addFoodSpinner)
        val adapter3 = ArrayAdapter.createFromResource(this,R.array.seccionesComida,android.R.layout.simple_spinner_item)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter3
    }

    fun addFood(view: View) {
        if (TextUtils.isEmpty(nameAddFood.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un nombre a la comida", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(priceAddFood.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa el precio", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(addFoodSpinner.toString())) {
            Toast.makeText(applicationContext, "Escoja la categoria del alimento", Toast.LENGTH_SHORT).show()
            return
        }


        nombre = nameAddFood.text.toString()
        precio = priceAddFood.text.toString().toIntOrNull()
        categoria = addFoodSpinner.selectedItem.toString()


        val table = hashMapOf(
            "Nombre" to nombre,
            "Precio" to precio!!.toDouble(),
            "Categoria" to categoria
        )

        db?.collection("menu")?.document("alimentos")?.collection("comida")?.add(table)?.addOnSuccessListener {
                documentReference ->
            Log.d(TAG,"Mesa añadida con la ID: ${documentReference.id}")
            Toast.makeText(this,"Mesa añadidad correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }?.addOnFailureListener { e ->
            Log.w(TAG,"Error al añadir mesa",e)
        }


    }


    fun cancel(view: View){
        finish()
    }

    companion object {
        private val TAG = FoodsAdd::class.java.getSimpleName()
    }

}
