package com.pominova.surfmemesapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.pominova.surfmemesapp.R

class StartActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        handler.postDelayed(openAuthActivity, 300);
    }

    private val openAuthActivity = Runnable {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }
}
