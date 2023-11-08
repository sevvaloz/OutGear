package com.sevvalozdamar.sportsgear.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalozdamar.sportsgear.data.model.User
import com.sevvalozdamar.sportsgear.data.repository.FirebaseAuthenticator
import com.sevvalozdamar.sportsgear.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val firebaseAuthenticator: FirebaseAuthenticator
) : ViewModel() {

    private val _result = MutableLiveData<Resource<Unit>>()
    val result: LiveData<Resource<Unit>> = _result

    fun signUpWithEmailAndPassword(user: User, password: String) {
        viewModelScope.launch {
            _result.value =
                try {
                    Resource.Success(
                        firebaseAuthenticator.signupWithEmailAndPassword(
                            user,
                            password
                        )
                    )
                } catch (e: Exception) {
                    Resource.Error(e.toString())
                }
        }
    }
}