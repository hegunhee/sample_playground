package com.hegunhee.sample_playground.feature.secretkeypad.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.DialogBottomsheetKeypadBinding
import com.hegunhee.sample_playground.feature.secretkeypad.KeypadAdapter
import com.hegunhee.sample_playground.feature.secretkeypad.KeypadType
import kotlinx.coroutines.launch

class KeypadBottomSheetDialogFragment(val keypadType : KeypadType) : BottomSheetDialogFragment(){

    private lateinit var viewDataBinding : DialogBottomsheetKeypadBinding
    private lateinit var keypadAdapter : KeypadAdapter
    private val viewModel : KeypadViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        keypadAdapter = KeypadAdapter(viewModel)
        val root = inflater.inflate(R.layout.dialog_bottomsheet_keypad,container,false)
        viewDataBinding = DialogBottomsheetKeypadBinding.bind(root).apply {
            viewModel = this@KeypadBottomSheetDialogFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            keypadRecyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(),3)
                adapter = keypadAdapter
            }
        }
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.fetchData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.keypad.collect { keypad ->
                        keypadAdapter.submitList(keypad)
                    }
                }
            }
        }
    }

    companion object {
        const val TAG_REGISTER = "register"
        const val TAG_CHECK = "check"

        fun getInstance(type : KeypadType) : KeypadBottomSheetDialogFragment {
            return KeypadBottomSheetDialogFragment(keypadType = type)
        }
    }
}