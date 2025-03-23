package com.chc.roundmeeting.ui.page.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomBar(modifier: Modifier = Modifier, currentPage: Int, onClick: (key: Int) -> Unit = {}) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(NavigationBarDefaults.containerColor),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(
            modifier = Modifier.weight(1f),
            label = { Text(text = "会议", fontSize = 12.sp) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "会议",
                    tint = animateColorAsState(
                        if (currentPage == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        TweenSpec(10)
                    ).value
                )
            }
        ) {
            onClick(0)
        }

        BottomBarItem(
            modifier = Modifier.weight(1f),
            label = { Text(text = "通讯录", fontSize = 12.sp) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "通讯录",
                    tint = animateColorAsState(
                        if (currentPage == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        TweenSpec(10)
                    ).value
                )
            }
        ) {
            onClick(1)
        }

        BottomBarItem(
            modifier = Modifier.weight(1f),
            label = { Text(text = "我的", fontSize = 12.sp) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "我的",
                    tint = animateColorAsState(
                        if (currentPage == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        TweenSpec(10)
                    ).value
                )
            }
        ) {
            onClick(2)
        }
    }
}

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            icon()
            Spacer(modifier = Modifier.height(4.dp))
            label()
        }
    }
}
