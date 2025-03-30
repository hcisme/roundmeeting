package com.chc.roundmeeting.ui.page.meetingroom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.AudioViewModel
import com.chc.roundmeeting.MainActivity
import com.chc.roundmeeting.R
import com.chc.roundmeeting.VideoViewModel
import com.chc.roundmeeting.services.floatwindow.FloatingWindowService
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.NumConstants
import com.chc.roundmeeting.utils.checkOverlayPermission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val roomVM = viewModel<RoomViewModel>(context as MainActivity)
    val audioVM = viewModel<AudioViewModel>(context)
    val videoVM = viewModel<VideoViewModel>(context)
    val roomConfig = roomVM.roomConfig!!

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(NumConstants.DEFAULT_APPBAR_HEIGHT.dp)
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(if (roomConfig.isOpenLoudspeaker) R.drawable.loud_speaker else R.drawable.ear),
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            checkOverlayPermission(context) {
                                navController.popBackStack()
                                if (FloatingWindowService.intent == null) {
                                    FloatingWindowService.start(context)
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.float_window),
                            contentDescription = null
                        )
                    }
                }

            },
            title = {
                Box(modifier = Modifier.fillMaxHeight()) {
                    Text(
                        "会议中",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.align(alignment = Alignment.Center)
                    )
                }
            },
            expandedHeight = NumConstants.DEFAULT_APPBAR_HEIGHT.dp,
            actions = {
                TextButton(
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        audioVM.recorder.stop()
                        videoVM.stopCameraProvider()
                        navController.popBackStack()
                    }
                ) {
                    Text("结束", color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}