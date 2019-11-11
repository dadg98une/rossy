package com.example.rossy.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.Forms.Register
import com.example.rossy.R
import kotlinx.android.synthetic.main.activity_pre_register.*

class PreRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_register)
    }

    //el pin de seguridad solo se puede modificar desde aqui!
    val pin = 5620

    //funcion de boton para validar nulos y si el pin es correcto
    fun checkValue(view: View){
        if (TextUtils.isEmpty(numPassword.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un pin", Toast.LENGTH_SHORT).show()
            return
        }

        if (numPassword.text.toString().length < 4) {
            Toast.makeText(applicationContext, "El pin es muy corto deben ser 4 digitos", Toast.LENGTH_LONG).show()
            return
        }

        if (numPassword.text.toString().length > 4) {
            Toast.makeText(applicationContext, "El pin es muy largo el maximo es 4 digitos", Toast.LENGTH_LONG).show()
            return
        }
        //si el pin es correcto entonces se deja accesar al activity de login
        if (numPassword.text.toString() == pin.toString()){
            startActivity(Intent(this, Register::class.java))
            finish()
        } else {
            Toast.makeText(applicationContext,"Pin incorrecto!",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
