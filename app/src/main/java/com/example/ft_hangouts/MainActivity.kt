package com.example.ft_hangouts

import android.Manifest
import android.Manifest.permission.*
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handlePermissions()

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);

        val db = DatabaseHelper(this)
       // val contacts_list = findViewById<ListView>(R.id.contacts_list)

        val addContact = findViewById<FloatingActionButton>(R.id.add_contact)

        addContact.setOnClickListener{
            val contactIntent = Intent(this, NewContactForm::class.java).apply{}
            startActivity(contactIntent)
        }

        //displayContactsList(db, contacts_list)
    }

    private fun handlePermissions() {
        val sendPerm = ContextCompat.checkSelfPermission(applicationContext, SEND_SMS)
        val readPerm = ContextCompat.checkSelfPermission(applicationContext, READ_SMS)
        val receivePerm = ContextCompat.checkSelfPermission(applicationContext, RECEIVE_SMS)

        if (sendPerm != PackageManager.PERMISSION_GRANTED
            && readPerm != PackageManager.PERMISSION_GRANTED
            && receivePerm != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(SEND_SMS, READ_SMS, RECEIVE_SMS),
                42
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 42) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val db = DatabaseHelper(this)
        val contacts_list = findViewById<ListView>(R.id.contacts_list)

        displayContactsList(db, contacts_list)
    }

    override fun onResume() {
        super.onResume()
        val db = DatabaseHelper(this)
        val contacts_list = findViewById<ListView>(R.id.contacts_list)

        displayContactsList(db, contacts_list)
    }

    fun displayContactsList(db: DatabaseHelper, contacts: ListView){
        val users = db.readContacts()
        val listItems = ArrayList<String>()
        for (i in 0 until users.size){
            val contact = users[i]
            listItems.add(contact.firstName + " " + contact.lastName)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        contacts.adapter = adapter
        handleContact(contacts, users)
    }

    fun handleContact(list: ListView, users: ArrayList<User>){
        list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val userInfoIntent = Intent(this, UserInfoActivity::class.java).apply {}
            userInfoIntent.putExtra("userId", users[position].id)
            startActivity(userInfoIntent)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(R.layout.activity_main)
    }
}