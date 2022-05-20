package com.iranmobiledev.moodino.ui.more

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.DialogChangeLanguageBinding
import com.iranmobiledev.moodino.databinding.DialogViewBinding
import com.iranmobiledev.moodino.listener.DialogEventListener
import com.iranmobiledev.moodino.utlis.ENGLISH
import com.iranmobiledev.moodino.utlis.PERSIAN

class ChangeLanguageDialog(private var language : Int , private val listener: ChangeLanguageDialogListener) : DialogFragment() {

    private lateinit var binding: DialogChangeLanguageBinding

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
        binding = DialogChangeLanguageBinding.inflate(layoutInflater)

        when(language){
            PERSIAN -> binding.rbDchangeLanguagePersian.isChecked = true
            ENGLISH -> binding.rbDchangeLanguageEnglish.isChecked = true
        }

        binding.rgDchangeLanguage.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_DchangeLanguage_persian -> language = PERSIAN
                R.id.rb_DchangeLanguage_english -> language = ENGLISH
            }
        }

        binding.btnDchangeLanguageCancel.setOnClickListener {
            dismiss()
        }

        binding.btnDchangeLanguageSave.setOnClickListener {
            listener.save(language)
            dismiss()
        }

        val alertDialog = AlertDialog.Builder(context)
        return alertDialog.setView(binding.root).create()
    }


    interface ChangeLanguageDialogListener{
        fun save(language: Int)
    }
}