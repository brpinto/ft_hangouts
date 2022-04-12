package com.example.ft_hangouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);

        val db = DatabaseHelper(this)
        val contacts_list = findViewById<ListView>(R.id.contacts_list)

        val addContact = findViewById<FloatingActionButton>(R.id.add_contact)

        addContact.setOnClickListener{
            val contactIntent = Intent(this, NewContactForm::class.java).apply{}
            startActivity(contactIntent)
        }

        displayContactsList(db, contacts_list)
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
        val users = db.readData()
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
}