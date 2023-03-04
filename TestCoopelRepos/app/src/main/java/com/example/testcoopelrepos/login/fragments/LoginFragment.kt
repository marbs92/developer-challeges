package com.example.testcoopelrepos.login.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.data.generic.Resource
import com.example.testcoopelrepos.R
import com.example.testcoopelrepos.databinding.FragmentLoginBinding
import com.example.testcoopelrepos.login.viewModel.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {


    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginFragmentViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        setupView()
        initObservers()
    }

    private fun initObservers() {
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when(result){
                is Resource.Failure -> {
                    // show warning alert
                }
                Resource.Loading -> {
                    // show loader
                }
                is Resource.Success -> {
                    goToHome()
                }
                null -> {
                    // show warning alert
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.doLogin(requireActivity())
            }
        }
    }

    private fun goToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}
