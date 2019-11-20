package com.example.rossy.Forms

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.Adapters.PageAdapterServices
import com.example.rossy.Objetos.User
import com.example.rossy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_service_add.*
import java.text.SimpleDateFormat
import java.util.*

class ServiceAdd : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var users: String? = null
    private var userName: String? = null
    private var area: String? = null
    private var mesaId: String? = null
    private var mesa: String? = null
    private var fecha: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_add)

        db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        val fragmentAdapter = PageAdapterServices(supportFragmentManager)

        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)

        users = user?.uid
        mesa = intent?.getStringExtra("name")
        area = intent?.getStringExtra("area")
        mesaId = intent?.getStringExtra("id")
        fecha = getFecha()

        val docRef = db?.collection("users")?.document(users!!.toString())
        docRef?.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {

                val user = snapshot?.toObject(User::class.java)
                userName = user?.name



                val service = hashMapOf(
                    "Area" to area,
                    "Atendio" to userName,
                    "Entregado" to false,
                    "Fecha" to fecha,
                    "IdAtendio" to users,
                    "IdMesa" to mesaId,
                    "Mesa" to mesa,
                    "Pedido" to arrayListOf<Array<String>>()
                )

                db?.collection("ordenes")?.add(service)?.addOnSuccessListener {
                        documentReference -> Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    fragmentAdapter.idService = documentReference.id
                }?.addOnFailureListener {
                        exception -> Log.w(TAG, "Error adding document", exception)
                }

                Log.d(TAG, "Current data: ${snapshot.data}")
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

    }

    private fun getFecha():String{
        val sdf = SimpleDateFormat("yyyy/M/dd hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    companion object {
        private val TAG = ServiceAdd::class.java.getSimpleName()
    }

}
