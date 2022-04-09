package com.iranmobiledev.moodino.ui.entries

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.*
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
import com.iranmobiledev.moodino.ui.entries.adapter.EntryContainerAdapter
import com.iranmobiledev.moodino.utlis.BottomNavVisibility
import com.iranmobiledev.moodino.utlis.EntryEventListener
import com.iranmobiledev.moodino.utlis.MoodinoSharedPreferences
import com.iranmobiledev.moodino.utlis.implementSpringAnimationTrait
import org.greenrobot.eventbus.EventBus
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntriesFragment : BaseFragment(), EntryEventListener{

    private lateinit var binding : FragmentEntriesBinding
    private lateinit var entriesContainerRv : RecyclerView
    private val entryViewModel : EntryViewModel by viewModel()
    private lateinit var entriesContainerAdapter : EntryContainerAdapter
    private lateinit var navController: NavController


    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(BottomNavState(true))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntriesBinding.inflate(inflater, container, false)
        initViews()
        entriesContainerRvImpl()

        binding.addEntryCardView.setOnClickListener{}
        makeSpringAnimation(binding.addEntryCardView)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MoodinoSharedPreferences.create(requireContext()).edit().putBoolean("first_enter", true).apply()
    }
    private fun makeSpringAnimation(view : View){
        view.implementSpringAnimationTrait()
    }
    private fun entriesContainerRvImpl(){
        val entriesList = entryViewModel.getEntries()
        entriesContainerAdapter = EntryContainerAdapter(requireContext(),
            entriesList as MutableList<MutableList<Entry>>,
            this
        )

        entriesContainerRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        entriesContainerRv.adapter = entriesContainerAdapter
    }
    private fun initViews(){
        entriesContainerRv = binding.entriesContainerRv
        entriesContainerRv.itemAnimator = null
        navController = NavHostFragment.findNavController(this)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        BottomNavVisibility.currentFragment.value = this.id
    }

    override fun deleteEntry(entry: Entry): Boolean {
        entryViewModel.deleteEntry(entry)
        entriesContainerAdapter.removeItem(entry)
        return true
    }

    override fun updateEntry(entry: Entry) {
        super.updateEntry(entry)
    }
}