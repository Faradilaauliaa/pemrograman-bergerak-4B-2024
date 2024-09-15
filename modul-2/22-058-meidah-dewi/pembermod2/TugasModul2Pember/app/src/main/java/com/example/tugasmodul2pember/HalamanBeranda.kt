package com.example.tugasmodul2pember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View


class HalamanBeranda : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_beranda)
    }
    fun KeProfil(view : View) {
        val intent = Intent(this, halaman_profil::class.java)
        startActivity(intent) }

}