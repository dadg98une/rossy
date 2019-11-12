package com.example.rossy.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Forms.TablesEdit
import com.example.rossy.Objetos.Tables
import com.example.rossy.R
import kotlinx.android.synthetic.main.card_table.view.*

class TablesAdapter(val tables : MutableList<Tables>): RecyclerView.Adapter<TablesAdapter.TablesViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesViewHolder {
        return TablesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_table,parent,false)
        )
    }

    override fun getItemCount() = tables.size

    override fun onBindViewHolder(holder: TablesViewHolder, position: Int) {
        val table = tables[position]

        holder.view.nameTableText.text = table.name
        holder.view.tableCapacityText.text = table.capacidad.toString()
        holder.view.areaTablesText.text = table.area
        holder.view.idTable.text = table.id

    }

    class TablesViewHolder(val view: View): RecyclerView.ViewHolder(view){
        init {
            view.editTable.setOnClickListener {
                val intent = Intent(view.context, TablesEdit::class.java)
                intent.putExtra("name",view.nameTableText.text)
                intent.putExtra("area",view.areaTablesText.text)
                intent.putExtra("capacidad",view.tableCapacityText.text)
                intent.putExtra("id",view.idTable.text)
                println("TEST ${view.idTable.text}")
                view.context.startActivity(intent)
            }
        }
    }


}