package com.iranmobiledev.moodino.ui.more

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentMoreBinding
import com.iranmobiledev.moodino.ui.MainActivity
import com.iranmobiledev.moodino.ui.more.pinLock.PinLockFragment
import com.iranmobiledev.moodino.utlis.ENGLISH
import com.iranmobiledev.moodino.utlis.PERSIAN
import org.greenrobot.eventbus.EventBus
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
    }

}