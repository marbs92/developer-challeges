package com.example.testcoopelrepos.home.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.generic.LocalResult
import com.example.data.local.datastore.DataStoreManager
import com.example.data.local.datastore.PreferencesData
import com.example.domain.local.repos.GetReposModelItem
import com.example.usescases.usescases.repos.PerformRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getReposSource: PerformRepos,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    
    private val _getReposResult = MutableLiveData<LocalResult<List<GetReposModelItem>>>()
    val getReposResult: LiveData<LocalResult<List<GetReposModelItem>>> = _getReposResult

    fun getReposSource(username: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = getReposSource.invoke(username)
        _getReposResult.postValue(result)
    }

    val userData = MutableLiveData<PreferencesData>()

    suspend fun getUserData() {
        userData.value = dataStoreManager.getPreferencesData()
    }


}