package com.iranmobiledev.moodino.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.AddEntryFragmentBinding
import com.iranmobiledev.moodino.listener.EmptyStateOnClickListener
import com.iranmobiledev.moodino.utlis.*
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

var initialFromBackPress = false

class AddEntryFragment : BaseFragment(), EmptyStateOnClickListener {

    private lateinit var binding: AddEntryFragmentBinding
    private var persianDate: PersianDate = PersianDateObj.persianDate
    private val entry = Entry()
    private var canNavigate = true

    override fun onStart() {
        super.onStart()
        canNavigate = arguments?.getBoolean(SHOULD_NAVIGATE, true)!!
        val emojiValue = arguments?.getInt(EMOJI_VALUE)
        emojiValue?.let { setEntryType(it) }
    }

    private fun setEntryType(emojiValue : Int) {
        when(emojiValue){
            1 -> setEntryAsAwful()
            2 -> setEntryAsBad()
            3 -> setEntryAsMeh()
            4 -> setEntryAsGood()
            5 -> setEntryAsRad()
        }
    }

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
        binding.dateTv.text = getDate()
        binding.timeTv.text = getTime()
    }

    private fun navigateToEntryDetailFragment(bundle: Bundle) {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
            .navigate(R.id.action_addEntryFragment_to_entryDetailFragment, bundle)
    }

    private fun setupClicks() {
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

    override fun onEmptyStateItemClicked(v: Int) {
        val bundle = Bundle()
        entry.date = EntryDate(persianDate.shYear, persianDate.shMonth, persianDate.shDay)
        entry.time = EntryTime(
            PersianDateFormat.format(persianDate, "H"),
            PersianDateFormat.format(persianDate, "i")
        )
        bundle.putParcelable("entry", entry)

        when (v) {
            binding.emojiViewAddEntry.radItem.id -> {
                setEntryAsRad()
                if(canNavigate)
                navigateToEntryDetailFragment(bundle)
            }
            binding.emojiViewAddEntry.goodItem.id -> {
                setEntryAsGood()
                if(canNavigate)
                navigateToEntryDetailFragment(bundle)
            }
            binding.emojiViewAddEntry.mehItem.id -> {
                setEntryAsMeh()
                if(canNavigate)
                navigateToEntryDetailFragment(bundle)
            }
            binding.emojiViewAddEntry.badItem.id -> {
                setEntryAsBad()
                if(canNavigate)
                navigateToEntryDetailFragment(bundle)
            }
            binding.emojiViewAddEntry.awfulItem.id -> {
                setEntryAsAwful()
                if(canNavigate)
                navigateToEntryDetailFragment(bundle)
            }
        }
    }

    private fun setEntryAsAwful() {
        entry.icon = R.drawable.emoji_awful
        entry.title = AWFUL
        entry.emojiValue = 1
    }

    private fun setEntryAsBad() {
        entry.icon = R.drawable.emoji_meh
        entry.title = MEH
        entry.emojiValue = 3
    }

    private fun setEntryAsMeh() {
        entry.icon = R.drawable.emoji_meh
        entry.title = MEH
        entry.emojiValue = 3
    }

    private fun setEntryAsGood() {
        entry.icon = R.drawable.emoji_good
        entry.title = GOOD
        entry.emojiValue = 4
    }

    private fun setEntryAsRad() {
        entry.title = RAD
        entry.icon = R.drawable.emoji_rad
        entry.emojiValue = 5
    }

    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_addEntryFragment_to_entriesFragment)
        }

    }
}