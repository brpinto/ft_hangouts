package com.example.ft_hangouts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

val DB_NAME = "Contacts"
val CONTACTS_TABLE = "Users"
val COL_LAST_NAME = "last_name"
val COL_FIRST_NAME = "first_name"
val COL_MAIL = "mail_address"
val COL_PHONE = "phone_number"
val COL_ID = "id"
val COL_AVATAR_COLOR = "avatar_color"
val SMS_TABLE = "Sms"
val COL_SMS_ID = "smsId"
val COL_SMS_CONTENT = "smsContent"
val COL_SENDER = "amISender"

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val contactsTable = "CREATE TABLE ${CONTACTS_TABLE} (" +
                " ${COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${COL_AVATAR_COLOR} TEXT," +
                " ${COL_FIRST_NAME} TEXT," +
                " ${COL_LAST_NAME} TEXT," +
                " ${COL_MAIL} TEXT," +
                " ${COL_PHONE} TEXT)"
        val smsTable = "CREATE TABLE ${SMS_TABLE} (" +
                "${COL_SMS_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${COL_PHONE} TEXT," +
                "${COL_SMS_CONTENT} TEXT," +
                "${COL_SENDER} BOOLEAN)"
        db.execSQL(contactsTable)
        db.execSQL(smsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    fun insertContact(avatarColor: String, firstName: String, lastName: String, email: String, phone: String): Long{
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COL_AVATAR_COLOR, avatarColor)
            put(COL_FIRST_NAME, firstName)
            put(COL_LAST_NAME, lastName)
            put(COL_MAIL, email)
            put(COL_PHONE, phone)
        }
        val result: Long = db.insert(CONTACTS_TABLE, null, values)
        if (result == (0).toLong())
            Log.i("Database", "User not created")
        else (result == (-1).toLong())
            Log.i("Database", "User created")
        Log.i("Database", "inserData: " + result)
        return result
    }

    fun updateContact(userId: Int, firstName: String, lastName: String, email: String, phone: String): Int{
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COL_FIRST_NAME, firstName)
            put(COL_LAST_NAME, lastName)
            put(COL_MAIL, email)
            put(COL_PHONE, phone)
        }
        return db.update(CONTACTS_TABLE, values, COL_ID + " = " + userId, null)
    }

    fun readContacts(): ArrayList<User> {
        val db = this.readableDatabase

        val query = "select * from " + CONTACTS_TABLE + " ORDER BY " + COL_LAST_NAME
        val result = db.rawQuery(query, null)
        var contacts: ArrayList<User> = ArrayList()
        if (result.moveToFirst()){
            do {
                var user: User = User(
                    result.getInt(0),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
                )
                contacts.add(user)
            }
                while (result.moveToNext())
        }
        result.close()
        return contacts
    }

    fun readMessages(phone: String): ArrayList<Message> {
        val db = this.readableDatabase

        val query = "select * from " + SMS_TABLE + " WHERE phone_number='" + phone + "' ORDER BY " + COL_SMS_ID + " ASC"
        val result = db.rawQuery(query, null)
        var messages: ArrayList<Message> = ArrayList()
        if (result.moveToFirst()){
            do {
                var message: Message = Message(
                    result.getInt(0),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3)
                )
                messages.add(message)
            }
            while (result.moveToNext())
        }
        result.close()
        return messages
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    fun getUser(id: Int): User{
        val db = this.readableDatabase

        val query = "select * from " + CONTACTS_TABLE + " WHERE " + COL_ID + " = " + id
        val result = db.rawQuery(query, null)
        var contacts: ArrayList<User> = ArrayList()
        var foundUser: User = User(0, "", "", "", "", "")
        if (result.moveToFirst()){
            var user: User = User(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getString(5)
            )
            foundUser = user
        }
        result.close()
        return foundUser
    }

    fun deleteUser(userId: Int): Int {
        val db = this.writableDatabase

        return db.delete(CONTACTS_TABLE, COL_ID + " = " + userId, null)
    }

    fun insertSms(phoneNumber: String, smsContent: String, amISender: Boolean = false): Long{
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COL_SMS_CONTENT, smsContent)
            put(COL_SENDER, amISender)
            put(COL_PHONE, phoneNumber)
        }
        val result: Long = db.insert(SMS_TABLE, null, values)
        if (result == (0).toLong())
            Log.i("Database", "Sms not created")
        else (result == (-1).toLong())
        Log.i("Database", "Sms created")
        Log.i("Database", "insertSms: " + result)
        return result
    }
}