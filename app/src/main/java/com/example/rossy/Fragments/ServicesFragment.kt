package com.example.rossy.Fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Adapters.ServicesAdapter
import com.example.rossy.Forms.ServicePreAdd
import com.example.rossy.Objetos.Services
import com.example.rossy.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_services.*

class ServicesFragment : Fragment() {

    private var db: FirebaseFirestore? = null
    private var docRef: Query? = null
    private var listener: ListenerRegistration? = null
    private val servicios = mutableListOf<Services>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_services, container, false)
        db = FirebaseFirestore.getInstance()
        docRef= db?.collection("ordenes")?.whereEqualTo("Entregado",false)

        var recyclerServices = v.findViewById<RecyclerView>(R.id.recyclerServices)

        update()
        servicios?.removeAll(servicios)

        recyclerServices?.layoutManager = LinearLayoutManager(this.context)
        recyclerServices?.adapter = ServicesAdapter(servicios)

        val addServiceButton = v.findViewById<FloatingActionButton>(R.id.addService)
        addServiceButton.setOnClickListener {
            addService()
        }


        return v
    }

    private fun addService (){
        startActivity(Intent(this@ServicesFragment.context, ServicePreAdd::class.java))
    }

    private fun update(){
        servicios?.clear()

        listener = docRef?.addSnapshotListener { value, e->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            servicios?.clear()
            recyclerServices.adapter?.notifyDataSetChanged()

            for (doc in value!!.documentChanges) {

                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        servicios?.removeAll(servicios)

                        for (docs in value!!){
                            val folios = docs?.get("Folio").toString()
                            var folio = folios.toIntOrNull()
                            if (folio == null) folio = 0
                            val mesa = docs?.get("Mesa").toString()
                            val servidor = docs?.get("Atendio").toString()
                            val area = docs.get("Area").toString()
                            var id = docs.id
                            servicios?.add(Services(id,folio,mesa,area,servidor))
                            recyclerServices.adapter?.notifyDataSetChanged()
                        }
                    }
                    DocumentChange.Type.MODIFIED -> {
                        servicios?.removeAll(servicios)
                        for (docs in value!!){
                            val folios = docs?.get("Folio").toString()
                            var folio = folios.toIntOrNull()
                            if (folio == null) folio = 0
                            val mesa = docs?.get("Mesa").toString()
                            val servidor = docs?.get("Atendio").toString()
                            val area = docs.get("Area").toString()
                            var id = docs.id
                            servicios?.add(Services(id,folio,mesa,area,servidor))
                            recyclerServices.adapter?.notifyDataSetChanged()
                        }
                    }
                    DocumentChange.Type.REMOVED -> {
                        servicios?.removeAll(servicios)
                        for (docs in value!!){
                            val folios = docs?.get("Folio").toString()
                            var folio = folios.toIntOrNull()
                            if (folio == null) folio = 0
                            val mesa = docs?.get("Mesa").toString()
                            val servidor = docs?.get("Atendio").toString()
                            val area = docs.get("Area").toString()
                            var id = docs.id
                            servicios?.add(Services(id,folio,mesa,area,servidor))
                            recyclerServices.adapter?.notifyDataSetChanged()
                        }
                        val servicios = value!!.toMutableList()

                        for ((index) in servicios.withIndex() ){
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
        private val TAG = ServicesFragment::class.java.getSimpleName()
    }
}
