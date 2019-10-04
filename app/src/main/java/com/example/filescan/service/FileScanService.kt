package com.example.filescan.service

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import java.io.File
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filescan.receiver.FileScanReceiver.Companion.ACTION_SEND_COMPLETION
import com.example.filescan.receiver.FileScanReceiver.Companion.ACTION_SEND_FILE_NAME
import com.example.filescan.receiver.FileScanReceiver.Companion.EXTRA_FILE_NAME
import com.example.filescan.receiver.FileScanReceiver.Companion.EXTRA_IS_COMPLETED
import android.app.NotificationManager
import android.content.Context
import com.example.filescan.R

class FileScanService : Service() {

    companion object {
        private const val NOTIFICATION_ID_SCANNING_FILES = 100
        private const val NOTIFICATION_ID_SCAN_COMPLETE = 101
    }

    private val job = Job()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO + job).launch {
            createScanningFilesNotification()
            scanFiles(Environment.getExternalStorageDirectory())
            sendCompletion()
            clearNotification()
            createScanCompleteNotification()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createScanningFilesNotification() {
        val builder = NotificationCompat
            .Builder(this, application.packageName)
            .setSmallIcon(R.drawable.database)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.scanning_files))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID_SCANNING_FILES, builder.build())
    }

    private fun createScanCompleteNotification() {
        val builder = NotificationCompat
            .Builder(this, application.packageName)
            .setSmallIcon(R.drawable.database)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.scan_completed))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID_SCAN_COMPLETE, builder.build())
    }

    private fun clearNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID_SCANNING_FILES)
    }

    private suspend fun scanFiles(file: File) {
        processFile(file)

        if (file.isDirectory) {
            for (innerFile in file.listFiles()) {
                delay(16)
                scanFiles((innerFile))
            }
        }
    }

    private fun processFile(file: File) {
        sendFileName(file.name)
    }

    private fun sendFileName(fileName: String) {
        val intent = Intent(ACTION_SEND_FILE_NAME)
        intent.putExtra(EXTRA_FILE_NAME, fileName)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun sendCompletion() {
        val intent = Intent(ACTION_SEND_COMPLETION)
        intent.putExtra(EXTRA_IS_COMPLETED, true)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
