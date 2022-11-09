package com.example.ft_hangouts

data class Message(var id: Int, var phoneNumber: String, var smsContent: String, var sender: String){
    constructor(message: Message): this(message.id, message.phoneNumber, message.smsContent, message.sender)

    public fun Messages(id: Int, phoneNumber: String, smsContent: String, sender: String){
        this.id = id
        this.phoneNumber = phoneNumber
        this.smsContent = smsContent
        this.sender = sender
    }
}
