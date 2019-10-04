package com.example.filescan.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.filescan.viewmodel.FileScanViewModel

class FileScanReceiver(
    private val viewModel: FileScanViewModel
) : BroadcastReceiver() {

    companion object {
        const val ACTION_POST_FILE_NAME = "ACTION_POST_FILE_NAME"
        const val EXTRA_FILE_NAME = "EXTRA_FILE_NAME"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_POST_FILE_NAME -> {
                val fileName = intent.getStringExtra(EXTRA_FILE_NAME)
                viewModel.setFileName(fileName)
            }
        }
    }
}
