package com.iranmobiledev.moodino.ui.entry

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.databinding.AddEntryFragmentBinding
import com.iranmobiledev.moodino.listener.DatePickerDialogEventListener
import com.iranmobiledev.moodino.listener.EmojiClickListener
import com.iranmobiledev.moodino.utlis.*
import com.iranmobiledev.moodino.utlis.dialog.getPersianDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import saman.zamani.persiandate.PersianDate


class AddEntryFragment : BaseFragment(), EmojiClickListener, DatePickerDialogEventListener {

    private val entry = Entry()
    private lateinit var binding: AddEntryFragmentBinding
    private var persianDate: PersianDate = PersianDate()
    private val args: AddEntryFragmentArgs by navArgs()
    private var nightMode : Boolean = false

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
        args.date.let { entry.date = it }
        args.time.let { entry.time = it }
   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emojiViewAddEntry.setEmojiClickListener(this)


    }

    private fun setupUi() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPress)
        binding.continueButton.visibility = if (args.initialFromBackPress) View.VISIBLE else View.GONE
        println("date is ${args.date}")
        binding.dateTv.text = args.date.let { getDate(it) }
        binding.timeTv.text = getTime()
        binding.emojiViewAddEntry.setSelectedEmojiView(args.emojiValue)
    }

    private fun navigateToEntryDetailFragment(entry: Entry) {
        val action =
            AddEntryFragmentDirections.actionAddEntryFragmentToEntryDetailFragment(entry = entry)
        findNavController().navigate(action)
    }

    private fun setupClicks() {
        binding.date.implementSpringAnimationTrait()
        binding.closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.date.setOnClickListener {
            val persianDate = PersianDate()
            entry.date.let {
                persianDate.shYear = it.year
                persianDate.shMonth = it.month
                persianDate.shDay = it.day
            }
            getPersianDialog(requireContext(), this, persianDate , nightMode()).show()
        }
        binding.continueButton.setOnClickListener { navigateToEntryDetailFragment(entry) }
    }

    override fun onEmojiItemClicked(emojiValue: Int) {
        entry.emojiValue = emojiValue
        if(!args.initialFromBackPress)
        navigateToEntryDetailFragment(entry)
    }

    private val onBackPress = object : OnBackPressedCallback(true) {
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
        entry.date.let {
            persianDate.shDay = it.day
            persianDate.shMonth = it.month
            persianDate.shYear = it.year
        }
        entry.time.let {
            persianDate.hour = Integer.parseInt(it.hour)
            persianDate.minute = Integer.parseInt(it.minutes)
        }

        binding.timeTv.text = getTime(persianDate)
        binding.dateTv.text =
            getDate(pattern = "j F Y", date = persianDate)
    }
}