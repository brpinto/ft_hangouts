package com.example.ft_hangouts

data class User(var firstName: String, var lastName: String, var mail: String, var phone: String){
    constructor(user: User): this(user.firstName, user.lastName, user.mail, user.phone)

    public fun Users(firstName: String, lastName: String, mail: String, phone: String){
        this.firstName = firstName
        this.lastName = lastName
        this.mail = mail
        this.phone = phone
    }
}
