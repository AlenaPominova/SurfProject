package com.pominova.surfmemesapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pominova.surfmemesapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navigationView: BottomNavigationView = findViewById(R.id.navigation_view)
        navigationView.itemIconTintList = null

        val navController = findNavController(R.id.navigation_host_fragment)
        navigationView.setupWithNavController(navController)
    }
}
