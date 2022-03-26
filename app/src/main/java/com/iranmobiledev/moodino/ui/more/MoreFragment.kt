package com.iranmobiledev.moodino.ui.more

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentMoreBinding
import com.iranmobiledev.moodino.ui.more.activities.ActivitiesActivity
import com.iranmobiledev.moodino.ui.more.pinLock.PinLockActivity

class MoreFragment : BaseFragment() {
    private lateinit var binding : FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnMoreActivities.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext() , ActivitiesActivity::class.java))
        }

        binding.btnMorePinLock.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext() , PinLockActivity::class.java))
        }

        binding.btnMoreColorMode.setOnClickListener {
            changeMode()
        }

        binding.btnMoreLanguage.setOnClickListener {
            changeLanguage()
        }
    }

    fun changeMode(){

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_mode , null)
        val dialog = AlertDialog.Builder(requireContext()).setView(view).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    fun changeLanguage(){

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_language , null)
        val dialog = AlertDialog.Builder(requireContext()).setView(view).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }
}