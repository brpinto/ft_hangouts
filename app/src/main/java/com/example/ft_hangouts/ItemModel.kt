package com.example.ft_hangouts

class ItemModel {
    private var firstName: String? = null
    private var lastName: String? = null
    private var mail: String? = null
    private var phone: String? = null
    private var avatar: String? = null

    fun ItemModel(firstName: String?, lastName: String?, mail: String?, phone: String?, avatar: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.mail = mail
        this.phone = phone
        this.avatar = avatar
    }

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getMail(): String? {
        return mail
    }

    fun setMail(mail: String) {
        this.mail = mail
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }
}