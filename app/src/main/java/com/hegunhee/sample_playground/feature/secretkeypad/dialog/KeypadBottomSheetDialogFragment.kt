package com.hegunhee.sample_playground.feature.secretkeypad.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.DialogBottomsheetKeypadBinding
import com.hegunhee.sample_playground.feature.secretkeypad.KeypadAdapter
import com.hegunhee.sample_playground.feature.secretkeypad.KeypadType

class KeypadBottomSheetDialogFragment(val keypadType : KeypadType) : BottomSheetDialogFragment(){

    private lateinit var viewDataBinding : DialogBottomsheetKeypadBinding
    private lateinit var keypadAdapter : KeypadAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        keypadAdapter = KeypadAdapter()
        val root = inflater.inflate(R.layout.dialog_bottomsheet_keypad,container,false)
        viewDataBinding = DialogBottomsheetKeypadBinding.bind(root).apply {
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
    }

    companion object {
        const val TAG_REGISTER = "register"
        const val TAG_CHECK = "check"

        fun getInstance(type : KeypadType) : KeypadBottomSheetDialogFragment {
            return KeypadBottomSheetDialogFragment(keypadType = type)
        }
    }
}