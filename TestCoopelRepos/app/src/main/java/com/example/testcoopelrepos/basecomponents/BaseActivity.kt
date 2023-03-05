package com.example.testcoopelrepos.basecomponents


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.testcoopelrepos.components.DialogLoading
import kotlin.reflect.KClass

abstract class BaseActivity<B : ViewBinding, V : ViewModel>(private val vkClass: KClass<V>) :
    AppCompatActivity(),
    BackHandler {


    lateinit var binding: B

    var dialogLoading: DialogLoading? = null

    val viewModel by lazy {
        ViewModelProvider(this)[vkClass.javaObjectType]
    }


    abstract fun getViewBinding(): B
    abstract fun initObservers()
    abstract fun setupView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initObservers()
        setupView()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }

    override fun showProgressBarCustom( isCancelable: Boolean) {
        dialogLoading?.dismiss()
        dialogLoading = DialogLoading(this).apply {
            setCanceledOnTouchOutside(isCancelable)
            show()
        }
    }


    override fun hideProgressBarCustom() {
        dialogLoading?.dismiss()
    }
}