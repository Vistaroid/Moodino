package com.iranmobiledev.moodino.ui.entry


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.*
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
import com.iranmobiledev.moodino.listener.DialogEventListener
import com.iranmobiledev.moodino.listener.EmptyStateOnClickListener
import com.iranmobiledev.moodino.listener.EntryEventLister
import com.iranmobiledev.moodino.ui.calendar.calendarpager.monthName
import com.iranmobiledev.moodino.ui.calendar.toolbar.ChangeCurrentMonth
import com.iranmobiledev.moodino.ui.entry.adapter.EntryContainerAdapter
import com.iranmobiledev.moodino.utlis.*
import io.github.persiancalendar.calendar.AbstractDate
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class EntriesFragment : BaseFragment(), EntryEventLister, ChangeCurrentMonth,
    KoinComponent, EmptyStateOnClickListener {

    private lateinit var binding: FragmentEntriesBinding
    private lateinit var recyclerView: RecyclerView
    private val viewModel: EntryViewModel by viewModel()
    private val adapter: EntryContainerAdapter by inject()
    private var emptyStateEnum: EmptyStateEnum = EmptyStateEnum.INVISIBLE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntriesBinding.inflate(inflater, container, false)
        setupUi()
        setupObserver()
        setupClicks()
        return binding.root
    }

    private fun setupUi() {
        adapter.create(
            requireContext().applicationContext, this,
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
            if (it.isEmpty() && emptyStateEnum == EmptyStateEnum.INVISIBLE)
                binding.emptyStateView.visibility = View.VISIBLE
            else if (it.isNotEmpty() && emptyStateEnum == EmptyStateEnum.VISIBLE)
                binding.emptyStateView.visibility = View.GONE
            adapter.setData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.emptyStateView.emojis.setEmptyStateOnClickListener(this@EntriesFragment)
    }

    private fun setupClicks() {
        binding.addEntryCardView.setOnClickListener {}
    }

    private fun navigateToEntryDetail(entry: Entry) {
        val bundle = Bundle()
        bundle.putParcelable("entry", entry)
        findNavController().navigate(R.id.action_entriesFragment_to_entryDetailFragment, bundle)
    }

    private fun deleteEntry(entry: Entry) {
        lifecycleScope.launchWhenResumed { showDeleteDialog(entry) }
    }

    private fun showDeleteDialog(entry: Entry) {
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
        dialog.show(parentFragmentManager, null)
    }

    override fun changeCurrentMonth(date: AbstractDate) {
        Toast.makeText(context, date.year.toString() + date.monthName, Toast.LENGTH_SHORT).show()
    }


    override fun delete(entry: Entry): Boolean {
        deleteEntry(entry)
        return true
    }

    override fun onEmptyStateItemClicked(v: Int) {
        Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show()
    }
}