package com.example.rossy.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rossy.Forms.ServiceAdd
import com.example.rossy.Objetos.Tables
import com.example.rossy.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.pre_add_table_record.view.*

class PreAddAdapter (val tables: MutableList<Tables>): RecyclerView.Adapter<PreAddAdapter.TablesViewHolder>(){

    private var id: String ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesViewHolder {
        return TablesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pre_add_table_record, parent, false)
        )
    }

    override fun getItemCount()= tables.size

    override fun onBindViewHolder(holder: TablesViewHolder, position: Int) {
        val table = tables[position]

        holder.view.namePreAddTable.text = table.name
        holder.view.capacityPreAddTable.text = table.capacidad.toString()
        holder.view.areaPreAddTable.text = table.area
        holder.view.idPreAddTable.text = table.id
        id = table.id

    }


    class TablesViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var db: FirebaseFirestore? = null
        init {
            view.setOnClickListener {
                val intent = Intent(view.context, ServiceAdd::class.java)
                intent.putExtra("name",view.namePreAddTable.text)
                intent.putExtra("area",view.areaPreAddTable.text)
                intent.putExtra("capacidad",view.capacityPreAddTable.text)
                intent.putExtra("id",view.idPreAddTable.text)
                val db = FirebaseFirestore.getInstance()
                val docRef= db?.collection("mesas")?.document(view.idPreAddTable.text.toString())
                docRef.update("Ocupada", true)
                view.context.startActivity(intent)
            }
        }
    }
}