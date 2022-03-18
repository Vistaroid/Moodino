package com.iranmobiledev.moodino.ui.entries

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.Navigation
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
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

    private fun navigateToEntryDetailFragment(bundle : Bundle){
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_addEntryFragment_to_entryDetailFragment, bundle)
    }


    private fun emojiItemClickHandler(){

        val entry = Entry()
        val bundle = Bundle()
        entry.time = arguments?.getString("time", "").toString()
        entry.date = arguments?.getString("date", "").toString()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        BottomNavVisibility.currentFragment.value = this.id
    }
}