package com.example.gmail

data class Email(
    val id: Int,
    val sender: String,
    val subject: String,
    val body: String,
    val timestamp: String,
    val isRead: Boolean,
    val isStarred: Boolean
)
