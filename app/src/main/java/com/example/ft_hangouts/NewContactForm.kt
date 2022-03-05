package com.example.ft_hangouts

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

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
        val editTextFirstName = findViewById<EditText>(R.id.first_name)
        val editTextLastName = findViewById<EditText>(R.id.last_name)
        val editTextMail = findViewById<EditText>(R.id.email_address)
        val editTextPhone = findViewById<EditText>(R.id.phone_number)

        val color: String = getRandomColor()

        create_contact.setOnClickListener(){
            var newUserId : Long = 0
            //val main_intent = Intent(this, MainActivity::class.java).apply{}
            if (editTextFirstName.text.toString().isNotEmpty()
                && editTextLastName.text.toString().isNotEmpty()
                && editTextMail.text.toString().isNotEmpty()
                && editTextPhone.text.toString().isNotEmpty()){
                val db = DatabaseHelper(this)

                newUserId = db.insertData(color, editTextFirstName.text.toString(), editTextLastName.text.toString(), editTextMail.text.toString(), editTextPhone.text.toString())
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