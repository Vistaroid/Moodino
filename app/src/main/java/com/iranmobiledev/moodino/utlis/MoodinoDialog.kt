package com.iranmobiledev.moodino.utlis

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.DialogViewBinding
import com.iranmobiledev.moodino.listener.DialogEventListener

class MoodinoDialog(
    private val mainText: String,
    private val subText: String,
    @DrawableRes private val icon: Int,
    private val deleteText: String,
    private val cancelText: String,
) : DialogFragment() {

    private lateinit var leftButton : MaterialButton
    private lateinit var rightButton : MaterialButton
    private var dialogEventListener: DialogEventListener? = null
    private lateinit var binding: DialogViewBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViews()
        drawContent()
        itemClickListener()

        dialog?.let { dialog ->
            dialog.window?.let { window ->
                window.setBackgroundDrawableResource(R.drawable.dialog_bg)
            }
        }

        val alertDialog = AlertDialog.Builder(context)

        return alertDialog.setView(binding.root).create()
    }

    private fun itemClickListener() {
        leftButton.setOnClickListener {
            dialogEventListener?.clickedItem(R.id.leftButton)
        }
        rightButton.setOnClickListener {
            dialogEventListener?.clickedItem(R.id.rightButton)
        }

    }
    private fun drawContent(){
        if (mainText.isEmpty())
            binding.mainTextDialog.visibility = View.GONE
        else
            binding.mainTextDialog.text = mainText
        if (subText.isEmpty())
            binding.subTextDialog.visibility = View.GONE
        else
            binding.subTextDialog.text = subText
        if(deleteText.isNotEmpty())
            binding.rightButton.text = deleteText
        if(cancelText.isNotEmpty())
            binding.leftButton.text = cancelText
        binding.dialogIcon.setImageResource(icon)
    }
    private fun initViews(){
        binding = DialogViewBinding.inflate(layoutInflater)
        leftButton = binding.leftButton
        rightButton = binding.rightButton
    }
    fun setItemEventListener(dialogEventListener: DialogEventListener){
        this.dialogEventListener = dialogEventListener
    }
}