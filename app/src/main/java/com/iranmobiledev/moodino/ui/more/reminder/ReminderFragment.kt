package com.iranmobiledev.moodino.ui.more.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentReminderBinding
import com.iranmobiledev.moodino.listener.DialogEventListener
import com.iranmobiledev.moodino.service.AlarmReciver
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReminderFragment : BaseFragment(){

    lateinit var binding : FragmentReminderBinding
    val viewModel : ReminderViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReminderBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolbarReminder.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        checkReminder()
        binding.switchReminderPopup.isChecked = viewModel.checkPopupReminder()

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext() , AlarmReciver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            1001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        binding.btnReminder.setOnClickListener {
            val a = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Select time")
                .setPositiveButtonText(getString(R.string.save))
                .setNegativeButtonText(getString(R.string.cancel))
                .build()

            a.addOnPositiveButtonClickListener {

                val time = Calendar.getInstance()
                time.set(Calendar.HOUR_OF_DAY, a.hour)
                time.set(Calendar.MINUTE, a.minute)
                time.set(Calendar.SECOND, 0)

                intent.putExtra("time" , time.timeInMillis)

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP , time.timeInMillis , AlarmManager.INTERVAL_DAY , pendingIntent)

                val h = if (a.hour < 10) "0" + a.hour.toString() else a.hour.toString()
                val m = if (a.minute < 10) "0" + a.minute.toString() else a.minute.toString()

                viewModel.setReminder("$h : $m")
                Toast.makeText(requireContext() ,getString(R.string.add_reminder_success) , Toast.LENGTH_SHORT).show()
                checkReminder()
            }

            a.show(requireActivity().supportFragmentManager , null)
        }

        binding.switchReminderPopup.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setPopupReminder(isChecked)
        }

        binding.ivReminderDelete.setOnClickListener {

            val dialog = makeDialog(
                mainText = R.string.delete_reminder,
                icon = R.drawable.ic_delete,
            )
            dialog.setItemEventListener(object : DialogEventListener {
                override fun clickedItem(itemId: Int) {
                    when (itemId) {
                        R.id.rightButton -> {
                            viewModel.setReminder("")
                            alarmManager.cancel(pendingIntent)
                            checkReminder()
                            dialog.dismiss()
                        }
                        R.id.leftButton -> {
                            dialog.dismiss()
                        }
                    }
                }
            })
            dialog.show(parentFragmentManager, null)
        }

    }

    fun checkReminder(){
        val time = viewModel.checkReminder()
        if (time.isEmpty()){
            binding.lReminderAdd.visibility = View.VISIBLE
            binding.lReminder.visibility = View.GONE
            binding.btnReminder.visibility = View.VISIBLE
        }
        else{
            binding.lReminder.visibility = View.VISIBLE
            binding.lReminderAdd.visibility = View.GONE
            binding.tvReminderTime.text = time
            binding.btnReminder.visibility = View.GONE
        }
    }
}