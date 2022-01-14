package com.example.ft_hangouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class UserInfoActivity : AppCompatActivity() {
    val db = DatabaseHelper(this)
    private lateinit var userFullName: TextView
    private lateinit var avatarContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        userFullName = findViewById(R.id.user_full_name)
        avatarContent = findViewById(R.id.avatar_content)
        val userId = intent.getIntExtra("userId", -1)
        val user = db.getUser(userId)
        userFullName.text = user.firstName + " " + user.lastName
        avatarContent.text = user.firstName.first().uppercase()
        Log.i("User", user.toString())
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.user_info_menu, menu)
        return true
    }
}