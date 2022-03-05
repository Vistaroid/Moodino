package com.iranmobiledev.moodino.ui.entries

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.AddEntryFragmentBinding

class AddEntryFragment : BaseFragment() {
    private lateinit var binding : AddEntryFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEntryFragmentBinding.inflate(inflater, container, false)

        binding.date.setOnClickListener{
            val dialog = DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->

                }, 1400, 12, 11)
            dialog.show()
        }
        return binding.root
    }
}