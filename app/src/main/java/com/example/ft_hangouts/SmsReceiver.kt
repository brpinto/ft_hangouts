package com.example.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import android.telephony.SmsMessage
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.Objects

class SmsReceiver: BroadcastReceiver() {
    private val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action.equals(SMS_RECEIVED)){
            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                val pdus: Array<Object> = bundle.get("pdus") as Array<Object>
                if (pdus.size !== 0)
                    return
                val messages: Array<SmsMessage?> = kotlin.arrayOfNulls<SmsMessage>(pdus.size)
                var msgBody: String = ""
                for (i in 0..messages.size) {
                    messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, SMS_RECEIVED_ACTION)
                    msgBody = messages[i]?.messageBody.toString()
                }
                val msgSender: String = messages[0]?.originatingAddress.toString()
                val db = DatabaseHelper(context)
                db.insertSms(msgSender, msgBody)
                db.close()

                val broadcastIntent = Intent().apply {
                    action = SMS_RECEIVED_ACTION
                    putExtra("phoneNumber", msgSender)
                }
                context.sendBroadcast(broadcastIntent)
            }
        }
    }
}