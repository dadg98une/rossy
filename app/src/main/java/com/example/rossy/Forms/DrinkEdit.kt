package com.example.rossy.Forms

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_drink_edit.*

class DrinkEdit : AppCompatActivity() {

    private var ID: String = ""

    private var db: FirebaseFirestore? = null
    private var docRef: DocumentReference? = null
    private var nombre: String? = null
    private var tamaño: Double? = null
    private var precio: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_edit)

        db = FirebaseFirestore.getInstance()

        val intent = intent
        ID = intent.getStringExtra("id")
        var name = intent.getStringExtra("nombre")
        var price = intent.getStringExtra("price")
        var size= intent.getStringExtra("size")

        nameEditDrink!!.setText(name)
        sizeEditDrink!!.setText(size)
        priceEditDrink!!.setText(price)


    }

    fun editDrink (view: View){

        if (TextUtils.isEmpty(nameEditDrink.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un nombre a la bebida", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(priceEditDrink.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa el precio", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(sizeEditDrink.toString())) {
            Toast.makeText(applicationContext, "Ingresa el tamaño de la bebida en mL", Toast.LENGTH_SHORT).show()
            return
        }


        nombre = nameEditDrink.text.toString()
        precio = priceEditDrink.text.toString().toDoubleOrNull()
        tamaño = sizeEditDrink.text.toString().toDoubleOrNull()

        docRef = db?.collection("menu")?.document("alimentos")?.collection("bebida")?.document(ID)
        docRef?.update("Tamaño", tamaño)
        docRef?.update("Nombre",nombre)
        docRef?.update("Precio",precio)

        finish()
    }

    fun cancel(view: View){
        finish()
    }

    companion object {
        private val TAG = DrinkEdit::class.java.getSimpleName()
    }
}
