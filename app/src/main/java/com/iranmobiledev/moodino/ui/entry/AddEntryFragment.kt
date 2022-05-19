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
import com.iranmobiledev.moodino.listener.DatePickerDialogEventListener
import com.iranmobiledev.moodino.listener.EmojiClickListener
import com.iranmobiledev.moodino.ui.view.ActivityView
import com.iranmobiledev.moodino.utlis.*
import com.iranmobiledev.moodino.utlis.dialog.getPersianDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

var initialFromBackPress = false

class AddEntryFragment : BaseFragment(), EmojiClickListener, DatePickerDialogEventListener{
    private val entry = Entry()
    private lateinit var binding: AddEntryFragmentBinding
    private var persianDate: PersianDate = PersianDate()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEntryFragmentBinding.inflate(inflater, container, false)
        setupUtil()
        setupClicks()
        setupUi()
        return binding.root
    }

    private fun setupUtil() {
        entry.date = EntryDate(persianDate.shYear, persianDate.shMonth, persianDate.shDay)
        entry.time = EntryTime(
            PersianDateFormat.format(persianDate, "H"),
            PersianDateFormat.format(persianDate, "i")
        )
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
        binding.date.implementSpringAnimationTrait()
        binding.closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.date.setOnClickListener{
            val persianDate = PersianDate()
            entry.date?.let {
                persianDate.shYear = it.year
                persianDate.shMonth = it.month
                persianDate.shDay = it.day
            }
            getPersianDialog(requireContext(),this,persianDate).show()
        }
    }

    override fun onStop() {
        super.onStop()
        initialFromBackPress = false
    }

    override fun onEmojiItemClicked(emojiValue: Int) {
        entry.emojiValue= emojiValue
        navigateToEntryDetailFragment(entry)
    }

    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_addEntryFragment_to_entriesFragment)
        }
    }

    override fun onDateSelected(persianPickerDate: PersianPickerDate) {
        entry.date = EntryDate(
            persianPickerDate.persianYear,
            persianPickerDate.persianMonth,
            persianPickerDate.persianDay
        )
        setupDate()
    }

    private fun setupDate() {
        val persianDate = PersianDate()
        entry.date?.let {
            persianDate.shDay = it.day
            persianDate.shMonth = it.month
            persianDate.shYear = it.year
        }
        entry.time?.let {
            persianDate.hour = Integer.parseInt(it.hour)
            persianDate.minute = Integer.parseInt(it.minutes)
        }
        binding.timeTv.text = getTime(persianDate)
        binding.dateTv.text =
            getDate(pattern = "j F Y", date = persianDate)
    }
}