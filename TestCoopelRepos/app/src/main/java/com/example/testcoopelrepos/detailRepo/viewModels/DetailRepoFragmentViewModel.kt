package com.example.testcoopelrepos.detailRepo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.generic.LocalResult
import com.example.data.local.datastore.DataStoreManager
import com.example.data.local.datastore.PreferencesData
import com.example.domain.network.response.readme.ReadmeResponseModel
import com.example.domain.network.response.tags.GetTagsResponseModelItem
import com.example.usescases.usescases.readme.PerformReadme
import com.example.usescases.usescases.tags.PerformGetTags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailRepoFragmentViewModel @Inject constructor(
    private val performGetTags: PerformGetTags,
    private val performReadme: PerformReadme,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _tagsResult = MutableLiveData<LocalResult<List<GetTagsResponseModelItem>>?>()
    val tagsResult: LiveData<LocalResult<List<GetTagsResponseModelItem>>?> = _tagsResult

    fun getTagsList(owner: String, repo: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = performGetTags(owner, repo)
        _tagsResult.postValue(result)
    }


    private val _readmeResult = MutableLiveData<LocalResult<ReadmeResponseModel>>()
    val readmeResult: LiveData<LocalResult<ReadmeResponseModel>> = _readmeResult


    // html_url
    fun getReadmeList(owner: String, repo: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = performReadme(owner, repo)
        _readmeResult.postValue(result)
    }

    val userData = MutableLiveData<PreferencesData>()

    suspend fun getUserData() {
        userData.value = dataStoreManager.getPreferencesData()
    }
}