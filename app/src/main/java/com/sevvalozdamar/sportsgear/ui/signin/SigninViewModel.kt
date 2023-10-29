package com.sevvalozdamar.sportsgear.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.sevvalozdamar.sportsgear.data.repository.FirebaseAuthenticator
import com.sevvalozdamar.sportsgear.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val firebaseAuthenticator: FirebaseAuthenticator
) : ViewModel() {

    private val _result = MutableLiveData<Resource<FirebaseUser>>()
    val result: LiveData<Resource<FirebaseUser>> = _result

    fun signinWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _result.value = Resource.Success(
                firebaseAuthenticator.signinWithEmailAndPassword(
                    email,
                    password
                ).user!!
            )
        }
    }
}