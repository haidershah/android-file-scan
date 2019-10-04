package com.example.filescan.service

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import java.io.File

class ScanFilesService : Service() {

    private val job = Job()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO + job).launch {
            scanFiles(Environment.getExternalStorageDirectory())
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private suspend fun scanFiles(file: File) {
        processFile(file)

        if (file.isDirectory) {
            for (innerFile in file.listFiles()) {
                delay(500)
                scanFiles((innerFile))
            }
        }
    }

    private fun processFile(file: File) {
        Log.e("yooooo", "file: ${file.name}")
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
