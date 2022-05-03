package com.iranmobiledev.moodino.ui.entry


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
import com.iranmobiledev.moodino.listener.EmptyStateListener
import com.iranmobiledev.moodino.listener.EntryEventLister
import com.iranmobiledev.moodino.ui.calendar.calendarpager.monthName
import com.iranmobiledev.moodino.ui.calendar.toolbar.ChangeCurrentMonth
import com.iranmobiledev.moodino.ui.entry.adapter.EntryContainerAdapter
import io.github.persiancalendar.calendar.AbstractDate
import com.iranmobiledev.moodino.utlis.EmptyStateEnum
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent


class EntriesFragment : BaseFragment(), EntryEventLister, EmptyStateListener, ChangeCurrentMonth,
    KoinComponent {

    private lateinit var binding: FragmentEntriesBinding
    private lateinit var recyclerView: RecyclerView
    private val viewModel: EntryViewModel by viewModel()
    private lateinit var adapter: EntryContainerAdapter
    private var emptyStateEnum: EmptyStateEnum = EmptyStateEnum.INVISIBLE
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
        println("fragment is : $savedInstanceState")
        parentFragmentManager.saveFragmentInstanceState(this)
        return binding.root
    }

    private fun setupUi() {
        adapter = EntryContainerAdapter(
            requireContext(),
            this,
            this,
            mutableListOf()
        )
        binding.mainToolbar.initialize(this)
        recyclerView = binding.entriesContainerRv
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.getEntries().observe(viewLifecycleOwner) {
            adapter.setEntries(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupClicks() {
        binding.addEntryCardView.setOnClickListener {}

    }

    private fun deleteEntry(entry: Entry) {

        val dialog = makeDialog(
            mainText = R.string.dialogMainText,
            subText = R.string.dialogSubText,
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
        dialog.show(childFragmentManager, null)

    }

    override fun changeCurrentMonth(date: AbstractDate) {
        Toast.makeText(context, date.year.toString() + date.monthName, Toast.LENGTH_SHORT).show()
    }

    override fun emptyStateVisibility(mustShow: Boolean) {
        when (mustShow) {
            true -> {
                if (emptyStateEnum == EmptyStateEnum.INVISIBLE) {
                    showEmptyState(mustShow)
                    emptyStateEnum = EmptyStateEnum.VISIBLE
                }
            }
            false -> {
                if (emptyStateEnum == EmptyStateEnum.VISIBLE) {
                    showEmptyState(mustShow)
                    emptyStateEnum = EmptyStateEnum.INVISIBLE
                }
            }
        }
    }

    override fun delete(entry: Entry): Boolean {
        deleteEntry(entry)
        return true
    }

    override fun onStop() {
        emptyStateEnum = EmptyStateEnum.INVISIBLE
        super.onStop()
    }
}