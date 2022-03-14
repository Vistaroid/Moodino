package com.iranmobiledev.moodino.ui.entries

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.utlis.implementSpringAnimationTrait
import com.iranmobiledev.moodino.databinding.EntryDetailFragmentBinding
import com.iranmobiledev.moodino.ui.entries.adapter.ActivitiesAdapter
import com.iranmobiledev.moodino.ui.entries.adapter.ActivityContainerAdapter
import com.iranmobiledev.moodino.utlis.BottomNavVisibility

class EntryDetailFragment(private val entryViewModel: EntryViewModel) : BaseFragment(), ActivityContainerAdapter.AdapterItemCallback{

    private lateinit var binding : EntryDetailFragmentBinding
    private lateinit var activitiesContainerAdapter : ActivityContainerAdapter
    private lateinit var activitiesRv : RecyclerView
    private lateinit var save : ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EntryDetailFragmentBinding.inflate(inflater, container, false)
        initViews()
        activitiesRvImpl()
        makeSpringAnimation()
        save.setOnClickListener{

        }
        return binding.root
    }
    private fun makeSpringAnimation(){
        save.implementSpringAnimationTrait()
    }
    private fun activitiesRvImpl(){
        activitiesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        activitiesRv.adapter = activitiesContainerAdapter
    }
    private fun initViews(){
        //TODO get activities from database
        activitiesContainerAdapter = ActivityContainerAdapter(entryViewModel.getActivities(), this, requireContext())
        activitiesRv = binding.activitiesContainerRv
        save = binding.saveLayout
    }
    override fun onExpandViewClicked() {

        //val visibility = if(view.visibility == View.GONE) View.VISIBLE else View.GONE
        //view.visibility = visibility
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        BottomNavVisibility.currentFragment.value = this.id
    }
}