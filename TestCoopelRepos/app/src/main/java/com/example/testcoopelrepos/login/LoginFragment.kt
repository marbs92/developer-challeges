package com.example.testcoopelrepos.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testcoopelrepos.R
import com.example.testcoopelrepos.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {


    private lateinit var binding: FragmentLoginBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        setupView()
        initObservers()
    }

    private fun initObservers() {

    }

    private fun setupView() {
        binding.apply {
            btnLogin.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }


}