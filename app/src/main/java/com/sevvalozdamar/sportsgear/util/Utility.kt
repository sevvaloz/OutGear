package com.sevvalozdamar.sportsgear.util

object Utility {
    fun checkFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> false
            password.isEmpty() -> false
            else -> true
        }
    }
}
