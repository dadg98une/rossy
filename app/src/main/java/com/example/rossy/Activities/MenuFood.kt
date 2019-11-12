package com.example.rossy.Activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Adapters.FoodsAdapter
import com.example.rossy.Forms.FoodsAdd
import com.example.rossy.Objetos.Foods
import com.example.rossy.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_menu_food.*
import kotlinx.android.synthetic.main.card_food.*

class MenuFood : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var docRef: CollectionReference? = null
    private var foodRec : RecyclerView? = null
    private var listener: ListenerRegistration? = null
    private val foods = mutableListOf<Foods>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_food)

        db = FirebaseFirestore.getInstance()
        docRef= db?.collection("menu")?.document("alimentos")?.collection("comida")

        update()

        foods?.removeAll(foods)

        recyclerFood?.layoutManager = LinearLayoutManager(this)
        recyclerFood?.adapter = FoodsAdapter(foods)

    }

    private fun update(){
        foods?.clear()

        listener = docRef?.addSnapshotListener { value, e->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            foods?.clear()
            recyclerFood.adapter?.notifyDataSetChanged()

            for (doc in value!!.documentChanges) {

                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        foods?.removeAll(foods)                                                     //DE AQUI CHECAR LISTAS Y DEMAS ELEMENTOS
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val cat = docs?.get("Categoria").toString()
                            var id = docs.id
                            foods?.add(Foods(id,name,precio,cat))
                            recyclerFood.setOnClickListener {
                                editFood.setText(id)
                            }
                            recyclerFood.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "comida añadida")
                        }                                                                           //HASTA ACA SE PUEDE COPIAR Y PEGAR EN ADDED, MODIFIED EN REMOVED CHECAR AFUERA DEL CICLO FOR
                        Toast.makeText(this, "Comida Añadida", Toast.LENGTH_SHORT).show()       //añadir this.context si es un fragmento
                        //Log.d(TAG, "Comida Agregada")
                    }
                    DocumentChange.Type.MODIFIED -> {
                        foods?.removeAll(foods)                                                     //DE AQUI CHECAR LISTAS Y DEMAS ELEMENTOS
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val cat = docs?.get("Categoria").toString()
                            var id = docs.id
                            foods?.add(Foods(id,name,precio,cat))
                            recyclerFood.setOnClickListener {
                                editFood.setText(id)
                            }
                            recyclerFood.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "comida actualizando")
                        }                                                                           //HASTA ACA SE PUEDE COPIAR Y PEGAR EN ADDED, MODIFIED EN REMOVED CHECAR AFUERA DEL CICLO FOR
                        Toast.makeText(this, "Comida Modificada", Toast.LENGTH_SHORT).show()
                        //Log.d(TAG, "Mesa modificada")
                    }
                    DocumentChange.Type.REMOVED -> {
                        foods?.removeAll(foods)                                                     //DE AQUI CHECAR LISTAS Y DEMAS ELEMENTOS
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val cat = docs?.get("Categoria").toString()
                            var id = docs.id
                            foods?.add(Foods(id,name,precio,cat))
                            recyclerFood.setOnClickListener {
                                editFood.setText(id)
                            }
                            recyclerFood.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "comida borrada")
                        }                                                                           //HASTA ACA SE PUEDE COPIAR Y PEGAR EN ADDED, MODIFIED EN REMOVED CHECAR AFUERA DEL CICLO FOR

                        val documentos = value!!.toMutableList()

                        for ((index) in documentos.withIndex() ){
                            Log.d(TAG,"the element at $index")
                        }
                        Toast.makeText(this, "Comida eliminada", Toast.LENGTH_SHORT).show()
                        //Log.d(TAG, "comida eliminada")
                    }
                }
            }


        }
    }

    fun addFoodForm (view: View) {
        startActivity(Intent(this,FoodsAdd::class.java))
    }

    companion object {
        private val TAG = MenuFood::class.java.getSimpleName()
    }


}
