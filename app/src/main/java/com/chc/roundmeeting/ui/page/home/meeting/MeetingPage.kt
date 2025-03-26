package com.chc.roundmeeting.ui.page.home.meeting

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
fun MeetingPage(modifier: Modifier = Modifier) {
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
                modifier = Modifier.fillMaxWidth(),
                expandedHeight = NumConstants.DEFAULT_APPBAR_HEIGHT.dp,
                title = {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(48.dp),
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
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(thickness = 0.4.dp, modifier = Modifier.padding(horizontal = 14.dp))

            OperationRow()

            HorizontalDivider(thickness = 0.4.dp, modifier = Modifier.padding(horizontal = 14.dp))

            MeetingList()
        }
    }
}
