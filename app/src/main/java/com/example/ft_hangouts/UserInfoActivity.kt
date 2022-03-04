package com.example.ft_hangouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class UserInfoActivity : AppCompatActivity() {
    val db = DatabaseHelper(this)
    private lateinit var userFullName: TextView
    private lateinit var avatarContent: TextView
    private lateinit var currentUser: User
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        updateButton = findViewById(R.id.update_contact)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        userFullName = findViewById(R.id.user_full_name)
        avatarContent = findViewById(R.id.avatar_content)
        val userId = intent.getIntExtra("userId", -1)
        Log.i("Database", "userInfo: " + userId.toString())
        currentUser = db.getUser(userId)
        userFullName.text = currentUser.firstName + " " + currentUser.lastName
        avatarContent.text = currentUser.firstName.first().uppercase()
        Log.i("Database", currentUser.toString())

        updateButton.setOnClickListener{
            val updateIntent = Intent(this, UpdateUserActivity::class.java).apply{}
            updateIntent.putExtra("userId", currentUser.id)
            //updateIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            finish()
            startActivity(updateIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val mainActivityIntent = Intent(this, MainActivity::class.java).apply {}
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(mainActivityIntent)
                true
            }
            R.id.delete_user -> {
                db.deleteUser(currentUser.id)
                val mainActivityIntent = Intent(this, MainActivity::class.java).apply {}
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(mainActivityIntent)
                return true
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