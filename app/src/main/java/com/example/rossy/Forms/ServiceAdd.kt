package com.example.rossy.Forms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rossy.Adapters.PageAdapterServices
import com.example.rossy.R
import kotlinx.android.synthetic.main.activity_service_add.*

class ServiceAdd : AppCompatActivity() {

    var pedido: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_add)

        val fragmentAdapter = PageAdapterServices(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)


    }

}
