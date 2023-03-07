package com.example.testcoopelrepos.home.fragment

import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.local.repos.GetReposModelItem
import com.example.testcoopelrepos.basecomponents.BaseFragment
import com.example.testcoopelrepos.basecomponents.ShowDialog
import com.example.testcoopelrepos.components.hideView
import com.example.testcoopelrepos.components.showView
import com.example.testcoopelrepos.databinding.FragmentHomeBinding
import com.example.testcoopelrepos.home.adapter.ReposAdapter
import com.example.testcoopelrepos.home.viewModels.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>(HomeFragmentViewModel::class) {

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun initObservers() {
        viewModel.apply {
            getReposResult.observe(viewLifecycleOwner) { listResult ->

                if (listResult.status == 200) {
                    if (listResult.data.isNullOrEmpty()) {
                        binding.tvEmptyList.showView()
                    } else {
                        setupListIntoAdapter(userData.value?.name, listResult.data!!)
                    }
                } else {
                    ShowDialog.showDialogInfo(
                        requireActivity(),
                        message = listResult.message
                    )
                }
            }
        }
    }

    override fun setupView() {
        binding.apply {
            showProgressBarCustom()


            lifecycleScope.launch {
                viewModel.apply {
                    getUserData()
                    userData.value?.userName?.let {
                        getReposSource(it)
                    }
                }
            }

            search.apply {
                etSearchService.apply {
                    setOnEditorActionListener { query, actionId, keyEvent ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            showProgressBarCustom()
                            filterResults(query.text.toString())
                            true
                        } else {
                            false
                        }
                    }
                    addTextChangedListener {
                        if (it.isNullOrEmpty()) {
                            binding.tvEmptyList.hideView()
                            val result = viewModel.getReposResult.value?.data
                            setupListIntoAdapter(viewModel.userData.value?.name, result ?: listOf())
                        }
                    }
                }
                ivShowResults.setOnClickListener {
                    filterResults(search.etSearchService.text.toString())
                }
            }
        }
    }

    private fun filterResults(query: String) {
        val result = viewModel.getReposResult.value?.data
        val filteredData = result?.filter { item ->
            query.contains(item.name) || query.contains(item.language) || query.contains(item.visibility)

        }
        binding.tvEmptyList.hideView()
        if (result.isNullOrEmpty() || filteredData.isNullOrEmpty()) {
            binding.tvEmptyList.showView()
        } else if (query.isEmpty()) {
            setupListIntoAdapter(viewModel.userData.value?.name, result)
        } else {
            setupListIntoAdapter(viewModel.userData.value?.name, filteredData)
        }
        hideProgressBarCustom()
    }

    private fun setupListIntoAdapter(ownerName: String?, reposList: List<GetReposModelItem>) {
        hideProgressBarCustom()
        val reposAdapter = ReposAdapter(ownerName, reposList) { dataItem ->
            val navigate = HomeFragmentDirections.actionHomeFragmentToDetailRepoFragment(
                dataItem.name,
                dataItem.defaultBranch,
                dataItem.size.toString()
            )
            findNavController().navigate(navigate)
        }
        binding.rvRepos.adapter = reposAdapter
    }
}