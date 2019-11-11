package com.example.rossy.Forms

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.Activities.Login
import com.example.rossy.MainActivity
import com.example.rossy.Objetos.User
import com.example.rossy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var userId: String? = null
    private var emailAddress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
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
        if (TextUtils.isEmpty(name.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa tu nombre", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(lastName.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa tu apellido", Toast.LENGTH_SHORT).show()
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

        if (TextUtils.isEmpty(passwordC.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa la confirmación de contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.text.toString().length < 6) {
            Toast.makeText(applicationContext, "La contraseña es muy corta, deben de ser mínimo 6 caracteres", Toast.LENGTH_LONG).show()
            return
        }

        if (password.text.toString() != passwordC.text.toString()){
            Toast.makeText(applicationContext, "Las contraseñas no coinciden!",Toast.LENGTH_LONG).show()
            return
        }

        progressBar.visibility = View.VISIBLE

        mAuth!!.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->

                Toast.makeText(this@Register, "Usuario registrado correctamente: " + task.isSuccessful(), Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE

                if (task.isSuccessful) {

                    val user = FirebaseAuth.getInstance().currentUser
                    userId = user!!.uid
                    emailAddress = user.email

                    val myUser = User(
                        name.text.toString(),
                        lastName.text.toString(),
                        emailAddress!!
                    )

                    //create the document with the user id above
                    mFirebaseDatabaseInstance?.collection("users")?.document(userId!!)?.set(myUser)

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
