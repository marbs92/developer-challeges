package com.example.testcoopelrepos.login.viewModel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.generic.Resource
import com.example.data.network.repository.auth.AuthRepository
import com.example.data.utlis.await
import com.example.testcoopelrepos.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    repository: AuthRepository,
    private val firebaseAuth: FirebaseAuth,
    private val provider: OAuthProvider.Builder,
) : ViewModel() {

    private val _loginResult = MutableLiveData<Resource<FirebaseUser>?>(null)
    val loginResult: LiveData<Resource<FirebaseUser>?> = _loginResult

    init {
        if (repository.currentUser != null) {
            _loginResult.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun doLogin(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        _loginResult.postValue(Resource.Loading)
        val result = login(activity)
        _loginResult.postValue(result)
    }

    private suspend fun login(activity: Activity): Resource<FirebaseUser>? {
        provider.addCustomParameter("login", "")
        val scopes = listOf("user:email")
        provider.scopes = scopes


        val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult
        if (pendingResultTask != null) {
            return try {
                val result = pendingResultTask.await()
                Resource.Success(result.user!!)
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure(e.message ?: activity.getString(R.string.error_message))
            }
        } else {

            return try {
                val result = firebaseAuth.startActivityForSignInWithProvider(
                    activity,
                    provider.build()
                ).await()
                Resource.Success(result.user!!)
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure(e.message ?: activity.getString(R.string.error_message))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (viewModelScope.isActive) {
            viewModelScope.cancel()
        }
    }
}