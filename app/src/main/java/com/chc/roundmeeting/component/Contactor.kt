package com.chc.roundmeeting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun Contactor(modifier: Modifier = Modifier, coverUrl: String, name: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable(onClick = onClick)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            FadeImage(
                firstChar = name.first(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.width(12.dp))

        Text(
            name,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun Contactor(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    name: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable(onClick = onClick)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = contentColorFor(MaterialTheme.colorScheme.surface).copy(alpha = 0.6F)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        }

        Spacer(Modifier.width(12.dp))

        Text(
            name,
            style = MaterialTheme.typography.titleSmall
        )
    }
}
