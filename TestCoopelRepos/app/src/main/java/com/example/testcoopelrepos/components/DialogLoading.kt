package com.example.testcoopelrepos.components

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.testcoopelrepos.basecomponents.BaseDialog
import com.example.testcoopelrepos.databinding.LoadingViewBinding

class DialogLoading(context: Context) : BaseDialog(context) {
    lateinit var mBinding: LoadingViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LoadingViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

    }
}
