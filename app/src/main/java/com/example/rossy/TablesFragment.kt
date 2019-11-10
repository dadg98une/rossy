package com.example.rossy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class TablesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tables, container, false)

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
