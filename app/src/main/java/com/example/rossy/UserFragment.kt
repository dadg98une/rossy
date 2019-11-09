package com.example.rossy

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()

        val user = FirebaseAuth.getInstance().currentUser
        userId = user?.uid
        //getDataOneTime()
        addUserDataChangeListener()

        return inflater.inflate(R.layout.fragment_user, container, false)
    }


    private fun getDataOneTime() {

        val docRef = mFirebaseDatabaseInstance?.collection("users")?.document(userId!!)

        docRef?.get()?.addOnSuccessListener{
            documentSnapshot ->
            val user = documentSnapshot.toObject(User::class.java)

            Log.e(TAG,"La información de usuario ha sido cambiada"+ user?.name)

            email_UserF.setText(user?.email)
            name_UserF.setText(user?.name)
            lastName_UserF.setText(user?.lastName)
        }
    }

    private fun addUserDataChangeListener() {
        // getting the data one time
        val docRef = mFirebaseDatabaseInstance?.collection("users")?.document(userId!!)
        docRef?.addSnapshotListener { snapshot, e ->

            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {

                Log.d(TAG, "Current data: ${snapshot.data}")
                val user = snapshot.toObject(User::class.java)

                // clear edit text
                email_UserF.setText(user?.email)
                name_UserF.setText(user?.name)
                lastName_UserF.setText(user?.lastName)
            }
        }
    }


     fun updateUser(name:String, lastName:String){
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName)){
            val myRef = mFirebaseDatabaseInstance?.collection("users")?.document(userId!!)
            myRef!!.update("name",name)
            myRef!!.update("lastName",lastName)
            //Toast.makeText(activity, "Datos del usuario actualizados correctamente", Toast.LENGTH_SHORT).show()
        } else{
            //Toast.makeText(activity, "Ocurrio un error!", Toast.LENGTH_SHORT).show()
        }
    }
}
