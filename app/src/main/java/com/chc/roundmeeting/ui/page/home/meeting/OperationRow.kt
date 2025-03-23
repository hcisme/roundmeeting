package com.chc.roundmeeting.ui.page.home.meeting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chc.roundmeeting.R

@Composable
fun OperationRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(116.dp)
    ) {
        TextIconButton(
            icon = Icons.Rounded.Add,
            text = "加入会议",
            modifier = Modifier.weight(1F),
            onClick = {}
        )

        TextIconButton(
            icon = painterResource(R.drawable.quick),
            text = "快速会议",
            modifier = Modifier.weight(1F),
            onClick = {}
        )

        TextIconButton(
            icon = Icons.Rounded.Check,
            text = "预定会议",
            modifier = Modifier.weight(1F),
            onClick = {}
        )

        TextIconButton(
            icon = painterResource(R.drawable.shared_screen),
            text = "共享会议",
            modifier = Modifier.weight(1F),
            onClick = {}
        )
    }
}

@Composable
private fun TextIconButton(
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 6.dp)
                .aspectRatio(1F)
                .clickable(onClick = onClick),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(0.6F)
                )
            }
        }

        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            letterSpacing = 1.8.sp
        )
    }
}

@Composable
private fun TextIconButton(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 6.dp)
                .aspectRatio(1F)
                .clickable(onClick = onClick),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(0.7F)
                )
            }
        }

        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            letterSpacing = 1.8.sp
        )
    }
}
