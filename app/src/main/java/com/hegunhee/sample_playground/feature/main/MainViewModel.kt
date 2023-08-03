package com.hegunhee.sample_playground.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(), MainNavItemActionHandler{

    private val _navItemList : MutableStateFlow<List<MainNavItem>> = MutableStateFlow(getNavActionList())
    val navItemList : StateFlow<List<MainNavItem>>
        get() = _navItemList

    private val _navigateId : MutableSharedFlow<Int> = MutableSharedFlow()
    val navigateActionId : SharedFlow<Int>
        get() = _navigateId

    override fun onClickNavItem(actionId: Int) {
        viewModelScope.launch {
            _navigateId.emit(actionId)
        }
    }


}