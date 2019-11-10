package com.example.rossy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class TablesFragment : Fragment() {

    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var nombre: String? = null
    private var capacidad: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tables, container, false)

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()

        val btnAddTable = v.findViewById(R.id.addBtn_TablesF) as Button

        btnAddTable.setOnClickListener {
            addTableForm()
        }

        return v
    }

    fun addTableForm() {
        startActivity(Intent(this@TablesFragment.context, TablesForm::class.java))
    }

}
