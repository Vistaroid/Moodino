package com.iranmobiledev.moodino.ui.more

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.navigation.fragment.findNavController
import com.iranmobiledev.moodino.NOTIFICATION_ID
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentMoreBinding
import com.iranmobiledev.moodino.ui.MainActivity
import org.koin.android.ext.android.inject


class MoreFragment : BaseFragment() {

    private lateinit var binding : FragmentMoreBinding
    val viewModel : MoreViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMorePinLock.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_pinLockFragment)
        }

        binding.btnMoreColorMode.setOnClickListener {
            ChangeModeDialog(viewModel.getMode() , object :ChangeModeDialog.ChangeModeDialogListener{
                override fun save(mode: Int) {
                    viewModel.setMode(mode)
                    MainActivity.SetThem.themeApp(mode)
                }
            }).show(requireActivity().supportFragmentManager , null)
        }

        binding.btnMoreLanguage.setOnClickListener {
            ChangeLanguageDialog(viewModel.getLanguage() , object :ChangeLanguageDialog.ChangeLanguageDialogListener{
                override fun save(language: Int) {
                    viewModel.setLanguage(language)
                    requireActivity().finish()
                    startActivity(requireActivity().intent)
                }
            }).show(requireActivity().supportFragmentManager , null)
        }

        binding.btnMoreReminder.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_reminderFragment)
        }

        binding.btnMoreAbout.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_aboutFragment)
        }
    }

}