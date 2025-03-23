package com.chc.roundmeeting.ui.page.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var currentPage by mutableIntStateOf(0)

    fun onChangeCurrentPage(current: Int) {
        currentPage = current
    }
}