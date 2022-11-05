package com.example.ft_hangouts

import android.content.Intent
import android.graphics.Color.rgb
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout


class UserInfoActivity : AppCompatActivity() {
    val db = DatabaseHelper(this)
    private lateinit var userFullName: TextView
    private lateinit var avatarContent: TextView
    private lateinit var currentUser: User
    private lateinit var updateButton: Button
<<<<<<< HEAD
    private lateinit var userAvatar: ImageView
    private lateinit var smsButton: View
=======
    private lateinit var smsButton: ConstraintLayout
>>>>>>> f590a93bd7d6f158fffb407597a7e88711fc75d5

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        updateButton = findViewById(R.id.update_contact)
<<<<<<< HEAD
        smsButton = findViewById(R.id.sms_card)
=======
        smsButton = findViewById(R.id.sms_button)
>>>>>>> f590a93bd7d6f158fffb407597a7e88711fc75d5

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        userFullName = findViewById(R.id.user_full_name)
        avatarContent = findViewById(R.id.avatar_content)
        val userId = intent.getIntExtra("userId", -1)

        currentUser = db.getUser(userId)
        userFullName.text = currentUser.firstName + " " + currentUser.lastName
        setAvatarColor(userId)
        avatarContent.text = currentUser.firstName.first().uppercase()
        Log.i("Database", currentUser.toString())

        updateButton.setOnClickListener{
            val updateIntent = Intent(this, UpdateUserActivity::class.java).apply{}
            updateIntent.putExtra("userId", currentUser.id)
            //updateIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            finish()
            startActivity(updateIntent)
        }

        smsButton.setOnClickListener{
<<<<<<< HEAD
            val smsIntent = Intent(this, SmsActivity::class.java).apply {}
=======
            val smsIntent = Intent(this, SendMessageActivity::class.java).apply {}
>>>>>>> f590a93bd7d6f158fffb407597a7e88711fc75d5
            smsIntent.putExtra("userId", currentUser.id)
            finish()
            startActivity(smsIntent)
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAvatarColor(userId: Int){
        val userAvatar: ImageView = findViewById(R.id.user_avatar)
        var avatarBackground: Drawable = userAvatar.background

        val currentUser = db.getUser(userId)
        val rgbArray = currentUser.color.split(" ")

        var avatarBg = (avatarBackground as GradientDrawable).mutate()
        (avatarBg as GradientDrawable).setColor(rgb(rgbArray[0].toInt(), rgbArray[1].toInt(), rgbArray[2].toInt()))
    }
}