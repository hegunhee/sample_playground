package com.hegunhee.sample_playground.feature.secretkeypad.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.FragmentPasswordLogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordLogFragment : Fragment() {

    private lateinit var viewDataBinding : FragmentPasswordLogBinding
    private val viewModel : PasswordLogViewModel by viewModels()
    private lateinit var logAdapter : LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logAdapter = LogAdapter()
        val root = inflater.inflate(R.layout.fragment_password_log,container,false)
        viewDataBinding = FragmentPasswordLogBinding.bind(root).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@PasswordLogFragment.viewModel
            logRecyclerView.adapter = logAdapter
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getStringArray(argKey)?.let {
                val logList = it.toList().map { it.toString() }
                viewModel.fetchData(logList)
            }
        }
        viewDataBinding.toolbar.setupWithNavController(findNavController())
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.logList.collect {
                        logAdapter.submitList(it)
                    }
                }
            }
        }
    }

    companion object{
        const val argKey = "passwordLog"
    }
}