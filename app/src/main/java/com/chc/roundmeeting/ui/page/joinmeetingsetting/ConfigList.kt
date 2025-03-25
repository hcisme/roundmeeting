package com.chc.roundmeeting.ui.page.joinmeetingsetting

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.component.Dialog
import com.chc.roundmeeting.utils.LocalSharedPreferences
import com.chc.roundmeeting.utils.PermissionPreferenceManager
import com.chc.roundmeeting.utils.SpaceSeparatorTransformation
import com.chc.roundmeeting.utils.TEXT_DEMO
import com.chc.roundmeeting.utils.getJoinMeetingHardwareConfig
import com.chc.roundmeeting.utils.startSettingActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ConfigList(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = LocalSharedPreferences.current
    val audioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val joinMeetingSettingSV = viewModel<JoinMeetingSettingViewModel>()

    LaunchedEffect(Unit) {
        val config = sharedPreferences.getJoinMeetingHardwareConfig()
        if (config != null) {
            joinMeetingSettingSV.onChangeConfig(config)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        item {
            LabelTextField(label = "会议号") {
                TransparentTextField(
                    modifier = Modifier.weight(1F),
                    value = joinMeetingSettingSV.config.meetingNumber,
                    onValueChange = {
                        joinMeetingSettingSV.onChangeConfig(
                            joinMeetingSettingSV.config.copy(
                                meetingNumber = it
                            )
                        )
                    },
                    visualTransformation = SpaceSeparatorTransformation(4),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "下拉箭头",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        item {
            LabelTextField(label = "您的名称") {
                TransparentTextField(
                    modifier = Modifier.weight(1F),
                    value = joinMeetingSettingSV.config.name,
                    onValueChange = {
                        joinMeetingSettingSV.onChangeConfig(
                            joinMeetingSettingSV.config.copy(
                                name = it
                            )
                        )
                    }
                )
            }
        }

        item {
            LabelTextField(label = "开启麦克风", horizontalArrangement = Arrangement.SpaceBetween) {
                Switch(
                    checked = joinMeetingSettingSV.config.isOpenMicrophone,
                    onCheckedChange = {
                        if (audioPermissionState.status.isGranted) {
                            joinMeetingSettingSV.onChangeConfig(
                                newConfig = joinMeetingSettingSV.config.copy(
                                    isOpenMicrophone = it
                                ),
                                sharedPreferences = sharedPreferences
                            )
                        } else {
                            joinMeetingSettingSV.audioDialogVisible = true
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        uncheckedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.8F)
                    )
                )
            }
        }

        item {
            LabelTextField(label = "开启扬声器", horizontalArrangement = Arrangement.SpaceBetween) {
                Switch(
                    checked = joinMeetingSettingSV.config.isOpenLoudspeaker,
                    onCheckedChange = {
                        joinMeetingSettingSV.onChangeConfig(
                            newConfig = joinMeetingSettingSV.config.copy(
                                isOpenLoudspeaker = it
                            ),
                            sharedPreferences = sharedPreferences
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        uncheckedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.8F)
                    )
                )
            }
        }

        item {
            LabelTextField(label = "开启视频", horizontalArrangement = Arrangement.SpaceBetween) {
                Switch(
                    checked = joinMeetingSettingSV.config.isOpenVideo,
                    onCheckedChange = {
                        if (cameraPermissionState.status.isGranted) {
                            joinMeetingSettingSV.onChangeConfig(
                                newConfig = joinMeetingSettingSV.config.copy(
                                    isOpenVideo = it
                                ),
                                sharedPreferences = sharedPreferences
                            )
                        } else {
                            joinMeetingSettingSV.videoDialogVisible = true
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        uncheckedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.8F)
                    )
                )
            }
        }
    }

    Dialog(
        visible = joinMeetingSettingSV.audioDialogVisible,
        confirmButtonText = "允许",
        cancelButtonText = "取消",
        onConfirm = {
            PermissionPreferenceManager.managePermissionRequestFlow(
                context = context,
                permissionType = audioPermissionState.permission,
                onFirstRequest = { audioPermissionState.launchPermissionRequest() }
            ) {
                if (audioPermissionState.status.shouldShowRationale) {
                    audioPermissionState.launchPermissionRequest()
                } else {
                    context.startSettingActivity(tooltip = "前往设置 手动开启该应用麦克风权限")
                }
            }
            joinMeetingSettingSV.audioDialogVisible = false
        },
        onDismissRequest = {
            joinMeetingSettingSV.audioDialogVisible = false
        }
    ) {
        Text(text = "需要访问麦克风，才能让其他参会者听到您的声音")
    }

    Dialog(
        visible = joinMeetingSettingSV.videoDialogVisible,
        confirmButtonText = "允许",
        cancelButtonText = "取消",
        onConfirm = {
            PermissionPreferenceManager.managePermissionRequestFlow(
                context = context,
                permissionType = cameraPermissionState.permission,
                onFirstRequest = { cameraPermissionState.launchPermissionRequest() }
            ) {
                if (cameraPermissionState.status.shouldShowRationale) {
                    cameraPermissionState.launchPermissionRequest()
                } else {
                    context.startSettingActivity(tooltip = "前往设置 手动开启该应用摄像头权限")
                }
            }
            joinMeetingSettingSV.videoDialogVisible = false
        },
        onDismissRequest = {
            joinMeetingSettingSV.videoDialogVisible = false
        }
    ) {
        Text(text = "开启摄像头权限，让参会成员看到您的实时画面")
    }
}

@Composable
private fun LabelTextField(
    modifier: Modifier = Modifier,
    label: String,
    labelWidth: Dp? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable (RowScope.() -> Unit)
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val singleTextWidthPixel = textMeasurer.measure(
        text = TEXT_DEMO,
        style = LocalTextStyle.current
    ).size.width
    val singleTextWidthDp = with(density) { singleTextWidthPixel.toDp() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        Box(
            modifier = Modifier
                .width(labelWidth ?: (singleTextWidthDp * 6))
                .padding(end = 8.dp)
        ) {
            Text(text = label, maxLines = 1)
        }

        content()
    }
}

@Composable
private fun TransparentTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (keyboardOptions.keyboardType == KeyboardType.Number) {
                onValueChange(newValue.replace(" ", ""))
            } else {
                onValueChange(newValue)
            }
        },
        modifier = modifier.background(Color.Transparent),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onBackground
        )
    )
}
