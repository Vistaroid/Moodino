package com.iranmobiledev.moodino.ui.entries

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
import com.iranmobiledev.moodino.database.AppDatabase
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
import com.iranmobiledev.moodino.ui.entries.adapter.EntryContainerAdapter
import com.iranmobiledev.moodino.utlis.MoodinoSharedPreferences
import com.iranmobiledev.moodino.utlis.implementSpringAnimationTrait
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntriesFragment : BaseFragment() {

    private lateinit var binding : FragmentEntriesBinding
    private lateinit var entriesContainerRv : RecyclerView
    private val entryViewModel : EntryViewModel by viewModel()
    private lateinit var entriesContainerAdapter : EntryContainerAdapter
    private lateinit var navController: NavController

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(BottomNavState(true))
    }

    override fun onPause() {
        super.onPause()
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
        //TODO should receive from database
        val entriesList = entryViewModel.getEntries()
        entriesContainerAdapter = EntryContainerAdapter(requireContext(),
            entriesList as MutableList<EntryList>
        )

        entriesContainerRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        entriesContainerRv.adapter = entriesContainerAdapter
    }
    private fun initViews(){
        entriesContainerRv = binding.entriesContainerRv
        navController = NavHostFragment.findNavController(this)
        val appDatabase = AppDatabase.getAppDatabase(requireContext())
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun entrySentToMe(entry: Entry){
        if(entry.state == EntryState.ADD)
        entriesContainerAdapter.addEntry(entry)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun entryListSentToMe(entryList: EntryList){
        if(entryList.state == EntryListState.UPDATE)
                entryViewModel.updateEntry(entryList)

        if(entryList.state == EntryListState.ADD)
                entryViewModel.addEntry(entryList)
        entriesContainerRv.smoothScrollToPosition(0)
    }

}