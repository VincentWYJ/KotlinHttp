package com.dylan.kotlinhttp.Utils

import android.content.Intent
import android.content.Context
import android.util.Log
import android.widget.Toast

object Utils {
    fun startHttpRequest(context: Context, action: String) {
        val intent = Intent(action)
        context.startActivity(intent)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLog(TAG: String, message: String) {
        Log.i(TAG, message)
    }
}