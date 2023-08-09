package com.hegunhee.sample_playground.feature.secretkeypad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.FragmentSecurityKeypadBinding
import com.hegunhee.sample_playground.feature.secretkeypad.dialog.KeypadBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecurityKeypadFragment : Fragment() {

    private lateinit var viewDataBinding : FragmentSecurityKeypadBinding
    private val viewModel : SecurityKeypadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_security_keypad,container,false)
        viewDataBinding = FragmentSecurityKeypadBinding.bind(root).apply {
            viewModel = this@SecurityKeypadFragment.viewModel
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.toolbar.setupWithNavController(findNavController())
        setResultListener()
        observeData()
    }

    private fun setResultListener() {
        setFragmentResultListener(KeypadBottomSheetDialogFragment.TAG_REGISTER) { key, bundle ->
            bundle.getString(KeypadBottomSheetDialogFragment.KEY_PASSWORD)?.let {
                viewModel.setPassword(it)
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.keypadNavigation.collect{ type ->
                        when(type) {
                            is KeypadType.Register ->{
                                KeypadBottomSheetDialogFragment.getInstance(type).show(parentFragmentManager, KeypadBottomSheetDialogFragment.TAG_REGISTER)
                            }
                            is KeypadType.Check -> {

                            }
                        }
                    }
                }
            }
        }
    }

}