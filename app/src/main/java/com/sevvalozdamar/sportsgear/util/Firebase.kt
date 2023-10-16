package com.sevvalozdamar.sportsgear.util

import com.google.firebase.auth.FirebaseAuth

class Firebase{
    companion object{
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
    }
}
