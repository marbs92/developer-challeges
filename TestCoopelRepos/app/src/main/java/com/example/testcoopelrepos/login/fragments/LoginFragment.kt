package com.example.testcoopelrepos.login.fragments

import androidx.navigation.fragment.findNavController
import com.example.testcoopelrepos.basecomponents.BaseFragment
import com.example.testcoopelrepos.basecomponents.ShowDialog.showDialogInfo
import com.example.testcoopelrepos.databinding.FragmentLoginBinding
import com.example.testcoopelrepos.login.viewModel.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginFragmentViewModel>(LoginFragmentViewModel::class) {
    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun initObservers() {
        viewModel.loginResult.observe(viewLifecycleOwner) { authUserResult ->

            authUserResult?.apply {
                if (authUserResult.status == 200 || authUserResult.data != null){
                    goToHome()
                }else{
                    showDialogInfo(requireActivity(), authUserResult.message)
                }
            }
        }
    }

    override fun setupView() {
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.doLogin(requireActivity())
            }
        }
    }

    private fun goToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
        hideProgressBarCustom()
    }
}
