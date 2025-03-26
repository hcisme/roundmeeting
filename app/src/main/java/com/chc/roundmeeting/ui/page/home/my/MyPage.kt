package com.chc.roundmeeting.ui.page.home.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chc.roundmeeting.component.FadeImage
import com.chc.roundmeeting.utils.NumConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TopAppBar(
                title = {},
                expandedHeight = NumConstants.DEFAULT_APPBAR_HEIGHT.dp,
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Rounded.Settings, contentDescription = null)
                    }
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
//                            .border(1.dp, Color.Red)
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(64.dp),
                            shape = CircleShape
                        ) {
                            FadeImage(
                                firstChar = 'U',
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "池海成",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Logout()
                }
            }
        }
    }
}
