package com.example.rossy

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()

        // if user logged in, go to sign-in screen
        if (mAuth!!.getCurrentUser() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
    }

    //se puede registrar un usuario
    fun onRegisterClicked(view: View) {
        if (TextUtils.isEmpty(user.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un nombre de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(email.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un correo electronico", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(password.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa una contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.text.toString().length < 6) {
            Toast.makeText(applicationContext, "La contraseña es muy corta, deben de ser mínimo 6 caracteres", Toast.LENGTH_LONG).show()
            return
        }

        progressBar.visibility = View.VISIBLE

        mAuth!!.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->

                Toast.makeText(this@Register, "Usuario registrado correctamente: " + task.isSuccessful(), Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE

                if (task.isSuccessful) {
                    startActivity(Intent(this@Register, Login::class.java))
                    finish()
                } else {
                    Toast.makeText(this@Register, "Autentificación fallida! " + task.getException(),
                        Toast.LENGTH_LONG).show()
                    Log.e("MyTag", task.getException().toString())
                }
            }
    }

    //cuando se quiere regresar al login
    fun onLoginClicked(view: View) {
        startActivity(Intent(this, Login::class.java))
    }
}
