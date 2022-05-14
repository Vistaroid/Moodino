package com.iranmobiledev.moodino.ui.entry

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentDayEntriesBinding
import com.iranmobiledev.moodino.ui.entry.adapter.EntryContainerAdapter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DayEntriesFragment : BaseFragment(), KoinComponent {

    companion object {
        fun newInstance() = DayEntriesFragment()
    }

    private lateinit var binding: FragmentDayEntriesBinding
    private val entryAdapter: EntryContainerAdapter by inject()
    private lateinit var viewModel: DayEntriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDayEntriesBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DayEntriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setUpUi(){
        binding.recyclerView.apply {
            layoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL, false )
            adapter= entryAdapter
        }
    }
}