package com.example.filescan.service

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import kotlinx.coroutines.*
import java.io.File
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filescan.receiver.FileScanReceiver.Companion.ACTION_POST_FILE_NAME
import com.example.filescan.receiver.FileScanReceiver.Companion.EXTRA_FILE_NAME

class FileScanService : Service() {

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
        sendBroadcast(file.name)
    }

    private fun sendBroadcast(fileName: String) {
        val intent = Intent(ACTION_POST_FILE_NAME)
        intent.putExtra(EXTRA_FILE_NAME, fileName)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
