package com.example.rossy.Fragments

import android.content.Intent
import android.os.Bundle
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

    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var nombre: String? = null
    private var capacidad: String? = null
    private var area: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tables, container, false)

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()


        val btnAddTable = v.findViewById<Button>(R.id.addBtn_TablesF)
        val rTables = v.findViewById<RecyclerView>(R.id.recyclerTables)

        btnAddTable.setOnClickListener {
            addTableForm()
        }

        val tables = listOf(
            Tables("Mesa 1","Adentro",4),
            Tables("Mesa 2","Adentro",4),
            Tables("Mesa 3","Adentro",4),
            Tables("Mesa 4","Adentro",4),
            Tables("Mesa 1","Afuera",4),
            Tables("Mesa 2","Afuera",4),
            Tables("Mesa 3","Afuera",4),
            Tables("Mesa 4","Afurea",4)
        )

        rTables.layoutManager = LinearLayoutManager(v.context)
        rTables.adapter = TablesAdapter(tables)

        return v
    }

    private fun addTableForm() {
        startActivity(Intent(this@TablesFragment.context, TablesForm::class.java))
    }

}
