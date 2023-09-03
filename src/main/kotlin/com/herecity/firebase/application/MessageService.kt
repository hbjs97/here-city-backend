package com.herecity.firebase.application

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Service

@Service
class MessageService {
    fun send(token: String) {
        val message = Message.builder()
            .putData("title", "title")
            .putData("body", "body")
            .setToken(token)
            .build()
        FirebaseMessaging.getInstance().send(message)
    }
}
