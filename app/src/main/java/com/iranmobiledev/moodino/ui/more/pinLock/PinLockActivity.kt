package com.iranmobiledev.moodino.ui.more.pinLock

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.databinding.ActivityPinLockBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PinLockActivity : BaseActivity() {

    lateinit var binding : ActivityPinLockBinding
    val viewModel : PinLockViewModel by viewModel()
    var dialog : AlertDialog ?= null
    var pin = ""
    var confirmPin = ""
    var charNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinLockBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.pinLockToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        when {
            viewModel.checkFingerPrint() -> {
                binding.rbPinlockFingetPrint.isChecked = true
                binding.btnPinlock.visibility = View.GONE
                binding.tvPinlockTitle.text = getString(R.string.active_pin_lock)
                binding.ivPinlock.setImageDrawable(getDrawable(R.drawable.ic_lock_active))
            }
            viewModel.checkPIN() -> {
                binding.rbPinlockPINLock.isChecked = true
                binding.btnPinlock.visibility = View.GONE
                binding.tvPinlockTitle.text = getString(R.string.active_pin_lock)
                binding.ivPinlock.setImageDrawable(getDrawable(R.drawable.ic_lock_active))
            }
            else -> {
                binding.rbPinlockOff.isChecked = true
                binding.tvPinlockTitle.text = getString(R.string.not_active_pin_lock)
                binding.ivPinlock.setImageDrawable(getDrawable(R.drawable.ic_lock_notactive))
            }
        }

        binding.btnPinlock.setOnClickListener {
            activeLocke()
        }

        binding.rbPinlockOff.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.offLock()
                binding.btnPinlock.visibility = View.VISIBLE
            }
        }

        binding.rbPinlockPINLock.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                if (!viewModel.checkPIN() && !viewModel.checkFingerPrint()) activeLocke()
                else{
                    viewModel.setFingerPrintLock(false)
                    viewModel.setPINLock(true)
                }
        }

        binding.rbPinlockFingetPrint.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                if (!viewModel.checkPIN() && !viewModel.checkFingerPrint()) activeLocke()
                else{
                    viewModel.setFingerPrintLock(true)
                    viewModel.setPINLock(true)
                }
        }
    }

    fun activeLocke(){

        val view = LayoutInflater.from(this).inflate(R.layout.dialog_pin_lock , null)
        dialog = AlertDialog.Builder(this).setView(view).create()
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()

        dialog!!.findViewById<TextView>(R.id.tv_pinLock_title)?.text =
            if (confirmPin.isNotEmpty()) getString(R.string.confirm_pin)
            else getString(R.string.enter_pin)

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_0)?.setOnClickListener {
            charNum += 1
            pin += "0"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_1)?.setOnClickListener {
            charNum += 1
            pin += "1"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_2)?.setOnClickListener {
            charNum += 1
            pin += "2"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_3)?.setOnClickListener {
            charNum += 1
            pin += "3"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_4)?.setOnClickListener {
            charNum += 1
            pin += "4"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_5)?.setOnClickListener {
            charNum += 1
            pin += "5"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_6)?.setOnClickListener {
            charNum += 1
            pin += "6"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_7)?.setOnClickListener {
            charNum += 1
            pin += "7"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_8)?.setOnClickListener {
            charNum += 1
            pin += "8"
            charNum()
        }

        dialog!!.findViewById<TextView>(R.id.btn_pinlock_9)?.setOnClickListener {
            charNum += 1
            pin += "9"
            charNum()
        }

        dialog!!.findViewById<ImageView>(R.id.btn_pinlock_delete)?.setOnClickListener {
            charNum -= 1
            pin = pin.substring(0 , pin.length - 1)
            charNum()
        }
    }

    fun charNum(){
        val c1 = dialog!!.findViewById<RadioButton>(R.id.rb_pinLock_1)
        val c2 = dialog!!.findViewById<RadioButton>(R.id.rb_pinLock_2)
        val c3 = dialog!!.findViewById<RadioButton>(R.id.rb_pinLock_3)
        val c4 = dialog!!.findViewById<RadioButton>(R.id.rb_pinLock_4)

        when(charNum){
            1 -> {
                c1?.isChecked = true
                c2?.isChecked = false
                c3?.isChecked = false
                c4?.isChecked = false
            }
            2 -> {
                c1?.isChecked = true
                c2?.isChecked = true
                c3?.isChecked = false
                c4?.isChecked = false
            }
            3 -> {
                c1?.isChecked = true
                c2?.isChecked = true
                c3?.isChecked = true
                c4?.isChecked = false
            }
            4 -> {
                c1?.isChecked = true
                c2?.isChecked = true
                c3?.isChecked = true
                c4?.isChecked = true
                dialog?.cancel()

                if (confirmPin.isEmpty()){
                    confirmPin = pin
                    pin = ""
                    charNum = 0
                    activeLocke()
                }
                else{
                    if (confirmPin == pin){
                        confirmPin = ""
                        pin = ""
                        charNum = 0
                        binding.rbPinlockFingetPrint.isChecked = true
                        binding.btnPinlock.visibility = View.GONE
                        viewModel.setPinLock(pin)
                    }
                    else{
                        binding.rbPinlockOff.isChecked = true
                        Toast.makeText(this ,getString(R.string.error_pin) , Toast.LENGTH_SHORT ).show()
                    }
                }
            }
        }
    }
}