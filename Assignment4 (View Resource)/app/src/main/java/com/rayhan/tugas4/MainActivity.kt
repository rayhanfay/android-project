package com.rayhan.tugas4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun pindahActivityKedua(view: View?) {
        val i = Intent(applicationContext, MainActivity2::class.java)
        i.putExtra( "Value1",  "Belajar Android")
        i.putExtra( "Value2",  "Pemrograman Mobile")
        startActivity(i)
    }
}