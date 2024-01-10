package com.daanidev.qrscannertask.ui.qrscanner

import android.Manifest
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import com.daanidev.qrscannertask.CommonUtils
import com.daanidev.qrscannertask.ui.qrscanner.component.BarcodeCamera
import com.daanidev.qrscannertask.ui.qrscanner.component.FeatureThatRequiresCameraPermission
import com.daanidev.qrscannertask.ui.qrscanner.component.NeedCameraPermissionScreen
import com.daanidev.qrscannertask.ui.theme.Black20Alpha
import com.daanidev.qrscannertask.ui.theme.Padding2
import com.daanidev.qrscannertask.ui.theme.Padding24
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRScanner(viewModel: QRScannerViewModel) {

    val context = LocalContext.current
    val qrCode = viewModel.qrCode.collectAsState()
    val cameraPermission = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    val camera = remember {
        BarcodeCamera()
    }

    val previousBarCodeValue = remember {
        mutableStateOf("")
    }

    FeatureThatRequiresCameraPermission(
        cameraPermissionState = cameraPermission,
        deniedContent = { status ->
            NeedCameraPermissionScreen(
                requestPermission = cameraPermission::launchPermissionRequest,
                shouldShowRationale = status.shouldShowRationale
            )
        },
        grantedContent = {
            CameraPreviewContent(
                camera = camera
            ) {
                viewModel.onQrCodeDetected(it)
            }
        }
    )

    qrCode.value.let {
        if (it.isNotEmpty() && it != previousBarCodeValue.value) {
            CommonUtils.showToastMessage(context, qrCode.value)
            previousBarCodeValue.value = it
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@Composable
fun CameraPreviewContent(
    camera: BarcodeCamera,
    getQRCodeCallback: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .drawWithContent {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val width = canvasWidth * CommonUtils.CANVAS_WIDTH_PERCENTAGE
                    val height =
                        width * CommonUtils.CANVAS_MULTIPLY_BY_2 / CommonUtils.CANVAS_MULTIPLY_BY_2.toFloat()

                    drawContent()
                    drawRect(color = Black20Alpha)

                    // Draws the rectangle in the middle
                    drawRoundRect(
                        topLeft = Offset(
                            (canvasWidth - width) / CommonUtils.CANVAS_MULTIPLY_BY_2,
                            canvasHeight * CommonUtils.CANVAS_HEIGHT_PERCENTAGE
                        ),
                        size = Size(width, height),
                        color = Color.Transparent,
                        cornerRadius = CornerRadius(Padding24.toPx()),
                        blendMode = BlendMode.SrcIn
                    )

                    // Draws the rectangle outline
                    drawRoundRect(
                        topLeft = Offset(
                            (canvasWidth - width) / CommonUtils.CANVAS_MULTIPLY_BY_2,
                            canvasHeight * CommonUtils.CANVAS_HEIGHT_PERCENTAGE
                        ),
                        color = Color.White,
                        size = Size(width, height),
                        cornerRadius = CornerRadius(Padding24.toPx()),
                        style = Stroke(width = Padding2.toPx()),
                        blendMode = BlendMode.Src
                    )
                }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                camera.CameraPreview(
                    onBarcodeScanned = { barcode ->
                        barcode?.displayValue?.let {
                            getQRCodeCallback(it)
                        }
                    }
                )
            }
        }
    }
}