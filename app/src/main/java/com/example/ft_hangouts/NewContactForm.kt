package com.example.ft_hangouts

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout

class NewContactForm : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact_form)

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        toolbar.setTitle("Créer un contact")
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.close_cross);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val create_contact = findViewById<Button>(R.id.create_contact)
        val editTextFirstName = findViewById<TextInputLayout>(R.id.first_name)
        val editTextLastName = findViewById<TextInputLayout>(R.id.last_name)
        val editTextMail = findViewById<TextInputLayout>(R.id.email_address)
        val editTextPhone = findViewById<TextInputLayout>(R.id.phone_number)

        val color: String = getRandomColor()

        create_contact.setOnClickListener(){
            var newUserId : Long = 0
            //val main_intent = Intent(this, MainActivity::class.java).apply{}
            if (editTextFirstName.editText?.text.toString().isNotEmpty()
                && editTextLastName.editText?.text.toString().isNotEmpty()
                && editTextMail.editText?.text.toString().isNotEmpty()
                && editTextPhone.editText?.text.toString().isNotEmpty()){
                val db = DatabaseHelper(this)

                newUserId = db.insertContact(color, editTextFirstName.editText?.text.toString(), editTextLastName.editText?.text.toString(), editTextMail.editText?.text.toString(), editTextPhone.editText?.text.toString())
                Log.i("Database", "newId: " + newUserId.toString())
            }
            val userInfoIntent = Intent(this, UserInfoActivity::class.java).apply {}
            val userId: Int = newUserId.toInt()
            userInfoIntent.putExtra("userId", userId)
            finish()
            startActivity(userInfoIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                /*val mainActivityIntent = Intent(this, MainActivity::class.java).apply {}
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(mainActivityIntent)*/
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getRandomColor(): String {
        var rgbString: String = "" + (0..255).random() + " " + (0..255).random() + " " + (0..255).random()

        return  rgbString
    }
}