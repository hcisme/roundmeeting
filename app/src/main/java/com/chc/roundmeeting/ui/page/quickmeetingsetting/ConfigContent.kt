package com.chc.roundmeeting.ui.page.quickmeetingsetting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.MainActivity
import com.chc.roundmeeting.R
import com.chc.roundmeeting.ui.page.joinmeetingsetting.LabelLayout
import com.chc.roundmeeting.utils.LocalSharedPreferences
import com.chc.roundmeeting.utils.StorageKeys
import com.chc.roundmeeting.utils.copyToClipboard
import com.chc.roundmeeting.utils.getQuickMeetingHardwareConfig
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ConfigContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = LocalSharedPreferences.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val quickMeetingSettingMV = viewModel<QuickMeetingSettingViewModel>(context as MainActivity)

    LaunchedEffect(Unit) {
        val storageConfig = sharedPreferences.getQuickMeetingHardwareConfig()
        if (storageConfig != null) {
            quickMeetingSettingMV.onChangeConfig(
                quickMeetingSettingMV.config.copy(
                    isOpenVideo = storageConfig.isOpenVideo,
                    isUsePersonMeetingNumber = storageConfig.isUsePersonMeetingNumber
                )
            )
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        item {
            LabelLayout(label = "开启视频", horizontalArrangement = Arrangement.SpaceBetween) {
                Switch(
                    checked = quickMeetingSettingMV.config.isOpenVideo,
                    onCheckedChange = {
                        quickMeetingSettingMV.onChangeConfig(
                            newConfig = quickMeetingSettingMV.config.copy(isOpenVideo = it),
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
            LabelLayout(
                label = "使用个人会议号",
                labelLength = 8,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(
                    checked = quickMeetingSettingMV.config.isUsePersonMeetingNumber,
                    onCheckedChange = {
                        quickMeetingSettingMV.onChangeConfig(
                            quickMeetingSettingMV.config.copy(isUsePersonMeetingNumber = it),
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
            LabelLayout(
                label = "个人会议号",
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = quickMeetingSettingMV.config.personMeetingNumber,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Icon(
                        painter = painterResource(R.drawable.copy),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            context.copyToClipboard(
                                StorageKeys.KEY_CLIPBOARD_MEETING_LABEL,
                                quickMeetingSettingMV.config.personMeetingNumber
                            )
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar("会议号已复制")
                            }
                        }
                    )
                }
            }
        }

    }

    SnackbarHost(hostState = snackBarHostState)
}
