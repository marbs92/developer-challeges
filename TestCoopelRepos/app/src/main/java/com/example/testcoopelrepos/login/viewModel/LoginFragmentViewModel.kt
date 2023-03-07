package com.example.testcoopelrepos.login.viewModel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.generic.LocalResult
import com.example.data.local.datastore.DataStoreManager
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    repository: AuthRepository,
    private val firebaseAuth: FirebaseAuth,
    private val provider: OAuthProvider.Builder,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _loginResult = MutableLiveData<LocalResult<FirebaseUser>?>(null)
    val loginResult: LiveData<LocalResult<FirebaseUser>?> = _loginResult

    init {
        if (repository.currentUser != null) {
            _loginResult.value = LocalResult(repository.currentUser!!)
        }
    }

    fun doLogin(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        val result = login(activity)
        _loginResult.postValue(result)
    }

    private suspend fun login(activity: Activity): LocalResult<FirebaseUser>? {
        provider.addCustomParameter("login", "")
        val scopes = listOf("user:email")
        provider.scopes = scopes


        val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult
        if (pendingResultTask != null) {
            return try {
                val result = pendingResultTask.await()
                LocalResult(result.user!!, 200)
            } catch (e: Exception) {
                e.printStackTrace()
                LocalResult(null, -1, e.message ?: activity.getString(R.string.error_message))
            }
        } else {

            return try {
                val result = firebaseAuth.startActivityForSignInWithProvider(
                    activity,
                    provider.build()
                ).await()
                saveDataInLocalStorage(
                    result.user?.displayName,
                    result.additionalUserInfo?.username,
                    result.user?.photoUrl?.toString()
                )
                LocalResult(result.user!!, 200)
            } catch (e: Exception) {
                e.printStackTrace()
                LocalResult(null, -1, e.message ?: activity.getString(R.string.error_message))
            }
        }
    }

    private suspend fun saveDataInLocalStorage(
        name: String?,
        username: String?,
        urlPhoto: String?
    ) {
        dataStoreManager.setPreferencesData(name, username, urlPhoto)
    }

    override fun onCleared() {
        super.onCleared()
        if (viewModelScope.isActive) {
            viewModelScope.cancel()
        }
    }
}