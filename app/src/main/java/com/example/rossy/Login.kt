package com.example.rossy

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class Login : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
    }

    //cuando se inicie la app
    public override fun onStart() {
        super.onStart()

        // if user logged in, go to sign-in screen
        if (mAuth!!.getCurrentUser() != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    //mientras la app se reinicia
    override fun onResume() {
        super.onResume()
        //progressBar2.visibility = View.GONE
    }

    //hacer un incio de sesion
    fun loginButtonClicked(view: View) {
        if (TextUtils.isEmpty(email.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa un correo electronico", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(password.text.toString())) {
            Toast.makeText(applicationContext, "Ingresa una contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        //progressBar2.visibility = View.VISIBLE

        //authenticate user
        mAuth!!.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                //progressBar2.visibility = View.GONE

                if (task.isSuccessful) {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (password.text.toString().length < 6) {
                        password.error = "La contraseña debe de ser minimo de 6 caracteres!"
                    } else {
                        Toast.makeText(this@Login, "Correo o Contraseña Incorrecta!", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    //preregister
    fun preregister(view: View) {
        startActivity(Intent(this, PreRegister::class.java))
    }
}
