package com.iranmobiledev.moodino.utlis.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.DialogFragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.callback.PhotoSelectorCallback
import com.iranmobiledev.moodino.databinding.PhotoSelectorDialogBinding
import com.iranmobiledev.moodino.utlis.implementSpringAnimationTrait

class PhotoSelectorDialog(private val listener: PhotoSelectorCallback): DialogFragment() {
    private lateinit var binding : PhotoSelectorDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = PhotoSelectorDialogBinding.inflate(layoutInflater)
        val view = binding.root
        setupClicks()
        setupAnimation()
        val builder = AlertDialog.Builder(requireContext()).setView(view)
        return builder.create()
    }

    private fun setupClicks(){
        binding.camera.setOnClickListener {

        }

        binding.galley.setOnClickListener {

        }
    }

    private fun setupAnimation() {
        binding.camera.implementSpringAnimationTrait()
        binding.galley.implementSpringAnimationTrait()
    }
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult){

    }
}