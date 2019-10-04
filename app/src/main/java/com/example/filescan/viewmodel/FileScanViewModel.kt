package com.example.filescan.viewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filescan.service.ScanFilesService

class FileScanViewModel : ViewModel() {

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String> get() = _fileName

    fun scanFiles(activity: AppCompatActivity) {
        val intent = Intent(activity.applicationContext, ScanFilesService::class.java)
        activity.startService(intent)
    }
}
