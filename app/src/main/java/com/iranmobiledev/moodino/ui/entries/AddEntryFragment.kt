package com.iranmobiledev.moodino.ui.entries

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.databinding.AddEntryFragmentBinding
import com.iranmobiledev.moodino.utlis.BottomNavVisibility

class AddEntryFragment() : BaseFragment() {

    private var entryDate : EntryDate? = null
    private lateinit var binding : AddEntryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEntryFragmentBinding.inflate(inflater, container, false)
        emojiItemClickHandler()
        entryDate = arguments?.getParcelable("entryDate")
        entryDate?.let {
            binding.dateTv.text = makeDateStandard(entryDate!!)
            binding.timeTv.text = makeTimeStandard(entryDate!!)
        }

        return binding.root
    }

    private fun makeTimeStandard(entryDate: EntryDate): String {
         if(entryDate.minute < 10)
             return "${entryDate.hours}:0${entryDate.minute}"
         return "${entryDate.hours}:${entryDate.minute}"
    }

    private fun navigateToEntryDetailFragment(bundle : Bundle){
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_addEntryFragment_to_entryDetailFragment, bundle)
    }

    private fun makeDateStandard(entryDate: EntryDate) : String{
        return "${entryDate.month} ${entryDate.day}"
    }
    private fun emojiItemClickHandler(){

        val entry = Entry()
        val bundle = Bundle()
        entry.date = getDate()
        bundle.putParcelable("entry", entry)

        binding.include.itemNothing.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_nothing
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemHappy.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_happy
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemSad.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_sad
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemVerySad.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_very_sad
            navigateToEntryDetailFragment(bundle)
        }
        binding.include.itemVeryHappy.setOnClickListener{
            entry.icon = R.drawable.ic_emoji_very_happy
            navigateToEntryDetailFragment(bundle)
        }
    }

    private fun getDate(): EntryDate? {
            return arguments?.getParcelable("entryDate")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        BottomNavVisibility.currentFragment.value = this.id
    }
}