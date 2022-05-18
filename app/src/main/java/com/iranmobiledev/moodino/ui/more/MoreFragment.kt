package com.iranmobiledev.moodino.ui.more

import android.content.Intent
import android.content.Intent.getIntent
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
import com.iranmobiledev.moodino.ui.more.pinLock.PinLockFragment
import com.iranmobiledev.moodino.utlis.ENGLISH
import com.iranmobiledev.moodino.utlis.MyContextWrapper
import com.iranmobiledev.moodino.utlis.PERSIAN
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
            changeMode()
        }

        binding.btnMoreLanguage.setOnClickListener {
            changeLanguage()
        }
    }

    fun changeMode(){

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_mode , null)
        val dialog = AlertDialog.Builder(requireContext()).setView(view).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    fun changeLanguage(){
        var lan = viewModel.getLanguage()

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_language , null)
        val dialog = AlertDialog.Builder(requireContext()).setView(view).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val btnSave = dialog.findViewById<MaterialButton>(R.id.btn_DchangeLanguage_save)
        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btn_DchangeLanguage_cancel)
        val rG = dialog.findViewById<RadioGroup>(R.id.rg_DchangeLanguage)
        val rbPersian = dialog.findViewById<RadioButton>(R.id.rb_DchangeLanguage_persian)
        val rbEnglish = dialog.findViewById<RadioButton>(R.id.rb_DchangeLanguage_english)

        when(lan){
            PERSIAN -> rbPersian?.isChecked = true
            ENGLISH -> rbEnglish?.isChecked = true
        }

        btnCancel?.setOnClickListener { dialog.dismiss() }

        rG?.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_DchangeLanguage_persian -> lan = PERSIAN
                R.id.rb_DchangeLanguage_english -> lan = ENGLISH
            }
        }

        btnSave?.setOnClickListener {
            viewModel.setLanguage(lan)
            requireActivity().finish();
            startActivity(requireActivity().intent);

            dialog.dismiss()
        }
    }
}