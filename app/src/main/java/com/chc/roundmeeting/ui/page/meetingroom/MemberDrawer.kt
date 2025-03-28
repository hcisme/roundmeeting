package com.chc.roundmeeting.ui.page.meetingroom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.MainActivity
import com.chc.roundmeeting.component.FadeImage
import com.chc.roundmeeting.utils.NumConstants
import com.chc.roundmeeting.utils.TestData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberDrawer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val roomVM = viewModel<RoomViewModel>(context as MainActivity)

    AnimatedVisibility(
        visible = roomVM.memberDrawerVisible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
            initialOffsetY = { it }
        ) + fadeIn(),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing),
            targetOffsetY = { it }
        ) + fadeOut()
    ) {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.inverseOnSurface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxHeight()) {
                            Text(
                                "成员管理",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.align(alignment = Alignment.Center)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
                        .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                    expandedHeight = NumConstants.DEFAULT_APPBAR_HEIGHT.dp,
                    actions = {
                        IconButton(
                            onClick = {
                                roomVM.hideMemberDrawer()
                            }
                        ) {
                            Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                        }
                    }
                )

                MemberList()
            }
        }
    }
}

@Composable
fun MemberList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(20) {
            ListItem(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {},
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                leadingContent = {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape
                    ) {
                        FadeImage(
                            coverUrl = TestData.TEST_AVATAR_URL,
                            firstChar = 'C'
                        )
                    }
                },
                headlineContent = {
                    Text("池海成 $it")
                }
            )
        }
    }
}
