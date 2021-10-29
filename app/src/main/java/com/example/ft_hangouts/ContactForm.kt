package com.example.ft_hangouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ContactForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        val create_contact = findViewById<Button>(R.id.create_contact)

        create_contact.setOnClickListener(){
            val main_intent = Intent(this, MainActivity::class.java).apply{}

            finish()
        }
    }
}