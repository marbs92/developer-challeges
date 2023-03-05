package com.example.testcoopelrepos.detailRepo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.testcoopelrepos.R
import com.example.testcoopelrepos.databinding.FragmentDetailRepoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRepoFragment : Fragment(R.layout.fragment_detail_repo) {


    lateinit var binding: FragmentDetailRepoBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailRepoBinding.bind(view)
        initObservers()
        setupView()


    }

    private fun initObservers() {

    }

    private fun setupView() {

    }


}