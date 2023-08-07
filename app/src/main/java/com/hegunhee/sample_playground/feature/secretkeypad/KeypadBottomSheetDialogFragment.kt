package com.hegunhee.sample_playground.feature.secretkeypad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.DialogBottomsheetKeypadBinding

class KeypadBottomSheetDialogFragment : BottomSheetDialogFragment(){

    private lateinit var viewDataBinding : DialogBottomsheetKeypadBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.dialog_bottomsheet_keypad,container,false)
        viewDataBinding = DialogBottomsheetKeypadBinding.bind(root)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}