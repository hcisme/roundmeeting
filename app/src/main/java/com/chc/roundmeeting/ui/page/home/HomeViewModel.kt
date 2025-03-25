package com.chc.roundmeeting.ui.page.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val initialPageIndex = 0
    var currentPage by mutableIntStateOf(initialPageIndex)

    fun onChangeCurrentPage(current: Int) {
        currentPage = current
    }
}