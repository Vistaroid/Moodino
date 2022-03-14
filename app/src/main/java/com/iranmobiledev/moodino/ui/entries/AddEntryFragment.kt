package com.iranmobiledev.moodino.ui.entries

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.AddEntryFragmentBinding
import com.iranmobiledev.moodino.utlis.BottomNavVisibility

class AddEntryFragment : BaseFragment() {
    private lateinit var binding : AddEntryFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEntryFragmentBinding.inflate(inflater, container, false)
        emojiItemClickHandler()

        binding.dateTv.text = arguments?.getString("date")
        binding.timeTv.text = arguments?.getString("time")

        return binding.root
    }

    private val emojiItemClickListener = View.OnClickListener {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_addEntryFragment_to_entryDetailFragment)
    }
    private fun emojiItemClickHandler(){
        binding.include.itemNothing.setOnClickListener(emojiItemClickListener)
        binding.include.itemHappy.setOnClickListener(emojiItemClickListener)
        binding.include.itemSad.setOnClickListener(emojiItemClickListener)
        binding.include.itemVerySad.setOnClickListener(emojiItemClickListener)
        binding.include.itemVeryHappy.setOnClickListener(emojiItemClickListener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        BottomNavVisibility.currentFragment.value = this.id
    }
}