package com.iranmobiledev.moodino.ui.entry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.AddEntryFragmentBinding
import com.iranmobiledev.moodino.utlis.*
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class AddEntryFragment() : BaseFragment() {

    private lateinit var binding : AddEntryFragmentBinding
    private var persianDate : PersianDate = PersianDate()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEntryFragmentBinding.inflate(inflater, container, false)
        emojiItemClickHandler()

        binding.dateTv.text = getDate()
        binding.timeTv.text = getTime()

        return binding.root
    }

    private fun navigateToEntryDetailFragment(bundle : Bundle){
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_addEntryFragment_to_entryDetailFragment, bundle)
    }
    private fun emojiItemClickHandler(){

        val entry = Entry(null)
        val bundle = Bundle()
        entry.date = EntryDate(persianDate.grgYear, persianDate.grgMonth, persianDate.grgDay)
        entry.time = EntryTime(persianDate.hour, persianDate.minute)
        bundle.putParcelable("entry", entry)


        binding.include.itemNothing.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_nothing
            entry.title = MEH
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemHappy.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_happy
            entry.title = GOOD
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemSad.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_sad
            entry.title = BAD
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemVerySad.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_very_sad
            entry.title = AWFUL
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemVeryHappy.setOnClickListener{
            entry.title = RAD
            entry.icon = R.drawable.ic_emoji_very_happy
            navigateToEntryDetailFragment(bundle)
        }
    }

    private fun getDate(): String {
        //TODO this format (english of farsi) should receive from const type
        return PersianDateFormat.format(persianDate, "Y F", PersianDateFormat.PersianDateNumberCharacter.FARSI)
    }

    private fun getTime() : String{
        return PersianDateFormat.format(persianDate, "H i")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        BottomNavVisibility.currentFragment.value = this.id
    }
}