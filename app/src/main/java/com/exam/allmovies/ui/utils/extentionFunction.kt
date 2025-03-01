package com.exam.allmovies.ui.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatReleaseDate(date: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd MMMM yyyy",Locale.ENGLISH)

        val parsedDate = inputFormat.parse(date)
        outputFormat.format(parsedDate ?: return "Invalid date")
    } catch (e: Exception) {
        "Invalid date"
    }
}
