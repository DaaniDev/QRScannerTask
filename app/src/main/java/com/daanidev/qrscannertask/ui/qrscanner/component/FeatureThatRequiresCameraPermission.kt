@file:OptIn(ExperimentalAnimationApi::class)

package com.daanidev.qrscannertask.ui.qrscanner.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatureThatRequiresCameraPermission(
    cameraPermissionState: PermissionState,
    deniedContent: @Composable (PermissionStatus.Denied) -> Unit,
    grantedContent: @Composable () -> Unit
) {
    // Camera permission state
    val status = cameraPermissionState.status
    AnimatedContent(targetState = status, label = "") { permissionStatus ->
        when(permissionStatus) {
            is PermissionStatus.Granted -> grantedContent()
            is PermissionStatus.Denied -> deniedContent(permissionStatus)
        }
    }
}
