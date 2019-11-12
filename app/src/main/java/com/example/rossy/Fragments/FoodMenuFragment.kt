package com.example.rossy.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rossy.Activities.MenuDrink
import com.example.rossy.Activities.MenuFood
import com.example.rossy.R
import kotlinx.android.synthetic.main.fragment_foodmenu.view.*

class FoodMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_foodmenu, container, false)

        v.goFood.setOnClickListener { view ->
            goFood(view)
        }
        v.goDrink.setOnClickListener {view ->
            goDrink(view)
        }

        return v
    }

    private fun goFood(view: View){
        val intent = Intent(view.context, MenuFood::class.java)
        startActivity(intent)
    }

    private fun goDrink(view: View){
        val intent = Intent(view.context, MenuDrink::class.java)
        startActivity(intent)
    }
}
