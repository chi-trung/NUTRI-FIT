package com.example.nutrifit.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userRepository = UserRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val user: FirebaseUser, val isNewUser: Boolean) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    fun initGoogleSignIn(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1064218808575-c1slpn265f1giaom0qai88nivh50uhst.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun getGoogleSignInIntent(): Intent {
        googleSignInClient.signOut()
        return googleSignInClient.signInIntent
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                result.user?.let { user ->
                    checkNewUser(user)
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun handleGoogleSignInResult(data: Intent?) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                _authState.value = AuthState.Error("Google sign-in failed: ${e.message}")
            }
        }
    }

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        try {
            val result = auth.signInWithCredential(credential).await()
            result.user?.let { user ->
                checkNewUser(user)
            }
        } catch (e: Exception) {
            _authState.value = AuthState.Error(e.message ?: "Firebase auth failed")
        }
    }

    fun signInWithGitHub(activity: Activity) {
        auth.signOut()

        val provider = OAuthProvider.newBuilder("github.com")
            .addCustomParameter("prompt", "login")
            .build()
        auth.startActivityForSignInWithProvider(activity, provider)
            .addOnSuccessListener { authResult ->
                viewModelScope.launch {
                    authResult.user?.let { user ->
                        checkNewUser(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                viewModelScope.launch {
                    _authState.value = AuthState.Error("GitHub sign-in failed: ${e.message}")
                }
            }
    }

    private suspend fun checkNewUser(user: FirebaseUser) {
        val isNewUser = userRepository.getUser(user.uid).isFailure
        _authState.value = AuthState.Success(user, isNewUser)
    }

    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
        _authState.value = AuthState.Idle
    }
}
