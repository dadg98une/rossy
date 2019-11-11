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
import com.example.rossy.Forms.TablesForm
import com.example.rossy.Objetos.Tables
import com.example.rossy.R
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class TablesFragment : Fragment() {

    private var db: FirebaseFirestore? = null
    private val mesas = mutableListOf<Tables>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tables, container, false)
        db = FirebaseFirestore.getInstance()

        val btnAddTable = v.findViewById<Button>(R.id.addBtn_TablesF)
        val rTables = v.findViewById<RecyclerView>(R.id.recyclerTables)

        btnAddTable.setOnClickListener {
            addTableForm()
        }


        update()

        rTables.layoutManager = LinearLayoutManager(v.context)

        rTables.adapter = TablesAdapter(mesas)


        return v
    }

    private fun addTableForm() {
        startActivity(Intent(this@TablesFragment.context, TablesForm::class.java))
    }

    private fun update(): MutableList<Tables> {
        val mesasRef = db?.collection("mesas")
        mesasRef?.addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            for (doc in value!!.documentChanges) {
                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        mesas.clear()
                        for (docs in value) {
                            val area = docs?.getString("Area").toString()
                            val capacidad = docs?.get("Capacidad").toString()
                            var capaciti = capacidad?.toIntOrNull()
                            if (capaciti == null) {
                                capaciti = 0
                            }
                            val nombre = docs?.get("Nombre").toString()
                            //var id = doc.id
                            mesas!!.add(Tables(nombre, area, capaciti))
                        }
                        Toast.makeText(this.context, "Mesa Añadida", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "Nueva mesa agregada")
                    }
                    DocumentChange.Type.MODIFIED -> {
                        mesas.clear()
                        for (docs in value!!) {
                            val area = docs.getString("Area").toString()
                            val capacidad = docs.get("Capacidad").toString()
                            var capaciti = capacidad.toIntOrNull()
                            if (capaciti == null) {
                                capaciti = 0
                            }
                            val nombre = docs.get("Nombre").toString()
                            //var id = doc.id
                            mesas.add(Tables(nombre, area, capaciti))
                        }
                        Toast.makeText(this.context, "Mesa Modificada", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "Mesa modificada")
                    }
                    DocumentChange.Type.REMOVED -> {
                        mesas.clear()
                        Toast.makeText(this.context, "Mesa Eliminada", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "Mesa eliminada")
                    }
                }
            }


        }
        return mesas
    }

    private fun updateOnce(): MutableList<Tables> {
        val mesasRef = db?.collection("mesas")?.get()

        mesasRef?.addOnSuccessListener { snapshot ->
            for (doc in snapshot!!){
                val area = doc.getString("Area").toString()
                val capacidad = doc.get("Capacidad").toString()
                var capaciti = capacidad.toIntOrNull()
                if (capaciti == null) {
                    capaciti = 0
                }
                val nombre = doc.get("Nombre").toString()
                //var id = doc.id
                mesas.add(Tables(nombre, area, capaciti))
                Log.d(TAG, "mesas actualizando")
            }
        }
        return mesas
    }

    companion object {
        private val TAG = TablesFragment::class.java.getSimpleName()
    }
}


