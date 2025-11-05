package com.example.nutrifit.viewmodel  // Thay bằng package thực tế của bạn

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {  // Nếu muốn đổi thành LoginViewModel, rename class này thành: class LoginViewModel : ViewModel()
    private val auth = FirebaseAuth.getInstance()

    // State để theo dõi trạng thái đăng nhập
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val user: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    // Khởi tạo Google Sign-In Client
    private lateinit var googleSignInClient: GoogleSignInClient

    fun initGoogleSignIn(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1064218808575-c1slpn265f1giaom0qai88nivh50uhst.apps.googleusercontent.com")  // Web Client ID đã thay
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    // Hàm public để lấy Google Sign-In Intent (thay vì truy cập trực tiếp googleSignInClient)
    fun getGoogleSignInIntent(): Intent {
        // Thêm: Sign out trước để clear cache và force prompt hơn (optional nhưng hiệu quả)
        googleSignInClient.signOut()
        return googleSignInClient.signInIntent
    }

    // Đăng nhập bằng Email/Password
    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Success(result.user?.email ?: "Unknown")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    // Đăng ký bằng Email/Password (nếu cần)
    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Success(result.user?.email ?: "Unknown")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign up failed")
            }
        }
    }

    // Xử lý kết quả Google Sign-In (gọi trong launcher của LoginScreen)
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
            _authState.value = AuthState.Success(result.user?.email ?: "Unknown")
        } catch (e: Exception) {
            _authState.value = AuthState.Error(e.message ?: "Firebase auth failed")
        }
    }

    // Đăng nhập bằng GitHub
    fun signInWithGitHub(activity: Activity) {
        // Thêm: Sign out Firebase trước để clear session và force prompt
        auth.signOut()

        val provider = OAuthProvider.newBuilder("github.com")
            .addCustomParameter("prompt", "login")  // Thêm: Thử force prompt login screen (có thể không work hoàn toàn với Firebase)
            .build()
        auth.startActivityForSignInWithProvider(activity, provider)
            .addOnSuccessListener { authResult ->
                viewModelScope.launch {
                    _authState.value = AuthState.Success(authResult.user?.email ?: "Unknown")
                }
            }
            .addOnFailureListener { e ->
                viewModelScope.launch {
                    _authState.value = AuthState.Error("GitHub sign-in failed: ${e.message}")
                }
            }
    }

    // Đăng xuất
    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
        _authState.value = AuthState.Idle
    }
}