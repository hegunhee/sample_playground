package com.hegunhee.sample_playground.feature.secretkeypad.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.FragmentPasswordLogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordLogFragment : Fragment() {

    private lateinit var viewDataBinding : FragmentPasswordLogBinding
    private val viewModel : PasswordLogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_password_log,container,false)
        viewDataBinding = FragmentPasswordLogBinding.bind(root).apply {
            viewModel = this@PasswordLogFragment.viewModel
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getStringArray(argKey)?.map { toString() }?.let{ logList ->
                viewModel.fetchData(logList)
            }
        }
        viewDataBinding.toolbar.setupWithNavController(findNavController())
    }

    companion object{
        const val argKey = "passwordLog"
    }
}