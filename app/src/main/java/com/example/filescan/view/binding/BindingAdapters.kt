package com.example.filescan.view.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.filescan.R

@BindingAdapter("file_size")
fun TextView.fileSize(fileSize: Long) {
    var formattedFileSize = fileSize

    // size in mb
    if (formattedFileSize >= 1_000_000) {
        formattedFileSize /= 1_000_000L
        text = context.getString(R.string.size_mb, formattedFileSize.toString())
    } else {
        text = context.getString(R.string.size_bytes, formattedFileSize.toString())
    }
}
