package com.example.ft_hangouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addContact = findViewById<FloatingActionButton>(R.id.add_contact)

        addContact.setOnClickListener{
            val contactIntent = Intent(this, ContactForm::class.java).apply{}

            startActivity(contactIntent)
        }
    }
}