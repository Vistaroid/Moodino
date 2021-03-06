package com.iranmobiledev.moodino.ui.entry

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.FragmentDayEntriesBinding
import com.iranmobiledev.moodino.callback.EntryEventLister
import com.iranmobiledev.moodino.ui.entry.adapter.EntryContainerAdapter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import saman.zamani.persiandate.PersianDate

class DayEntriesFragment : BaseFragment(), KoinComponent, EntryEventLister {

    companion object {
        fun newInstance() = DayEntriesFragment()
    }

    private lateinit var binding: FragmentDayEntriesBinding
    private val entryAdapter: EntryContainerAdapter by inject()
    private lateinit var viewModel: DayEntriesViewModel
    private var day : Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentDayEntriesBinding.inflate(inflater, container, false)
        day = DayEntriesFragmentArgs.fromBundle(requireArguments()).day
        setUpUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entryAdapter.getEmptyStateLiveData().observe(viewLifecycleOwner){
            when(it){
                true -> binding.emptyStateDayEntires.visibility = View.VISIBLE
                false -> binding.emptyStateDayEntires.visibility = View.GONE
            }
        }

        binding.addBtn.setOnClickListener {
            val date= PersianDate()
            val direction= DayEntriesFragmentDirections.actionDayEntriesFragmentToAddEntryFragment(
                EntryDate(1400,1,1), EntryTime(10.toString(),10.toString())
            )
            findNavController().navigate(direction)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DayEntriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setUpUi(){
        entryAdapter.bindSpecificDay(day)
        binding.recyclerView.apply {
            layoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL, false )
            adapter= entryAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        entryAdapter.setData(entryAdapter.copyData)
    }

    override fun update(entry: Entry) {
        val action = DayEntriesFragmentDirections.actionDayEntriesFragmentToEntryDetailFragment (edit = true, entry = entry)
        findNavController().navigate(action)
    }
    override fun delete(entry: Entry): Boolean {
      //  lifecycleScope.launchWhenResumed { showDeleteDialog(entry) }
        return true
    }

}