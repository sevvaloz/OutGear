package com.sevvalozdamar.sportsgear.utils

import android.app.AlertDialog
import android.content.Context

object PopupHelper {
    fun showErrorPopup(context: Context?, message: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton(
            "Close"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun showDeleteFavPopup(context: Context?, onYesClicked: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure that you want to delete all your favorites?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, _ ->
            onYesClicked()
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun showClearCartPopup(context: Context?, onYesClicked: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure that you want to clear your cart?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, _ ->
            onYesClicked()
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}