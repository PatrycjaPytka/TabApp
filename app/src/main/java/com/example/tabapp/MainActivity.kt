package com.example.tabapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.apply {
            title = "TabApp"
            elevation = 12F
        }
    }

    fun menuBtn(view: View) {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}