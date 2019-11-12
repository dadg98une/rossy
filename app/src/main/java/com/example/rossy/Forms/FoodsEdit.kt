package com.example.rossy.Forms

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_foods_edit.*

class FoodsEdit : AppCompatActivity() {

    private var ID: String = ""

    private var db: FirebaseFirestore? = null
    private var docRef: DocumentReference? = null
    private var nombre: String? = null
    private var categoria: String? = null
    private var precio: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foods_edit)

        db = FirebaseFirestore.getInstance()

        val intent = intent
        ID = intent.getStringExtra("id")
        var name = intent.getStringExtra("nombre")
        var price = intent.getStringExtra("price")
        var category = intent.getStringExtra("categoria")

        val spinner = findViewById<Spinner>(R.id.editSpinnerFood)
        val adapter2 = ArrayAdapter.createFromResource(this,
            R.array.seccionesComida,android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter2

        if (category != null) {
            val spinnerPosition = adapter2.getPosition(category)
            spinner.setSelection(spinnerPosition)
        }

        nameEditFood!!.setText(name)
        priceEditFood!!.setText(price)

    }

    fun editFood (view: View){

        if (TextUtils.isEmpty(nameEditFood.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un nombre a la comida", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(priceEditFood.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa el precio", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(editSpinnerFood.toString())) {
            Toast.makeText(applicationContext, "Ingresa la categoria", Toast.LENGTH_SHORT).show()
            return
        }


        nombre = nameEditFood.text.toString()
        precio = priceEditFood.text.toString().toDoubleOrNull()
        categoria = editSpinnerFood.selectedItem.toString()

        docRef = db?.collection("menu")?.document("alimentos")?.collection("comida")?.document(ID)
        docRef?.update("Categoria", categoria)
        docRef?.update("Nombre",nombre)
        docRef?.update("Precio",precio)

        finish()
    }

    fun cancel(view: View){
        finish()
    }

    companion object {
        private val TAG = FoodsEdit::class.java.getSimpleName()
    }

}
