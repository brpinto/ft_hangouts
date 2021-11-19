package com.example.ft_hangouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class UserInfoActivity : AppCompatActivity() {
    val db = DatabaseHelper(this)
    private lateinit var firstNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var mailTextView: TextView
    private lateinit var phoneTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        firstNameTextView = findViewById(R.id.user_first_name)
        lastNameTextView = findViewById(R.id.user_last_name)
        mailTextView = findViewById(R.id.user_mail)
        phoneTextView = findViewById(R.id.user_phone)
        val userId = intent.getIntExtra("userId", -1)
        val user = db.getUser(userId)
        firstNameTextView.text = user.firstName
        lastNameTextView.text = user.lastName
        mailTextView.text = user.mail
        phoneTextView.text = user.phone

        Log.i("User", user.toString())
    }
}