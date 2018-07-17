package com.payot.pos.Utils

import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(format: String): Date {
    return SimpleDateFormat(format).parse(this)
}