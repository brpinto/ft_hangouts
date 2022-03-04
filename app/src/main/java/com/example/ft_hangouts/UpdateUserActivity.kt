package com.example.ft_hangouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var editTextUpdateFirstName: EditText
    private lateinit var editTextUpdateLastName: EditText
    private lateinit var editTextUpdateMail: EditText
    private lateinit var editTextUpdatePhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)
        val db = DatabaseHelper(this)

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        toolbar.setTitle("Modifier le contact")
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.close_cross);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val updateButton = findViewById<Button>(R.id.update_button)
        editTextUpdateFirstName = findViewById(R.id.updating_first_name)
        editTextUpdateLastName = findViewById(R.id.updating_last_name)
        editTextUpdateMail = findViewById(R.id.updating_email_address)
        editTextUpdatePhone = findViewById(R.id.updating_phone_number)

        var currentUser = db.getUser(intent.getIntExtra("userId", -1))
        editTextUpdateFirstName.setText(currentUser.firstName)
        editTextUpdateLastName.setText(currentUser.lastName)
        editTextUpdateMail.setText(currentUser.mail)
        editTextUpdatePhone.setText(currentUser.phone)

        updateButton.setOnClickListener(){
            val result = db.updateData(currentUser.id, editTextUpdateFirstName.text.toString(), editTextUpdateLastName.text.toString(), editTextUpdateMail.text.toString(), editTextUpdatePhone.text.toString())
            val userInfoIntent = Intent(this, UserInfoActivity::class.java).apply {}
            userInfoIntent.putExtra("userId", currentUser.id)
            finish()
            startActivity(userInfoIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}