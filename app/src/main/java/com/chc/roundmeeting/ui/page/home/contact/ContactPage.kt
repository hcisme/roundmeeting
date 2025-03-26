package com.chc.roundmeeting.ui.page.home.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chc.roundmeeting.R
import com.chc.roundmeeting.utils.NumConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPage(modifier: Modifier = Modifier) {
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
            CenterAlignedTopAppBar(
                title = {
                    Text("通讯录", style = MaterialTheme.typography.titleMedium)
                },
                expandedHeight = NumConstants.DEFAULT_APPBAR_HEIGHT.dp,
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add_friend),
                            contentDescription = null
                        )
                    }
                }
            )

            ContactList()
        }
    }
}
