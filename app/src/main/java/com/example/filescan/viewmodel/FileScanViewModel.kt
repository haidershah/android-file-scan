package com.example.filescan.viewmodel

import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class FileScanViewModel : ViewModel() {

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String> get() = _fileName

    fun scanFiles() {
        scanFiles(Environment.getExternalStorageDirectory())
    }

    private fun scanFiles(file: File) {
        processFile(file)

        if (file.isDirectory) {
            for (innerFile in file.listFiles()) {
                scanFiles((innerFile))
            }
        }
    }

    private fun processFile(file: File) {
        Log.e("yooooo", "file: ${file.name}")
    }
}
