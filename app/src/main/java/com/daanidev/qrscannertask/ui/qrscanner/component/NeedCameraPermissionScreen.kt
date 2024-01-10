package com.daanidev.qrscannertask.ui.qrscanner.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.daanidev.qrscannertask.ui.theme.Padding16
import com.daanidev.qrscannertask.ui.theme.Padding8

@Composable
fun NeedCameraPermissionScreen(
    requestPermission: () -> Unit,
    shouldShowRationale: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val textToShow = if (shouldShowRationale) {
            /***
            If the user has denied the permission but the rationale can be shown,
            then gently explain why the app requires this permission
            */
            "The camera is important for this app. Please grant the permission."
        } else {
            /***
            If it's the first time the user lands on this feature, or the user
            doesn't want to be asked again for this permission, explain that the
            permission is required
            */
            "Camera permission required for this feature to be available. " +
                    "Please grant the permission"
        }
        Text(
            modifier = Modifier.padding(horizontal = Padding16),
            text = textToShow,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(Padding8))
        Button(onClick = requestPermission) {
            Text("Request Camera Permission")
        }
    }
}