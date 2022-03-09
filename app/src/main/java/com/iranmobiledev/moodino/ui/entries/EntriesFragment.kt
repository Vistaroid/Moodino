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
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
import com.iranmobiledev.moodino.ui.entries.adapter.EntryContainerAdapter
import com.iranmobiledev.moodino.utlis.implementSpringAnimationTrait
import org.greenrobot.eventbus.EventBus

class EntriesFragment : BaseFragment() {

    private lateinit var binding : FragmentEntriesBinding
    private lateinit var entriesContainerRv : RecyclerView

    override fun onStop() {
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

        binding.addEntryCardView.setOnClickListener{

        }

        makeSpringAnimation(binding.addEntryCardView)

        return binding.root
    }

    private fun makeSpringAnimation(view : View){
        view.implementSpringAnimationTrait()
    }

    private fun entriesContainerRvImpl(){
        //TODO should receive from database
        val entriesList = ArrayList<List<Entry>>()
        entriesContainerRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        entriesContainerRv.adapter = EntryContainerAdapter(requireContext(), entriesList)
    }

    private fun initViews(){
        entriesContainerRv = binding.entriesContainerRv
    }
}