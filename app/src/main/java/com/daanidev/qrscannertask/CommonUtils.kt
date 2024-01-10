package com.daanidev.qrscannertask

import android.content.Context
import android.widget.Toast

object CommonUtils {

    const val CANVAS_WIDTH_PERCENTAGE = 0.6f
    const val CANVAS_HEIGHT_PERCENTAGE = 0.3f
    const val CANVAS_MULTIPLY_BY_2 = 2
    fun showToastMessage(context: Context, message: String) {
        context.apply {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}