package com.example.rossy.Forms

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rossy.Adapters.PreAddAdapter
import com.example.rossy.Objetos.Tables
import com.example.rossy.R
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_service_pre_add.*

class ServicePreAdd : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {
        areaQuery = spinner.selectedItem.toString()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        update()
    }

    private var db: FirebaseFirestore? = null
    private var docRef: Query? = null
    private var estoo: ListenerRegistration? = null
    private val mesas = mutableListOf<Tables>()
    private  var areaQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_pre_add)

        db = FirebaseFirestore.getInstance()

        val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(this,R.array.seccionesArea,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter.setNotifyOnChange(true)

        spinner.adapter = adapter

        areaQuery = spinner.selectedItem.toString()

        update()

        mesas?.removeAll(mesas)

        recyclerTablePreAdd?.layoutManager = LinearLayoutManager(this)
        recyclerTablePreAdd?.adapter = PreAddAdapter(mesas)
    }

    fun buscar(view: View?) {
        areaQuery = spinner.selectedItem.toString()
        update()
    }

    private fun update() {
        mesas?.clear()

        docRef= db?.collection("mesas")?.whereEqualTo("Area", areaQuery)?.whereEqualTo("Ocupada",false)

        estoo = docRef?.addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            mesas?.clear()
            recyclerTablePreAdd.adapter?.notifyDataSetChanged()

            for (doc in value!!.documentChanges) {

                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        mesas?.removeAll(mesas)
                        for (docs in value!!){
                            val area = docs?.getString("Area").toString()
                            val capacidad = docs?.get("Capacidad").toString()
                            var capaciti = capacidad.toIntOrNull()
                            if (capaciti == null) {
                                capaciti = 0
                            }
                            val nombre = docs?.get("Nombre").toString()
                            var id = docs.id
                            mesas?.add(Tables(id, nombre, area, capaciti))
                            recyclerTablePreAdd.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "mesas actualizando")
                        }
                    }
                    DocumentChange.Type.MODIFIED -> {
                        mesas?.removeAll(mesas)
                        for (docs in value!!){
                            val area = docs?.getString("Area").toString()
                            val capacidad = docs?.get("Capacidad").toString()
                            var capaciti = capacidad.toIntOrNull()
                            if (capaciti == null) {
                                capaciti = 0
                            }
                            val nombre = docs?.get("Nombre").toString()
                            var id = docs.id
                            mesas?.add(Tables(id, nombre, area, capaciti))
                            recyclerTablePreAdd.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "mesas actualizando")
                        }
                    }
                    DocumentChange.Type.REMOVED -> {
                        mesas?.removeAll(mesas)
                        for (docs in value!!){
                            val area = docs?.getString("Area").toString()
                            val capacidad = docs?.get("Capacidad").toString()
                            var capaciti = capacidad.toIntOrNull()
                            if (capaciti == null) {
                                capaciti = 0
                            }
                            val nombre = docs?.get("Nombre").toString()
                            var id = docs.id
                            mesas?.add(Tables(id, nombre, area, capaciti))
                            recyclerTablePreAdd.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "mesas actualizando")
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

    companion object {
        private val TAG = ServicePreAdd::class.java.getSimpleName()
    }

}
