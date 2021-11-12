package com.example.ft_hangouts

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ContactForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        val create_contact = findViewById<Button>(R.id.create_contact)
        val editTextFirstName = findViewById<EditText>(R.id.first_name)
        val editTextLastName = findViewById<EditText>(R.id.last_name)
        val editTextMail = findViewById<EditText>(R.id.email_address)
        val editTextPhone = findViewById<EditText>(R.id.phone_number)

        create_contact.setOnClickListener(){
            //val main_intent = Intent(this, MainActivity::class.java).apply{}
            if (editTextFirstName.text.toString().isNotEmpty()
                && editTextLastName.text.toString().isNotEmpty()
                && editTextMail.text.toString().isNotEmpty()
                && editTextPhone.text.toString().isNotEmpty()){
                val db = DatabaseHelper(this)

                db.insertData(editTextFirstName.text.toString(), editTextLastName.text.toString(), editTextMail.text.toString(), editTextPhone.text.toString())
            }
            finish()
        }
    }
}