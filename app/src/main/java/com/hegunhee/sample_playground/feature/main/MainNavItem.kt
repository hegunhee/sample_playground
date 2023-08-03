package com.hegunhee.sample_playground.feature.main

import android.annotation.SuppressLint
import androidx.annotation.NavigationRes
import com.hegunhee.sample_playground.R

data class MainNavItem(val name : String,val actionId : Int)

internal fun getNavActionList() : List<MainNavItem> {
    return listOf<MainNavItem>(
        MainNavItem(name = "보안키패드",actionId = R.id.action_main_to_security)
    )
}
