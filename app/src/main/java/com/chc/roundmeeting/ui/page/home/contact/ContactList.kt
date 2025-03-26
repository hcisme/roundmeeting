package com.chc.roundmeeting.ui.page.home.contact

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chc.roundmeeting.component.Contactor
import com.chc.roundmeeting.utils.TestData

@Composable
fun ContactList(modifier: Modifier = Modifier) {
    val _modifier = remember { Modifier.padding(horizontal = 8.dp) }

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 4.dp)
                    .then(_modifier)
            ) {
                Text(
                    "我的联系人",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
            }
        }

        items(20) {
            Contactor(
                modifier = _modifier,
                coverUrl = TestData.TEST_AVATAR_URL,
                name = "池海成 $it"
            ) {}
        }

        item {
            Contactor(modifier = _modifier, imageVector = Icons.Rounded.Add, name = "添加联系人") {

            }
        }
    }
}
