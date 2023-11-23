package com.sevvalozdamar.sportsgear.ui.profile

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
class ProfileViewModel @Inject constructor(
    private val firebaseAuthenticator: FirebaseAuthenticator
) : ViewModel() {

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    fun signOut() = viewModelScope.launch {
        firebaseAuthenticator.signOut()
    }

    fun getUserInfo() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = try {
            Resource.Success(
                firebaseAuthenticator.getCurrentUser()
            )
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }
}