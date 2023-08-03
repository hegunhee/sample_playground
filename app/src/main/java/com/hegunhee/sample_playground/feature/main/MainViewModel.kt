package com.hegunhee.sample_playground.feature.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(), MainNavItemActionHandler{
    
    override fun onClickNavItem(actionId: Int) {
        TODO("Not yet implemented")
    }


}