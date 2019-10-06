package com.example.filescan.viewmodel

import android.content.Intent
import android.view.View
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

    private val _scanCompletedVisibility = MutableLiveData<Int>(View.GONE)
    val scanCompletedVisibility: LiveData<Int> get() = _scanCompletedVisibility

    private val _largestFileName = MutableLiveData<String>()
    val largestFileName: LiveData<String> get() = _largestFileName

    private val _largestFileSize = MutableLiveData<String>()
    val largestFileSize: LiveData<String> get() = _largestFileSize

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
        _scanCompletedVisibility.value = View.GONE

        val serviceIntent = Intent(activity.applicationContext, FileScanService::class.java)
        activity.stopService(serviceIntent)
    }

    fun setFileName(fileName: String?) {
        _fileName.value = fileName
    }

    fun setIsCompleted(completed: Boolean) {
        if (completed) {
            _scanCompletedVisibility.value = View.VISIBLE
        }
    }

    fun setLargestFileInfo(largestFileName: String?, largestFileSize: Long) {
        _largestFileName.value = largestFileName

        var formattedSize = largestFileSize
        if(formattedSize > 1_000_000) {
            formattedSize /= 1_000_000L
        }
        _largestFileSize.value = formattedSize.toString()
    }
}
