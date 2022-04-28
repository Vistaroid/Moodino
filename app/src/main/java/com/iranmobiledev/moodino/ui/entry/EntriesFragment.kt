package com.iranmobiledev.moodino.ui.entry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.*
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
import com.iranmobiledev.moodino.listener.DialogEventListener
import com.iranmobiledev.moodino.listener.EntryEventLister
import com.iranmobiledev.moodino.ui.calendar.calendarpager.monthName
import com.iranmobiledev.moodino.ui.calendar.toolbar.ChangeCurrentMonth
import com.iranmobiledev.moodino.ui.entry.adapter.EntryContainerAdapter
import io.github.persiancalendar.calendar.AbstractDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class EntriesFragment : BaseFragment(), EntryEventLister, ChangeCurrentMonth, KoinComponent {

    private lateinit var binding: FragmentEntriesBinding
    private lateinit var recyclerView: RecyclerView
    private val viewModel: EntryViewModel by viewModel()
    private lateinit var adapter: EntryContainerAdapter

    override fun onStart() {
        super.onStart()
        val entry = arguments?.getParcelable<Entry>("entry")
        entry?.let {
            adapter.addEntry(it)
        }
        arguments?.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntriesBinding.inflate(inflater, container, false)
        setupUi()
        setupClicks()
        setupObserver()
        return binding.root
    }

    private fun setupUi() {
        binding.mainToolbar.initialize(this)
        recyclerView = binding.entriesContainerRv
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = EntryContainerAdapter(requireContext(), mutableListOf(), this)
        recyclerView.adapter = adapter
    }
    private fun setupObserver() {
        viewModel.getEntries().observe(viewLifecycleOwner){
            adapter.setEntries(it)
            adapter.notifyDataSetChanged()
        }
    }
    private fun setupClicks() {
        binding.addEntryCardView.setOnClickListener {}
    }

    override fun delete(entry: Entry): Boolean {
        val dialog = makeDialog(
            mainText = "Delete loving memory?",
            subText = "This is your life we are talking\nabout. Do you want to delete a\npart of it?",
            icon = R.drawable.ic_delete,
        )
        dialog.setItemEventListener(object : DialogEventListener {
            override fun clickedItem(itemId: Int) {
                when (itemId) {
                    R.id.rightButton -> {
                        viewModel.deleteEntry(entry)
                        adapter.removeItem(entry)
                        dialog.dismiss()
                    }
                    R.id.leftButton -> {
                        dialog.dismiss()
                    }
                }
            }
        })
        dialog.show(parentFragmentManager, null)
        return true
    }

    override fun changeCurrentMonth(date: AbstractDate) {
        Toast.makeText(context, date.year.toString() + date.monthName, Toast.LENGTH_SHORT).show()
    }
}