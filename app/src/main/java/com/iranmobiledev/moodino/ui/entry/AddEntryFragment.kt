package com.iranmobiledev.moodino.ui.entry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.AddEntryFragmentBinding
import com.iranmobiledev.moodino.utlis.*
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
var initialFromBackPress = false
class AddEntryFragment : BaseFragment() {

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

    private fun setupUi() {
        binding.continueButton.visibility = if(initialFromBackPress) View.VISIBLE else View.GONE
        binding.dateTv.text = getDate()
        binding.timeTv.text = getTime()
    }

    private fun navigateToEntryDetailFragment(bundle: Bundle) {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
            .navigate(R.id.action_addEntryFragment_to_entryDetailFragment, bundle)
    }

    private fun setupClicks() {
        val entry = Entry()
        val bundle = Bundle()
        entry.date = EntryDate(persianDate.shYear, persianDate.shMonth, persianDate.shDay)
        entry.time = EntryTime(
            PersianDateFormat.format(persianDate, "H"),
            PersianDateFormat.format(persianDate, "i")
        )
        bundle.putParcelable("entry", entry)

        binding.include.itemNothing.setOnClickListener {
            entry.icon = R.drawable.emoji_meh
            entry.title = MEH
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemHappy.setOnClickListener {
            entry.icon = R.drawable.emoji_good
            entry.title = GOOD
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemSad.setOnClickListener{
            entry.icon = R.drawable.emoji_bad
            entry.title = BAD
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemVerySad.setOnClickListener {
            entry.icon = R.drawable.emoji_awful
            entry.title = AWFUL
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemVeryHappy.setOnClickListener {
            entry.title = RAD
            entry.icon = R.drawable.emoji_rad
            navigateToEntryDetailFragment(bundle)
        }
        binding.closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun getDate(): String {
        return PersianDateFormat.format(
            persianDate,
            "Y F",
            PersianDateFormat.PersianDateNumberCharacter.FARSI
        )
    }

    private fun getTime(): String {
        return PersianDateFormat.format(persianDate, "H:i")
    }

    override fun onStop() {
        super.onStop()
        initialFromBackPress = false
    }
}