package com.example.filescan.view.activity

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filescan.R
import com.example.filescan.databinding.ActivityMainBinding
import com.example.filescan.receiver.FileScanReceiver
import com.example.filescan.receiver.FileScanReceiver.Companion.ACTION_POST_FILE_NAME
import com.example.filescan.service.ScanFilesService
import com.example.filescan.view.listener.FileScanListener
import com.example.filescan.viewmodel.FileScanViewModel

class MainActivity : AppCompatActivity(), FileScanListener {

    private lateinit var viewModel: FileScanViewModel
    private lateinit var receiver: FileScanReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )

        viewModel = ViewModelProvider(this).get(FileScanViewModel::class.java)
        receiver = FileScanReceiver(viewModel)

        binding.viewModel = viewModel
        binding.listener = this
        binding.lifecycleOwner = this
    }

    private fun stopScan() {
        val serviceIntent = Intent(this, ScanFilesService::class.java)
        stopService(serviceIntent)
    }

    override fun onScanClicked() {
        viewModel.onScanClicked(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stopScan()
    }

    override fun onStart() {
        LocalBroadcastManager
            .getInstance(this)
            .registerReceiver(receiver, IntentFilter(ACTION_POST_FILE_NAME))
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager
            .getInstance(this)
            .unregisterReceiver(receiver)
    }
}
