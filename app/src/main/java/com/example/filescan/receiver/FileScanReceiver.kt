package com.example.filescan.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.filescan.viewmodel.FileScanViewModel

class FileScanReceiver(
    private val viewModel: FileScanViewModel
) : BroadcastReceiver() {

    companion object {
        const val ACTION_SEND_FILE_NAME = "ACTION_SEND_FILE_NAME"
        const val ACTION_SEND_COMPLETION = "ACTION_SEND_COMPLETION"

        const val EXTRA_FILE_NAME = "EXTRA_FILE_NAME"
        const val EXTRA_IS_COMPLETED = "EXTRA_IS_COMPLETED"
        const val EXTRA_LARGEST_FILE_NAME = "EXTRA_LARGEST_FILE_NAME"
        const val EXTRA_LARGEST_FILE_SIZE = "EXTRA_LARGEST_FILE_SIZE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_SEND_FILE_NAME -> {
                val fileName = intent.getStringExtra(EXTRA_FILE_NAME)
                viewModel.setFileName(fileName)
            }
            ACTION_SEND_COMPLETION -> {
                val isCompleted = intent.getBooleanExtra(EXTRA_IS_COMPLETED, false)
                viewModel.setIsCompleted(isCompleted)

                val largestFileName = intent.getStringExtra(EXTRA_LARGEST_FILE_NAME)
                val largestFileSize = intent.getLongExtra(EXTRA_LARGEST_FILE_SIZE, 0)
                viewModel.setLargestFileInfo(largestFileName, largestFileSize)
            }
        }
    }
}
