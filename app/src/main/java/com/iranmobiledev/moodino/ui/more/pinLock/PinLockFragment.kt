package com.iranmobiledev.moodino.ui.more.pinLock

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentPinLockBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PinLockFragment : BaseFragment() {

    lateinit var binding : FragmentPinLockBinding
    val viewModel : PinLockViewModel by viewModel()
    var pin = ""
    var confirmPin = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinLockBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.pinLockToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        checkPINLock()


        binding.btnPinlock.setOnClickListener {
            getPin()
        }

        binding.rbPinlockOff.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.offLock()
                binding.btnPinlock.visibility = View.VISIBLE
                binding.tvPinlockTitle.text = getString(R.string.not_active_pin_lock)
                binding.ivPinlock.setImageDrawable(requireContext().getDrawable(R.drawable.ic_lock_notactive))
            }
        }

        binding.rbPinlockPINLock.setOnClickListener {
            if (binding.rbPinlockPINLock.isChecked && !viewModel.checkPIN()){
                getPin()
            }else{
                viewModel.setFingerPrintLock(false)
                viewModel.setPINLock(true)
            }
        }

        binding.rbPinlockFingetPrint.setOnClickListener {
            if (binding.rbPinlockFingetPrint.isChecked && !viewModel.checkPIN()){
                getPin()
            }else{
                viewModel.setFingerPrintLock(true)
                viewModel.setPINLock(true)
            }
        }
    }

    fun checkPINLock(){
        when {
            viewModel.checkFingerPrint() -> {
                binding.rbPinlockFingetPrint.isChecked = true
                binding.btnPinlock.visibility = View.GONE
                binding.tvPinlockTitle.text = getString(R.string.active_pin_lock)
                binding.ivPinlock.setImageDrawable(requireContext().getDrawable(R.drawable.ic_lock_active))
            }
            viewModel.checkPIN() -> {
                binding.rbPinlockPINLock.isChecked = true
                binding.btnPinlock.visibility = View.GONE
                binding.tvPinlockTitle.text = getString(R.string.active_pin_lock)
                binding.ivPinlock.setImageDrawable(requireContext().getDrawable(R.drawable.ic_lock_active))
            }
            else -> {
                binding.rbPinlockOff.isChecked = true
                binding.tvPinlockTitle.text = getString(R.string.not_active_pin_lock)
                binding.ivPinlock.setImageDrawable(requireContext().getDrawable(R.drawable.ic_lock_notactive))
            }
        }
    }

    fun getPin(){
        PinLockDialog(getString(R.string.enter_pin) , object :PinLockDialog.PinLockDialogListener{
            override fun pin(pin: String) {
                this@PinLockFragment.pin = pin
                getPinConfirm()
            }
        }).show(requireActivity().supportFragmentManager , null)
    }

    fun getPinConfirm(){
        PinLockDialog(getString(R.string.enter_pin) , object :PinLockDialog.PinLockDialogListener{
            override fun pin(pin: String) {
                this@PinLockFragment.confirmPin = pin

                if (confirmPin == this@PinLockFragment.pin){
                    binding.btnPinlock.visibility = View.GONE
                    binding.rbPinlockFingetPrint.isChecked = true

                    viewModel.setPinLock(pin)
                    binding.tvPinlockTitle.text = getString(R.string.active_pin_lock)
                    binding.ivPinlock.setImageDrawable(requireContext().getDrawable(R.drawable.ic_lock_active))
                }
                else{
                    binding.rbPinlockOff.isChecked = true
                    Toast.makeText(requireContext() ,getString(R.string.error_pin) , Toast.LENGTH_SHORT ).show()
                }

                confirmPin = ""
                this@PinLockFragment.pin = ""
            }
        }).show(requireActivity().supportFragmentManager , null)
    }

}