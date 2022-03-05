package com.iranmobiledev.moodino.ui.entries

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.common.implementSpringAnimationTrait
import com.iranmobiledev.moodino.databinding.EntryDetailFragmentBinding
import com.iranmobiledev.moodino.ui.entries.adapter.ActivitiesAdapter

class EntryDetailFragment : BaseFragment(), ActivitiesAdapter.AdapterItemCallback{

    private lateinit var binding : EntryDetailFragmentBinding
    private lateinit var activitiesAdapter : ActivitiesAdapter
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
        activitiesRv.adapter = activitiesAdapter
    }
    private fun initViews(){
        activitiesAdapter = ActivitiesAdapter(this)
        activitiesRv = binding.activitiesRv
        save = binding.saveLayout
    }

    override fun onExpandViewClicked(view: View) {
        //val visibility = if(view.visibility == View.GONE) View.VISIBLE else View.GONE
        //view.visibility = visibility
    }

}