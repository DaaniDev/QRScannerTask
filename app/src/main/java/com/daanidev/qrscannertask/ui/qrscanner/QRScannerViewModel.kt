package com.daanidev.qrscannertask.ui.qrscanner

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRScannerViewModel @Inject constructor() : ViewModel() {

    private val _qrCode: MutableStateFlow<String> = MutableStateFlow("")
    val qrCode: StateFlow<String> = _qrCode

    fun onQrCodeDetected(result: String) {
        Log.w("QR Scanner Task", result)
        viewModelScope.launch(Dispatchers.IO) {
            _qrCode.update { result }
        }
    }
}
