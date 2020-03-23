package com.example.news.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.databinding.ProgressDialogBinding

/**
 * Created by SURYA N on 22/3/20.
 */
object ViewUtils {

    fun showProgress(context: Context, msg: String): AlertDialog? {
        try {
            val binding = DataBindingUtil.inflate<ProgressDialogBinding>(LayoutInflater.from(context), R.layout.progress_dialog, null, false)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setView(binding.root)
            mBuilder.setCancelable(false)
            //show dialog
            val mAlertDialog = mBuilder.show()

            binding.tvProgressMsg.text = msg
            return mAlertDialog
        }catch (e:Exception){

        }
        return null
    }

    fun dismissProgress(alertDialog: AlertDialog?) {
        if (alertDialog?.isShowing == true) {
            alertDialog.dismiss()
        }
    }


}