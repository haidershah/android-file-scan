package com.example.filescan.service

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.filescan.R
import java.io.File

class ScanFilesService : IntentService(ScanFilesService::class.java.name) {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, R.string.scan_started, Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
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

    override fun onDestroy() {
        Toast.makeText(this, R.string.scan_completed, Toast.LENGTH_SHORT).show()
    }
}
