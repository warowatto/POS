package com.payot.pos.Utils

import android.content.Context
import android.util.TypedValue

fun Int.toPx(context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}