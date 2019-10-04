package com.example.filescan.viewmodel

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filescan.R
import com.example.filescan.service.ScanFilesService

class FileScanViewModel : ViewModel() {

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String> get() = _fileName

    private val _scanBtnText = MutableLiveData<Int>(R.string.start_scan)
    val scanBtnText: LiveData<Int> get() = _scanBtnText

    private lateinit var intent: Intent

    fun onScanClicked(activity: AppCompatActivity) {
        when (_scanBtnText.value) {
            R.string.start_scan -> startScan(activity)
            R.string.stop_scan -> stopScan(activity)
        }
    }

    private fun startScan(activity: AppCompatActivity) {
        intent = Intent(activity.applicationContext, ScanFilesService::class.java)
        activity.startService(intent)

        _scanBtnText.value = R.string.stop_scan
    }

    private fun stopScan(activity: AppCompatActivity) {
        if (!::intent.isInitialized) {
            return
        }

        activity.stopService(intent)

        _scanBtnText.value = R.string.start_scan
    }
}
