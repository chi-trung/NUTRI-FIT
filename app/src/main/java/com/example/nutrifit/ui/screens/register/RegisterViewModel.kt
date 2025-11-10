package com.example.nutrifit.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.model.User
import com.example.nutrifit.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository = UserRepository()

    // State to represent the registration result
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    fun registerWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            try {
                // Step 1: Create user with Firebase Auth
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = authResult.user

                if (firebaseUser != null) {
                    // Step 2: Create a User object with only the essential information
                    val newUser = User(
                        id = firebaseUser.uid,
                        email = email
                    )

                    // Step 3: Save the user to Firestore
                    val saveResult = userRepository.saveUser(newUser)
                    if (saveResult.isSuccess) {
                        _registrationState.value = RegistrationState.Success
                    } else {
                        _registrationState.value = RegistrationState.Error(saveResult.exceptionOrNull()?.message ?: "Failed to save user data.")
                    }
                } else {
                    _registrationState.value = RegistrationState.Error("Failed to create user.")
                }
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }
}

// Sealed class to represent different states of the registration process
sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}
