package com.iranmobiledev.moodino.ui.more.pinLock

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.DialogPinLockBinding
import com.iranmobiledev.moodino.utlis.*

class PinLockDialog(private val title : String ,private val listener: PinLockDialogListener) : DialogFragment() {

    private lateinit var binding: DialogPinLockBinding
    var charNum : Int = 0
    var pin : String = ""

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
        binding = DialogPinLockBinding.inflate(layoutInflater)


        binding.tvPinLockTitle.text = title

        binding.btnPinlock0.setOnClickListener {
            pin += "0"
            chekcNum(true)
        }

        binding.btnPinlock1.setOnClickListener {
            pin += "1"
            chekcNum(true)
        }

        binding.btnPinlock2.setOnClickListener {
            pin += "2"
            chekcNum(true)
        }

        binding.btnPinlock3.setOnClickListener {
            pin += "3"
            chekcNum(true)
        }

        binding.btnPinlock4.setOnClickListener {
            pin += "4"
            chekcNum(true)
        }

        binding.btnPinlock5.setOnClickListener {
            pin += "5"
            chekcNum(true)
        }

        binding.btnPinlock6.setOnClickListener {
            pin += "6"
            chekcNum(true)
        }

        binding.btnPinlock7.setOnClickListener {
            pin += "7"
            chekcNum(true)
        }

        binding.btnPinlock8.setOnClickListener {
            pin += "8"
            chekcNum(true)
        }

        binding.btnPinlock9.setOnClickListener {
            pin += "9"
            chekcNum(true)
        }

        binding.btnPinlockDelete.setOnClickListener {
            if (pin.isNotEmpty()){
                pin = pin.substring(0 , pin.length - 1)
                chekcNum(false)
            }
        }

        val alertDialog = AlertDialog.Builder(context)
        return alertDialog.setView(binding.root).create()
    }


    fun chekcNum(add : Boolean){
        if (add) charNum += 1 else charNum -= 1
        when(charNum){
            1 -> {
                binding.rbPinLock1.isChecked = true
                binding.rbPinLock2.isChecked = false
                binding.rbPinLock3.isChecked = false
                binding.rbPinLock4.isChecked = false
            }
            2 -> {
                binding.rbPinLock1.isChecked = true
                binding.rbPinLock2.isChecked = true
                binding.rbPinLock3.isChecked = false
                binding.rbPinLock4.isChecked = false
            }
            3 -> {
                binding.rbPinLock1.isChecked = true
                binding.rbPinLock2.isChecked = true
                binding.rbPinLock3.isChecked = true
                binding.rbPinLock4.isChecked = false
            }
            4 -> {
                binding.rbPinLock1.isChecked = true
                binding.rbPinLock2.isChecked = true
                binding.rbPinLock3.isChecked = true
                binding.rbPinLock4.isChecked = true

                dismiss()
                listener.pin(pin)
            }
        }
    }


    interface PinLockDialogListener{
        fun pin(pin: String)
    }
}