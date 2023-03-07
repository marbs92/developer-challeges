package com.example.testcoopelrepos.main.viewModels

import androidx.lifecycle.ViewModel
import com.example.data.local.datastore.DataStoreManager
import com.example.data.local.datastore.PreferencesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


}