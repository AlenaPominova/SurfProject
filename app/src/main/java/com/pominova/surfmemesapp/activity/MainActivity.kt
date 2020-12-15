package com.pominova.surfmemesapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pominova.surfmemesapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var searchMenuItem: MenuItem
    private lateinit var mSearchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navigationView = findViewById(R.id.navigation_view)
        navigationView.itemIconTintList = null

        navController = findNavController(R.id.navigation_host_fragment)
        navigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        searchMenuItem = menu.findItem(R.id.search_action)
        mSearchView = searchMenuItem.getActionView() as SearchView
        mSearchView.setQueryHint(getText(R.string.search))
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
