package com.payot.pos.Utils

import java.text.DecimalFormat

val format = DecimalFormat("#,###")

fun Int.toLocaleString(): String {
    return format.format(this)
}