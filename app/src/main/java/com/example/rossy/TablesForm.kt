package com.example.rossy

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_tables_form.*

class TablesForm : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var nombre: String? = null
    private var capacidad: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tables_form)

        db = FirebaseFirestore.getInstance()
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

        nombre = tableName.text.toString()
        capacidad = tableCapacity.text.toString().toIntOrNull()


        val table = hashMapOf(
            "Nombre" to nombre,
            "Capacidad" to capacidad,
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
