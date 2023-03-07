package com.example.testcoopelrepos.detailRepo.fragments

import android.graphics.Color
import android.util.Base64
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.domain.network.response.tags.GetTagsResponseModelItem
import com.example.testcoopelrepos.R
import com.example.testcoopelrepos.basecomponents.BaseFragment
import com.example.testcoopelrepos.basecomponents.ShowDialog.showDialogInfo
import com.example.testcoopelrepos.databinding.FragmentDetailRepoBinding
import com.example.testcoopelrepos.detailRepo.viewModels.DetailRepoFragmentViewModel
import com.example.testcoopelrepos.main.viewModels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailRepoFragment :
    BaseFragment<FragmentDetailRepoBinding, DetailRepoFragmentViewModel>(DetailRepoFragmentViewModel::class) {


    private val args: DetailRepoFragmentArgs by navArgs()
    override fun getViewBinding() = FragmentDetailRepoBinding.inflate(layoutInflater)

    override fun initObservers() {
        viewModel.tagsResult.observe(viewLifecycleOwner) { result ->
            result?.apply {
                if (result.status == 200) {
                    setupTags(result.data!!)
                } else {
                    showDialogInfo(requireActivity(), message = result.message, btnAceptar = {

                    })
                }
            }
        }
        viewModel.readmeResult.observe(viewLifecycleOwner) { result ->
            result?.apply {
                hideProgressBarCustom()
                if (result.status == 200) {
                    setReadmeInTextView(result.data?.content)
                } else {
                    showDialogInfo(requireActivity(), message = result.message)
                }
            }
        }
    }

    override fun setupView() {
        showProgressBarCustom()

        binding.apply {
            tvRepoName.text = args.repoName
            tvBranch.text = args.branchName
            tvSize.text = requireActivity().getString(R.string.size, args.size)

        }

        viewModel.apply {
            lifecycleScope.launch {
                getUserData()
                getTagsList(userData.value!!.userName, args.repoName)
                getReadmeList(userData.value!!.userName, args.repoName)
            }
        }
    }

    private fun setTagIntoView(tag: String) {
        val viewGroup: ViewGroup = binding.tagContent
        val textView = TextView(requireContext())
        textView.apply {
            textSize = 12f
            setTextColor(Color.BLACK)
            setBackgroundResource(R.drawable.blue_rounded_corners)
            text = tag
            setPadding(30, 5, 30, 5)

        }
        viewGroup.addView(textView)
    }

    private fun setReadmeInTextView(content: String?) {
        content?.let {
            binding.tvReadme.text = String(Base64.decode(it, Base64.DEFAULT))
        }
    }

    private fun setupTags(result: List<GetTagsResponseModelItem>) {
        val subList = mutableListOf<GetTagsResponseModelItem>()
        if (result.size > 4) {
            subList.addAll(result.subList(0, 4))
            subList.forEach {
                setTagIntoView(it.name ?: "")
            }
        } else if (result.isNotEmpty()) {
            if (result.first().name != null) {
                result.forEach {
                    setTagIntoView(it.name ?: "")
                }
            }
        }
    }
}