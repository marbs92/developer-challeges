package com.example.testcoopelrepos.basecomponents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.testcoopelrepos.components.DialogLoading
import kotlin.reflect.KClass

abstract class BaseFragment<T : ViewBinding, V : ViewModel>(private val vkClass: KClass<V>) :
    Fragment() {
    var cuBackHandler: BackHandler? = null
    private var listenerBackPress: BackFragment? = null

    var dialogLoading: DialogLoading? = null

    private var _binding: T? = null
    val binding: T
        get() = _binding!!

    val viewModel by lazy {
        ViewModelProvider(this)[vkClass.javaObjectType]
    }

    abstract fun getViewBinding(): T
    abstract fun initObservers()
    abstract fun setupView()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initBackHandler()
        setupView()
    }

    private fun initBackHandler() {
        cuBackHandler = if (activity is BackHandler) {
            activity as BackHandler
        } else {
            object : BackHandler {
                override fun showProgressBarCustom(isCancelable: Boolean) {
                    showCustomLoading(isCancelable)
                }

                override fun hideProgressBarCustom() {
                    hideCustomLoading()
                }
            }
        }
    }


    private fun hideCustomLoading() {
        dialogLoading?.dismiss()
    }

    private fun showCustomLoading(cancelable: Boolean) {
        dialogLoading?.dismiss()
        dialogLoading = this@BaseFragment.context?.let { DialogLoading(it) }?.apply {
            setCanceledOnTouchOutside(cancelable)
            show()
        }
    }

    fun showProgressBarCustom(isCancelable: Boolean = false) {
        cuBackHandler?.showProgressBarCustom(isCancelable)
    }

    fun hideProgressBarCustom() {
        cuBackHandler?.hideProgressBarCustom()
    }

    fun setCUBackFragment(listenerBackPress: BackFragment) {
        this.listenerBackPress = listenerBackPress
    }

    fun onFragmentBackPressed(): Boolean {
        if (listenerBackPress != null)
            listenerBackPress?.onBackPress()

        return false
    }

    interface BackFragment {
        fun onBackPress()
    }

}