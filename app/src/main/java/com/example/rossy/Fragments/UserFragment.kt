package com.example.rossy.Fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rossy.R
import com.example.rossy.Objetos.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var userId: String? = null
    private var docRef: DocumentReference? = null

    //private val updateBtn: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_user, container, false)
        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()

        val updateBtn = v.findViewById(R.id.updateBtn_UserF) as Button
        //funcion OnClick del boton updateBtn_UserF

        val user = FirebaseAuth.getInstance().currentUser
        userId = user?.uid

        addUserDataChangeListener()

        updateBtn.setOnClickListener {
            onUpdateClickedUser()
        }

        return v
    }


    override fun onDestroyView() {
        super.onDestroyView()
        docRef = null
        val registration = mFirebaseDatabaseInstance?.collection("users")?.addSnapshotListener{
            snapshot, exception ->

        }
        registration?.remove()
    }

    private fun addUserDataChangeListener() {
        // getting the data one time
        docRef = mFirebaseDatabaseInstance?.collection("users")?.document(userId!!)

        docRef?.addSnapshotListener { snapshot, e ->

            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {

                Log.d(TAG, "Current data: ${snapshot.data}")
                val user = snapshot.toObject(User::class.java)

                // clear edit text
                email_UserF?.setText(user?.email)
                name_UserF?.setText(user?.name)
                lastName_UserF?.setText(user?.lastName)
            }
        }
    }

    private fun onUpdateClickedUser() {
        val name = name_UserF.getText().toString()
        val lastName = lastName_UserF.getText().toString()
        updateUser(name, lastName)
    }


    private fun updateUser(name: String, lastName: String) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName)) {
            val myRef = mFirebaseDatabaseInstance?.collection("users")?.document(userId!!)
            myRef!!.update("name", name)
            myRef.update("lastName", lastName)
            Toast.makeText(
                activity,
                "Datos del usuario actualizados correctamente",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private val TAG = UserFragment::class.java.getSimpleName()
    }
}


/*    private fun getDataOneTime() {

        val docRef = mFirebaseDatabaseInstance?.collection("users")?.document(userId!!)

        docRef?.get()?.addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(User::class.java)

            Log.e(TAG, "La información de usuario ha sido cambiada" + user?.name)

            email_UserF.setText(user?.email)
            name_UserF.setText(user?.name)
            lastName_UserF.setText(user?.lastName)
        }
    }*/