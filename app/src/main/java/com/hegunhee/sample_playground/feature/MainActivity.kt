package com.hegunhee.sample_playground.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hegunhee.sample_playground.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}