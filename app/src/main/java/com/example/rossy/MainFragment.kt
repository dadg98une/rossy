package com.example.rossy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//import com.google.android.material.bottomnavigation.BottomNavigationView
//, BottomNavigationView.OnNavigationItemSelectedListener
class MainFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //bottom_navigation.setOnNavigationItemSelectedListener()
        return inflater.inflate(R.layout.fragment_main, container, false)

    }




/*    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.nav_services -> {
                childFragmentManager.beginTransaction().replace(
                    R.id.fragment_container_main,
                    ServicesFragment()
                ).commit()
            }
            R.id.nav_foodmenu -> {
                childFragmentManager.beginTransaction().replace(
                    R.id.fragment_container_main,
                    FoodMenuFragment()
                ).commit()
            }
        }
        return true
    }*/

}


/*        if (bottom_navigation == null){

        } else{
            bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { MenuItem ->
                when (MenuItem.itemId) {
                    R.id.nav_services -> {
                        childFragmentManager.beginTransaction().replace(
                            R.id.fragment_container_main,
                            ServicesFragment()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.nav_foodmenu -> {
                        childFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            FoodMenuFragment()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })
        }*/

//bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


/*if (savedInstanceState == null) {
    val fragment = ServicesFragment()
    childFragmentManager.beginTransaction()
        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
        .commit()
}*/
