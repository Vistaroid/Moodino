package com.iranmobiledev.moodino.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.EmptyStateFragmentBinding

class EmptyStateFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = EmptyStateFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }
}