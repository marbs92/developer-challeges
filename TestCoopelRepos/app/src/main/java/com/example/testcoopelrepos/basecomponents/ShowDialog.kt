package com.example.testcoopelrepos.basecomponents

import android.app.Activity
import com.example.testcoopelrepos.R
import com.example.testcoopelrepos.databinding.DialogGenericAlertBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object ShowDialog {
    fun showDialogInfo(
        context: Activity,
        title: String? = null,
        message: String? = "",
        isCancelable: Boolean? = false,
        btnAceptar: () -> Unit? = {}
    ) {
        val dialogView = context.layoutInflater.inflate(R.layout.dialog_generic_alert, null, false)
        val dialog =
            MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme).setView(dialogView).show()
        dialog.setCancelable(isCancelable!!)
        val bindingDialog = DialogGenericAlertBinding.bind(dialogView!!)
        bindingDialog.apply {

            txtOne.text = if (title.isNullOrEmpty()) "Algo no está bien" else title
            txtTwo.text = message

            btnRetry.setOnClickListener {
                btnAceptar()
                dialog.dismiss()
            }
        }
    }
}
