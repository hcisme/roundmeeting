package com.chc.roundmeeting.ui.page.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.ui.page.home.contact.ContactPage
import com.chc.roundmeeting.ui.page.home.my.MyPage
import com.chc.roundmeeting.ui.page.home.meeting.MeetingPage
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val homeVM = viewModel<HomeViewModel>()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { 3 }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
            ) {
                BottomBar(currentPage = homeVM.currentPage) {
                    homeVM.onChangeCurrentPage(it)
                    coroutineScope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
            }
        }
    ) { _ ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalAlignment = Alignment.Top
            ) {
                when (it) {
                    0 -> MeetingPage()

                    1 -> ContactPage()

                    2 -> MyPage()
                }
            }
        }
    }

    BackHandler {
        (context as Activity).moveTaskToBack(true)
    }
}
