package com.example.filescan.viewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filescan.R
import com.example.filescan.service.FileScanService

class FileScanViewModel : ViewModel() {

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String> get() = _fileName

    private val _scanBtnText = MutableLiveData<Int>(R.string.start_scan)
    val scanBtnText: LiveData<Int> get() = _scanBtnText

    fun onScanClicked(activity: AppCompatActivity) {
        when (_scanBtnText.value) {
            R.string.start_scan -> startScan(activity)
            R.string.stop_scan -> stopScan(activity)
        }
    }

    private fun startScan(activity: AppCompatActivity) {
        _scanBtnText.value = R.string.stop_scan

        val serviceIntent = Intent(activity.applicationContext, FileScanService::class.java)
        activity.startService(serviceIntent)
    }

    private fun stopScan(activity: AppCompatActivity) {
        _scanBtnText.value = R.string.start_scan

        val serviceIntent = Intent(activity.applicationContext, FileScanService::class.java)
        activity.stopService(serviceIntent)
    }

    fun setFileName(fileName: String?) {
        _fileName.value = fileName
    }
}
