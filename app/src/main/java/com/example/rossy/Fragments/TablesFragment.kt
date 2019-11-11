package com.example.rossy.Fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Adapters.TablesAdapter
import com.example.rossy.Forms.TablesForm
import com.example.rossy.Objetos.Tables
import com.example.rossy.R
import com.google.firebase.firestore.FirebaseFirestore

class TablesFragment : Fragment() {

    private var db: FirebaseFirestore? = null
    private val mesas = mutableListOf<Tables>()
    private var nombre: String? = null
    private var capacidad: String? = null
    private var area: String? = null

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

        val mesasRef = db?.collection("mesas")

        mesasRef?.addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            mesas.clear()

            for (doc in value!!) {
                val area = doc.getString("Area").toString()
                val capacidad = doc.get("Capacidad").toString()
                var capaciti = capacidad.toIntOrNull()
                if (capaciti == null){
                    capaciti = 0
                }
                val nombre = doc.get("Nombre").toString()
                //var id = doc.id
                mesas.add(Tables(nombre,area,capaciti))
            }
            Log.d(ContentValues.TAG, "mesas actualizando")
        }

        rTables.layoutManager = LinearLayoutManager(v.context)

        rTables.adapter = TablesAdapter(mesas)

        return v
    }

    private fun addTableForm() {
        startActivity(Intent(this@TablesFragment.context, TablesForm::class.java))
    }

}
