package com.example.filescan.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.filescan.R
import com.example.filescan.databinding.ActivityMainBinding
import com.example.filescan.view.listener.FileScanListener
import com.example.filescan.viewmodel.FileScanViewModel

class MainActivity : AppCompatActivity(), FileScanListener {

    private lateinit var viewModel: FileScanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )

        viewModel = ViewModelProvider(this).get(FileScanViewModel::class.java)

        binding.viewModel = viewModel
        binding.listener = this
        binding.lifecycleOwner = this
    }

    override fun onScanClicked() {
        viewModel.scanFiles(this)
    }
}
