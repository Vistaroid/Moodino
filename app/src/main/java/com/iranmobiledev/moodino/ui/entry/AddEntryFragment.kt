package com.iranmobiledev.moodino.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.AddEntryFragmentBinding
import com.iranmobiledev.moodino.listener.EmojiClickListener
import com.iranmobiledev.moodino.ui.view.ActivityView
import com.iranmobiledev.moodino.utlis.*
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

var initialFromBackPress = false

class AddEntryFragment : BaseFragment(), EmojiClickListener{

    private lateinit var binding: AddEntryFragmentBinding
    private var persianDate: PersianDate = PersianDate()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEntryFragmentBinding.inflate(inflater, container, false)
        setupClicks()
        setupUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emojiViewAddEntry.setEmptyStateOnClickListener(this)
    }

    private fun setupUi() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPress)
        binding.continueButton.visibility = if (initialFromBackPress) View.VISIBLE else View.GONE
        binding.dateTv.text = getDate(pattern = "j F Y")
        binding.timeTv.text = getTime()
    }

    private fun navigateToEntryDetailFragment(entry: Entry) {
        val action = AddEntryFragmentDirections.actionAddEntryFragmentToEntryDetailFragment(entry = entry)
        findNavController().navigate(action)
    }

    private fun setupClicks() {
        binding.closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()
        initialFromBackPress = false
    }

    override fun onEmojiItemClicked(emojiValue: Int) {
        val entry = Entry()
        entry.date = EntryDate(persianDate.shYear, persianDate.shMonth, persianDate.shDay)
        entry.time = EntryTime(
            PersianDateFormat.format(persianDate, "H"),
            PersianDateFormat.format(persianDate, "i")
        )
        entry.emojiValue= emojiValue
        navigateToEntryDetailFragment(entry)
    }

    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_addEntryFragment_to_entriesFragment)
        }

    }
}