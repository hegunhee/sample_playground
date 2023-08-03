package com.hegunhee.sample_playground.feature.main

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
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var viewDataBinding : FragmentMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var navItemAdapter : NavItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main,container,false)
        navItemAdapter = NavItemAdapter(viewModel)
        viewDataBinding = FragmentMainBinding.bind(root).apply {
            lifecycleOwner = viewLifecycleOwner
            recyclerView.adapter = navItemAdapter
        }
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navItemList.collect{ itemList ->
                        navItemAdapter.submitList(itemList)
                    }
                }
                launch {
                    viewModel.navigateActionId.collect { actionId ->
                        findNavController().navigate(actionId)
                    }
                }
            }
        }
    }
}