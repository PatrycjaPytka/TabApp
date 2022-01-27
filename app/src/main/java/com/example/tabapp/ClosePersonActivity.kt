package com.example.tabapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ClosePersonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_person)
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