package com.example.ft_hangouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class UserInfoActivity : AppCompatActivity() {
    val db = DatabaseHelper(this)
    private lateinit var userFullName: TextView
    private lateinit var avatarContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        userFullName = findViewById(R.id.user_full_name)
        avatarContent = findViewById(R.id.avatar_content)
        val userId = intent.getIntExtra("userId", -1)
        val user = db.getUser(userId)
        userFullName.text = user.firstName + " " + user.lastName
        avatarContent.text = user.firstName.first().uppercase()
        Log.i("User", user.toString())
    }
}