package com.iranmobiledev.moodino.ui.entries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
import com.iranmobiledev.moodino.ui.entries.adapter.EntryContainerAdapter

class EntriesFragment : BaseFragment() {

    private lateinit var binding : FragmentEntriesBinding
    private lateinit var entriesContainerRv : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntriesBinding.inflate(inflater, container, false)
        initViews()
        entriesContainerRvImpl()

        return binding.root
    }

    private fun entriesContainerRvImpl(){
        entriesContainerRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        entriesContainerRv.adapter = EntryContainerAdapter()
    }

    private fun initViews(){
        entriesContainerRv = binding.entriesContainerRv
    }
}