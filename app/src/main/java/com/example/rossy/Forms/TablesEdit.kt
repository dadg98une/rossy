package com.example.rossy.Forms

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_tables_edit.*


class TablesEdit : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    private var select: String? = null

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    private var ID: String = ""

    private var db: FirebaseFirestore? = null
    private var nombre: String? = null
    private var area: String? = null
    private var capacidad: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rossy.R.layout.activity_tables_edit)

        db = FirebaseFirestore.getInstance()

        //val compareValue = "Adentro"

        val intent = intent
        ID = intent.getStringExtra("id")
        var nameI = intent.getStringExtra("name")
        var areaI = intent.getStringExtra("area")
        var capacidadI = intent.getStringExtra("capacidad")

        val spiner = findViewById<Spinner>(com.example.rossy.R.id.tableAreaEdit)
        val adapter2 = ArrayAdapter.createFromResource(this,
            com.example.rossy.R.array.seccionesArea,android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spiner.adapter = adapter2

        if (areaI != null) {
            val spinnerPosition = adapter2.getPosition(areaI)
            spiner.setSelection(spinnerPosition)
        }

        tableNameEdit!!.setText(nameI)
        tableCapacityEdit!!.setText(capacidadI)

    }

    fun editTable (view: View){

        if (TextUtils.isEmpty(tableNameEdit.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un nombre a la mesa", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(tableCapacityEdit.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa la capacidad de la mesa", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(tableAreaEdit.toString())) {
            Toast.makeText(applicationContext, "Ingresa donde esta ubicada la mesa", Toast.LENGTH_SHORT).show()
            return
        }


        nombre = tableNameEdit.text.toString()
        capacidad = tableCapacityEdit.text.toString().toIntOrNull()
        area = tableAreaEdit.selectedItem.toString()

        val docRef = db?.collection("mesas")?.document(ID)
        docRef?.update("Capacidad", capacidad)
        docRef?.update("Nombre",nombre)
        docRef?.update("Area",area)

        finish()
    }

    fun cancel(view: View){
        finish()
    }

    companion object {
        private val TAG = TablesAdd::class.java.getSimpleName()
    }

}
