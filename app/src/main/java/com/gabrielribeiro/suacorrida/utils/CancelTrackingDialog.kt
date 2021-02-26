package com.gabrielribeiro.suacorrida.utils

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gabrielribeiro.suacorrida.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelTrackingDialog : DialogFragment() {

    private var yesListener : (() -> Unit)? = null
    fun setYesListener(listener : () -> Unit) {
        yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setTitle(getString(R.string.cancelar_corrida))
                .setMessage(getString(R.string.certeza_que_dejsesa_cancelar_corrida))
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton(getString(R.string.sim)){_ , _ ->
                    yesListener?.let{yes ->
                        yes()
                    }
                }
                .setNegativeButton(getString(R.string.nap)){dialogInterface, _ ->
                    dialogInterface.cancel()

                }
                .create()

    }
}