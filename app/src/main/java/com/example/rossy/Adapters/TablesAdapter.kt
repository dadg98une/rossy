package com.example.rossy.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Objetos.Tables
import com.example.rossy.R
import kotlinx.android.synthetic.main.card_table.view.*

class TablesAdapter(val tables : List<Tables>): RecyclerView.Adapter<TablesAdapter.TablesViewHolder>() {



}


/*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesViewHolder {
        return TablesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_table,parent,false)
        )
    }

    override fun getItemCount() = tables.size

    override fun onBindViewHolder(holder: TablesViewHolder, position: Int) {
        val table = tables[position]

        holder.view.tableName.text = table.name
        holder.view.tableCapacity.text = table.capacidad.toString()
        holder.view.tableArea.text = table.area
    }

    class TablesViewHolder(val view: View): RecyclerView.ViewHolder(view)
 */