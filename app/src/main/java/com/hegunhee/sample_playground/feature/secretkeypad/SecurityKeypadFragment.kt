package com.hegunhee.sample_playground.feature.secretkeypad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.FragmentSecurityKeypadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecurityKeypadFragment : Fragment() {

    private lateinit var viewDataBinding : FragmentSecurityKeypadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_security_keypad,container,false)
        viewDataBinding = FragmentSecurityKeypadBinding.bind(root)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.toolbar.setupWithNavController(findNavController())
    }
}