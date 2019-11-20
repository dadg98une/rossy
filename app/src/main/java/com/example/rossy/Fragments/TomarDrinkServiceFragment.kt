package com.example.rossy.Fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Adapters.DrinksServiceAddAdapter
import com.example.rossy.Objetos.Drinks
import com.example.rossy.Objetos.FragmentData
import com.example.rossy.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_tomar_drink_services.*

class TomarDrinkServiceFragment : Fragment(), FragmentData{

    override val idService: String
        get() = ""

    private var db: FirebaseFirestore? = null
    private var docRef: CollectionReference? = null
    private var listener: ListenerRegistration? = null
    private val drinks = mutableListOf<Drinks>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tomar_drink_services, container, false)

        db = FirebaseFirestore.getInstance()
        docRef= db?.collection("menu")?.document("alimentos")?.collection("bebida")

        var rDrink = v.findViewById<RecyclerView>(R.id.recyclerTomarDrinkService)

        drinks?.removeAll(drinks)

        update()

        rDrink?.layoutManager = LinearLayoutManager(v.context)
        rDrink?.adapter = DrinksServiceAddAdapter(drinks)

        return v
    }

    private fun update(){
        drinks?.clear()

        listener = docRef?.addSnapshotListener { value, e->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            drinks?.clear()
            recyclerTomarDrinkService.adapter?.notifyDataSetChanged()

            for (doc in value!!.documentChanges) {

                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        drinks?.removeAll(drinks)
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
                            drinks?.add(Drinks(id,name,precio,tamaño,idService))
                            recyclerTomarDrinkService.adapter?.notifyDataSetChanged()
                        }
                    }
                    DocumentChange.Type.MODIFIED -> {
                        drinks?.removeAll(drinks)
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
                            drinks?.add(Drinks(id,name,precio,tamaño,idService))
                            recyclerTomarDrinkService.adapter?.notifyDataSetChanged()
                        }
                    }
                    DocumentChange.Type.REMOVED -> {
                        drinks?.removeAll(drinks)
                        for (docs in value!!){
                            val price = docs?.get("Precio").toString()
                            var precio = price.toDoubleOrNull()
                            if (precio == null) precio = 0.0
                            val name = docs?.get("Nombre").toString()
                            val size = docs?.get("Tamaño").toString()
                            var tamaño = size.toIntOrNull()
                            if (tamaño == null){
                                tamaño = 0
                            }
                            var id = docs.id
                            drinks?.add(Drinks(id,name,precio,tamaño,idService))
                            recyclerTomarDrinkService.adapter?.notifyDataSetChanged()
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
        private val TAG = TomarDrinkServiceFragment::class.java.simpleName
    }

}