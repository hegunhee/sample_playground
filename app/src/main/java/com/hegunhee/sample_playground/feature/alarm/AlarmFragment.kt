package com.hegunhee.sample_playground.feature.alarm

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.broadcast.AlarmReceiver
import com.hegunhee.sample_playground.broadcast.AlarmReceiver.Companion.SECOND_ALARM_PENDING_INTENT_FLAG
import com.hegunhee.sample_playground.broadcast.AlarmType
import com.hegunhee.sample_playground.databinding.FragmentAlarmBinding
import com.hegunhee.sample_playground.util.Time
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmFragment : Fragment() {

    private lateinit var viewDataBinding : FragmentAlarmBinding
    private val inputMethodManager : InputMethodManager by lazy {
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private val alarmManager : AlarmManager by lazy {
        requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_alarm,container,false)
        viewDataBinding = FragmentAlarmBinding.bind(root).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.run {
            setSecondAlarm()
        }
    }

    private fun FragmentAlarmBinding.setSecondAlarm() {
        secondAlarmSendButton.setOnClickListener {
            val second = secondAlarmEditText.text.toString()
            if(second.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "초를 입력해주세요", Toast.LENGTH_SHORT).show()
                secondAlarmEditText.requestFocus()
                inputMethodManager.showSoftInput(secondAlarmEditText,InputMethodManager.SHOW_IMPLICIT)
            }else {
                registerSecondAlarm(second.toLong())
            }
        }
        cancelSecondAlarmButton.setOnClickListener {
            cancelSecondAlarm()
        }
    }

    private fun registerSecondAlarm(second : Long) {
        val pendingIntent = AlarmReceiver.getAlarmPendingIntent(requireContext(),AlarmType.SECOND,SECOND_ALARM_PENDING_INTENT_FLAG)
        val timeMills = Time.toTimeMills(second)
        alarmManager.set(AlarmManager.RTC,timeMills,pendingIntent)
    }

    private fun cancelSecondAlarm() {
        val pendingIntent = AlarmReceiver.getAlarmPendingIntent(requireContext(),AlarmType.SECOND,SECOND_ALARM_PENDING_INTENT_FLAG)
        alarmManager.cancel(pendingIntent)
    }
}