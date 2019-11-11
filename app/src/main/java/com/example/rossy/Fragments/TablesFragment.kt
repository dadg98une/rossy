package com.example.rossy.Fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Adapters.TablesAdapter
import com.example.rossy.Forms.TablesAdd
import com.example.rossy.Objetos.Tables
import com.example.rossy.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_tables.*

class TablesFragment : Fragment() {

    private var db: FirebaseFirestore? = null
    private var docRef: CollectionReference? = null
    private var estoo: ListenerRegistration? = null
    private val mesas = mutableListOf<Tables>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tables, container, false)
        db = FirebaseFirestore.getInstance()

        docRef= db?.collection("mesas")

        val btnAddTable = v.findViewById<Button>(R.id.addBtn_TablesF)
        var rTables = v.findViewById<RecyclerView>(R.id.recyclerTables)

        btnAddTable.setOnClickListener {
            addTableForm()
        }

        update()

        mesas?.removeAll(mesas)

        rTables?.layoutManager = LinearLayoutManager(v.context)
        rTables?.adapter = TablesAdapter(mesas)

        return v
    }

    private fun addTableForm() {
        startActivity(Intent(this@TablesFragment.context, TablesAdd::class.java))
    }

    private fun update() {
        mesas?.clear()

        estoo = docRef?.addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            mesas?.clear()
            recyclerTables.adapter?.notifyDataSetChanged()

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
                            mesas?.add(Tables(nombre, area, capaciti))
                            recyclerTables.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "mesas actualizando")
                        }
                        Toast.makeText(this.context, "Mesa Añadida", Toast.LENGTH_SHORT).show()
                        //Log.d(TAG, "Nueva mesa agregada")
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
                            mesas?.add(Tables(nombre, area, capaciti))
                            recyclerTables.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "mesas actualizando")
                        }
                        Toast.makeText(this.context, "Mesa Modificada", Toast.LENGTH_SHORT).show()
                        //Log.d(TAG, "Mesa modificada")
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
                            mesas?.add(Tables(nombre, area, capaciti))
                            recyclerTables.adapter?.notifyDataSetChanged()
                            //Log.d(TAG, "mesas actualizando")
                        }

                        val documentos = value!!.toMutableList()

                        for ((index) in documentos.withIndex() ){
                            Log.d(TAG,"the element at $index")
                        }
                        Toast.makeText(this.context, "Mesa Eliminada", Toast.LENGTH_SHORT).show()
                        //Log.d(TAG, "Mesa eliminada")
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        estoo?.remove()
    }




    companion object {
        private val TAG = TablesFragment::class.java.getSimpleName()
    }


}


