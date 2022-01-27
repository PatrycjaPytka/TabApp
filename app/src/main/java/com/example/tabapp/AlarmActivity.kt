package com.example.tabapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val settings = getSharedPreferences("Switch1 state",0)
        val silent = settings.getBoolean("switchkey", false)
        val switch1: Switch = findViewById(R.id.switch1)

        val settings2 = getSharedPreferences("Switch2 state",0)
        val silent2 = settings2.getBoolean("switch2key", false)
        val switch2: Switch = findViewById(R.id.switch2)

        switch1.setChecked(silent)
        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switch1.setChecked(true)
                Toast.makeText(this, "Włączono notyfikację przypominającą", Toast.LENGTH_LONG).show();
            } else {
                switch1.setChecked(false)
                Toast.makeText(this, "Notyfikacja przypominająca została wyłączona", Toast.LENGTH_LONG).show();
            }
            val settings = getSharedPreferences("Switch1 state", 0)
            val editor = settings.edit()
            editor.putBoolean("switchkey", isChecked)
            editor.commit()
        }

        switch2.setChecked(silent2)
        switch2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switch2.setChecked(true)
                Toast.makeText(this, "Skan kodu kreskowego został włączony", Toast.LENGTH_LONG).show();
            } else {
                switch2.setChecked(false)
                Toast.makeText(this, "Wyłączono skan kodu kreskowego", Toast.LENGTH_LONG).show();
            }
            val settings2 = getSharedPreferences("Switch2 state", 0)
            val editor2 = settings2.edit()
            editor2.putBoolean("switch2key", isChecked)
            editor2.commit()
        }
    }
    fun menuBtn(view: View) {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}