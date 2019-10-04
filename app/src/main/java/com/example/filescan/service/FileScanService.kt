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

    private val job = Job()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO + job).launch {
            createNotification()
            scanFiles(Environment.getExternalStorageDirectory())
            sendCompletion()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification() {
        val channelId = "wow"
        val notificationId = 100

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.database)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.scanning_files))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // notificationID allows you to update the notification later on.
        notificationManager.notify(notificationId, builder.build())
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
