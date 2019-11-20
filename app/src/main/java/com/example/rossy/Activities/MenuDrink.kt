package com.example.rossy.Activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rossy.Adapters.DrinksAdapter
import com.example.rossy.Forms.DrinkAdd
import com.example.rossy.Objetos.Drinks
import com.example.rossy.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_menu_drink.*
import kotlinx.android.synthetic.main.card_drink.*

class MenuDrink : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var docRef: CollectionReference? = null
    private var listener: ListenerRegistration? = null
    private val drinks = mutableListOf<Drinks>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_drink)

        db = FirebaseFirestore.getInstance()
        docRef= db?.collection("menu")?.document("alimentos")?.collection("bebida")

        update()

        drinks?.removeAll(drinks)

        recyclerDrink?.layoutManager = LinearLayoutManager(this)
        recyclerDrink?.adapter = DrinksAdapter(drinks)


    }

    private fun update(){
        drinks?.clear()

        listener = docRef?.addSnapshotListener { value, e->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            drinks?.clear()
            recyclerDrink.adapter?.notifyDataSetChanged()

            for (doc in value!!.documentChanges) {

                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        drinks?.removeAll(drinks)                                                     //DE AQUI CHECAR LISTAS Y DEMAS ELEMENTOS
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val size = docs?.get("Tamaño").toString()
                            var tamaño = size.toIntOrNull()
                            if (tamaño == null){
                                tamaño = 0
                            }
                            var id = docs.id
                            drinks?.add(Drinks(id,name,precio,tamaño,""))
                            recyclerDrink.setOnClickListener {
                                editDrink.setText(id)
                            }
                            recyclerDrink.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "comida actualizando")
                        }                                                                 //HASTA ACA SE PUEDE COPIAR Y PEGAR EN ADDED, MODIFIED EN REMOVED CHECAR AFUERA DEL CICLO FOR
                        //Toast.makeText(this, "Bebida Añadida", Toast.LENGTH_SHORT).show()       //añadir this.context si es un fragmento
                        //Log.d(TAG, "Comida Agregada")
                    }
                    DocumentChange.Type.MODIFIED -> {
                        drinks?.removeAll(drinks)                                                     //DE AQUI CHECAR LISTAS Y DEMAS ELEMENTOS
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val size = docs?.get("Tamaño").toString()
                            var tamaño = size.toIntOrNull()
                            if (tamaño == null){
                                tamaño = 0
                            }
                            var id = docs.id
                            drinks?.add(Drinks(id,name,precio,tamaño,""))
                            recyclerDrink.setOnClickListener {
                                editDrink.setText(id)
                            }
                            recyclerDrink.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "comida actualizando")
                        }                                                                           //HASTA ACA SE PUEDE COPIAR Y PEGAR EN ADDED, MODIFIED EN REMOVED CHECAR AFUERA DEL CICLO FOR
                        //Toast.makeText(this, "Bebida Modificada", Toast.LENGTH_SHORT).show()
                        //Log.d(TAG, "Mesa modificada")
                    }
                    DocumentChange.Type.REMOVED -> {
                        drinks?.removeAll(drinks)                                                     //DE AQUI CHECAR LISTAS Y DEMAS ELEMENTOS
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val size = docs?.get("Tamaño").toString()
                            var tamaño = size.toIntOrNull()
                            if (tamaño == null){
                                tamaño = 0
                            }
                            var id = docs.id
                            drinks?.add(Drinks(id,name,precio,tamaño,""))
                            recyclerDrink.setOnClickListener {
                                editDrink.setText(id)
                            }
                            recyclerDrink.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "comida borrada")
                        }                                                                           //HASTA ACA SE PUEDE COPIAR Y PEGAR EN ADDED, MODIFIED EN REMOVED CHECAR AFUERA DEL CICLO FOR

                        val documentos = value!!.toMutableList()

                        for ((index) in documentos.withIndex() ){
                            Log.d(TAG,"the element at $index")
                        }
                        //Toast.makeText(this, "Bebida eliminada", Toast.LENGTH_SHORT).show()
                        //Log.d(TAG, "comida eliminada")
                    }
                }
            }


        }
    }

    fun addDrinkForm (view: View){
        startActivity(Intent(this,DrinkAdd::class.java))
    }

    companion object {
        private val TAG = MenuDrink::class.java.getSimpleName()
    }

}
