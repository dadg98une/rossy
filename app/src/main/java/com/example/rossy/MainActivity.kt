package com.example.rossy

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //listener para la navigation view
        nav_view.setNavigationItemSelectedListener(this);

        val toogle = ActionBarDrawerToggle(this,drawer_layout,toolbar,
            R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()
        //se abre el fragmento inmediatamente
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                MainFragment()).commit()
            nav_view.setCheckedItem(R.id.nav_main)
        }

    }

    //activa nuevos fragmentos al main
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId){
            R.id.nav_main -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    MainFragment()).commit()
            }
            R.id.nav_monitor -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    MonitorFragment()).commit()
            }
            R.id.nav_tables -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    TablesFragment()).commit()
            }
            R.id.nav_user -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    UserFragment()).commit()
            }
            R.id.nav_logout-> {
                //para cerrar sesion con la instancia de firebase
                Toast.makeText(this,"Sesion Cerrada Correctamente",Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, Login::class.java))
                finish()
            }
            else -> {
                Toast.makeText(this,"Algo paso",Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        } else  {
            super.onBackPressed()
        }
    }



}
