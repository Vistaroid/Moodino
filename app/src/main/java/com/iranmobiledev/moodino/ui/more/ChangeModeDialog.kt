package com.iranmobiledev.moodino.ui.more

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.DialogChangeModeBinding
import com.iranmobiledev.moodino.utlis.*

class ChangeModeDialog(private var mode : Int, private val listener: ChangeModeDialogListener) : DialogFragment() {

    private lateinit var binding: DialogChangeModeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.let { mDialog ->
            mDialog.window?.let { window ->
                window.setBackgroundDrawableResource(R.drawable.dialog_background)
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogChangeModeBinding.inflate(layoutInflater)

        when(mode){
            LIGHT -> binding.rbDchangeModeLight.isChecked = true
            DARK -> binding.rbDchangeModeDark.isChecked = true
            SYSTEM_DEFAULT -> binding.rbDchangeModeSystem.isChecked = true
        }

        binding.rgDchangeMode.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_DchangeMode_light -> mode = LIGHT
                R.id.rb_DchangeMode_dark -> mode = DARK
                R.id.rb_DchangeMode_system -> mode = SYSTEM_DEFAULT
            }
        }

        binding.btnDchangeModeCancel.setOnClickListener {
            dismiss()
        }

        binding.btnDchangeModeSave.setOnClickListener {
            listener.save(mode)
            dismiss()
        }

        val alertDialog = AlertDialog.Builder(context)
        return alertDialog.setView(binding.root).create()
    }


    interface ChangeModeDialogListener{
        fun save(mode: Int)
    }
}