package com.chc.roundmeeting.ui.page.home.my

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    var logoutDialogVisible by mutableStateOf(false)
}