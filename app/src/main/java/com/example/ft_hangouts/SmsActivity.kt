package com.example.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout


class SmsActivity : AppCompatActivity() {
    private lateinit var smsEditText: TextInputLayout
    private lateinit var sendButton: ImageButton
    private lateinit var currentUser: User
    private lateinit var intentFilter: IntentFilter
    private lateinit var sms_list: ListView
    private lateinit var db: DatabaseHelper
    private lateinit var listItems: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)
        sms_list = findViewById<ListView>(R.id.sms_list)
        displayMessagesList()
        var fullName: String = currentUser.firstName + " " + currentUser.lastName

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        toolbar.setTitle(fullName)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        smsEditText = findViewById(R.id.sms_editText)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        smsEditText.requestFocus()

        sendButton = findViewById(R.id.send_button)
        sendButton.setOnClickListener(){
            var newSms : Long = 0
            //val main_intent = Intent(this, MainActivity::class.java).apply{}
            if (smsEditText.editText?.text.toString().isNotEmpty()){
                newSms = db.insertSms(currentUser.phone, smsEditText.editText?.text.toString(), true)
                Log.i("Database", "newSMS: " + newSms.toString())
                sendMessage(currentUser.phone, smsEditText.editText?.text.toString())
                displayMessagesList()
                smsEditText.editText?.setText("")
            }
        }
        intentFilter = IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val intentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.extras!!.getString("phoneNumber") == currentUser.phone) {
                displayMessagesList()
            }
        }
    }

    fun sendMessage(phoneNumber: String, smsContent: String) {
        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, smsContent, null, null)
    }

    private fun scrollMyListViewToBottom() {
        sms_list.post(Runnable {
            sms_list.setSelection(listItems.size - 1)
        })
    }

    override fun onStart() {
        super.onStart()

        displayMessagesList()
    }

    fun displayMessagesList(){
        db = DatabaseHelper(this)
        currentUser = db.getUser(intent.getIntExtra("userId", -1))
        val messages = db.readMessages(currentUser.phone)
        listItems = ArrayList<String>()
        for (i in 0 until messages.size){
            val message = messages[i]
            listItems.add(message.smsContent)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        sms_list.adapter = adapter

        adapter.notifyDataSetChanged()
        scrollMyListViewToBottom()
    }

    override fun onResume() {
        registerReceiver(intentReceiver, intentFilter)
        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(intentReceiver)
        super.onPause()
    }
}