package com.example.ft_hangouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout

class SmsActivity : AppCompatActivity() {
    private lateinit var smsEditText: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)
        val db = DatabaseHelper(this)
        var currentUser = db.getUser(intent.getIntExtra("userId", -1))
        var fullName: String = currentUser.firstName + " " + currentUser.lastName

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        toolbar.setTitle(fullName)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        smsEditText = findViewById(R.id.sms_editText)
        smsEditText.requestFocus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}