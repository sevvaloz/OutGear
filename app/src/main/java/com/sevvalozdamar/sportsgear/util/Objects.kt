package com.sevvalozdamar.sportsgear.util

import com.google.firebase.auth.FirebaseAuth

object Utility {
    fun checkFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> false
            password.isEmpty() -> false
            else -> true
        }
    }
}

object Firebase {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
}