package com.chc.roundmeeting.ui.page.meetingroom

import androidx.compose.foundation.layout.Box
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
import com.chc.roundmeeting.MainActivity
import com.chc.roundmeeting.R
import com.chc.roundmeeting.services.MeetingService
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.NumConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier, onClickExitText: () -> Unit = {}) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val roomVM = viewModel<RoomViewModel>(context as MainActivity)
    val roomConfig = roomVM.roomConfig!!

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(NumConstants.DEFAULT_APPBAR_HEIGHT.dp)
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(if (roomConfig.isOpenLoudspeaker) R.drawable.loud_speaker else R.drawable.ear),
                        contentDescription = null
                    )
                }
            },
            title = {
                Box(modifier = Modifier.fillMaxHeight()) {
                    Text(
                        "腾讯会议",
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
                        onClickExitText()
                        navController.popBackStack()
                    }
                ) {
                    Text("结束", color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}