package com.example.rossy.Forms

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_tables_form.*

class TablesForm : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this,"item seleccionado",Toast.LENGTH_SHORT).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var db: FirebaseFirestore? = null
    private var nombre: String? = null
    private var area: String? = null
    private var capacidad: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tables_form)

        db = FirebaseFirestore.getInstance()

        val spiner = findViewById<Spinner>(R.id.tableArea)
        val adapter2 = ArrayAdapter.createFromResource(this,R.array.seccionesArea,android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spiner.adapter = adapter2

    }

    fun addTable(view: View) {
        if (TextUtils.isEmpty(tableName.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un nombre a la mesa", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(tableCapacity.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa la capacidad de la mesa", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(tableArea.toString())) {
            Toast.makeText(applicationContext, "Ingresa donde esta ubicada la mesa", Toast.LENGTH_SHORT).show()
            return
        }


        nombre = tableName.text.toString()
        capacidad = tableCapacity.text.toString().toIntOrNull()
        area = tableArea.selectedItem.toString()


        val table = hashMapOf(
            "Nombre" to nombre,
            "Capacidad" to capacidad,
            "Area" to area,
            "Ocupada" to false
        )

        db?.collection("mesas")?.add(table)?.addOnSuccessListener {
            documentReference ->
            Log.d(TAG,"Mesa añadida con la ID: ${documentReference.id}")
            Toast.makeText(this,"Mesa añadidad correctamente",Toast.LENGTH_SHORT).show()
            finish()
        }?.addOnFailureListener { e ->
            Log.w(TAG,"Error al añadir mesa",e)
        }


    }

    fun cancel(view: View){
        finish()
    }

    companion object {
        private val TAG = TablesForm::class.java.getSimpleName()
    }
}
