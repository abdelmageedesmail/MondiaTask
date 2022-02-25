package com.abdelmageed.mondiatask.utils

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.qualifiers.ActivityContext
import java.lang.Exception
import javax.inject.Inject

class ProgressLoading(val context: Context) {
    val show: ProgressDialog = ProgressDialog(context)

    fun showLoading() {
        insertIndex()
        show.setCanceledOnTouchOutside(false)
        show.show()
    }

    fun insertIndex() {
        show.setMessage("loading...")
    }

    fun cancelLoading() {
        show!!.cancel()

    }
}