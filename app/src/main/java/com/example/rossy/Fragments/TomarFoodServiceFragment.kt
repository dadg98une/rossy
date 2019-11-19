package com.example.rossy.Fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Adapters.FoodsServiceAddAdapter
import com.example.rossy.Objetos.Foods
import com.example.rossy.R
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_tomar_food_services.*

class TomarFoodServiceFragment : Fragment(){

    private var db: FirebaseFirestore? = null
    private var docRef: Query? = null
    private var listener: ListenerRegistration? = null
    private val foods = mutableListOf<Foods>()
    private  var areaQuery: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tomar_food_services, container, false)

        db = FirebaseFirestore.getInstance()

        var rFood = v.findViewById<RecyclerView>(R.id.recyclerTomarFoodService)
        val btnBuscar = v.findViewById<Button>(R.id.buscarTFoodService)
        val spinner = v?.findViewById<Spinner>(R.id.spinner2)
        val adapter = ArrayAdapter.createFromResource(v.context,R.array.seccionesComida,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter.setNotifyOnChange(true)

        spinner?.adapter = adapter

        areaQuery = spinner?.selectedItem.toString()

        foods?.removeAll(foods)

        btnBuscar.setOnClickListener {
            areaQuery = spinner?.selectedItem.toString()
            update()
        }

        update()

        rFood?.layoutManager = LinearLayoutManager(v.context)
        rFood?.adapter = FoodsServiceAddAdapter(foods)

        return v
    }

    private fun update(){
        foods?.clear()

        docRef= db?.collection("menu")?.document("alimentos")?.collection("comida")?.whereEqualTo("Categoria", areaQuery)


        listener = docRef?.addSnapshotListener { value, e->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            foods?.clear()
            recyclerTomarFoodService.adapter?.notifyDataSetChanged()

            for (doc in value!!.documentChanges) {

                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        foods?.removeAll(foods)
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val categoria = docs?.get("Categoria").toString()
                            var id = docs.id
                            foods?.add(Foods(id,name,precio,categoria))
                            recyclerTomarFoodService.adapter?.notifyDataSetChanged()
                        }
                    }
                    DocumentChange.Type.MODIFIED -> {
                        foods?.removeAll(foods)
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val categoria = docs?.get("Categoria").toString()
                            var id = docs.id
                            foods?.add(Foods(id,name,precio,categoria))
                            recyclerTomarFoodService.adapter?.notifyDataSetChanged()
                        }
                    }
                    DocumentChange.Type.REMOVED -> {
                        foods?.removeAll(foods)
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) {
                                precio = 0.0
                            }
                            val name = docs?.get("Nombre").toString()
                            val categoria = docs?.get("Categoria").toString()
                            var id = docs.id
                            foods?.add(Foods(id,name,precio,categoria))
                            recyclerTomarFoodService.adapter?.notifyDataSetChanged()
                        }

                        val documentos = value!!.toMutableList()

                        for ((index) in documentos.withIndex() ){
                            Log.d(TAG,"the element at $index")
                        }
                    }
                }
            }


        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        listener?.remove()
    }

    companion object {
        private val TAG = TomarFoodServiceFragment::class.java.simpleName
    }

}