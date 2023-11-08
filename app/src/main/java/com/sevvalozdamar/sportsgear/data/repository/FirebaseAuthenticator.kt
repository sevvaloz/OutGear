package com.sevvalozdamar.sportsgear.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sevvalozdamar.sportsgear.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthenticator @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun getFirebaseUserUid(): String = auth.currentUser?.uid.orEmpty()

    suspend fun getCurrentUser(): User {
        val user = firestore.collection("users").document(getFirebaseUserUid()).get().await()
        val name = user["name"] as String
        val surname = user["surname"] as String
        val email = user["email"] as String
        return User(name, surname, email)
    }

    fun isCurrentUserExist() = auth.currentUser != null

    suspend fun signupWithEmailAndPassword(
        user: User,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(user.email, password).await()
        val userModel = hashMapOf(
            "id" to getFirebaseUserUid(),
            "name" to user.name,
            "surname"  to user.surname,
            "email" to user.email
        )
        firestore.collection("users").document(getFirebaseUserUid()).set(userModel).await()
    }

    suspend fun signinWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult = auth.signInWithEmailAndPassword(email, password).await()

    fun signOut() = auth.signOut()

}