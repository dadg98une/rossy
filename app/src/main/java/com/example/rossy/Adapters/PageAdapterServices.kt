package com.example.rossy.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.rossy.Fragments.PedidoServiceFragment
import com.example.rossy.Fragments.TomarDrinkServiceFragment
import com.example.rossy.Fragments.TomarFoodServiceFragment

class PageAdapterServices(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> TomarFoodServiceFragment()
            1 -> TomarDrinkServiceFragment()
            else -> PedidoServiceFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Agregar Comidas"
            1 -> "Agregar Bebidas"
            else -> "Pedido"
        }
    }

}