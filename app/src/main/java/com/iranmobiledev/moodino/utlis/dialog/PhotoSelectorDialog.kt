package com.iranmobiledev.moodino.utlis.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.iranmobiledev.moodino.BuildConfig
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.callback.PhotoSelectorCallback
import com.iranmobiledev.moodino.databinding.PhotoSelectorDialogBinding
import com.iranmobiledev.moodino.utlis.implementSpringAnimationTrait
import java.io.File


class PhotoSelectorDialog(private val listener: PhotoSelectorCallback, private val mContext: Context) : DialogFragment() {
    private lateinit var binding: PhotoSelectorDialogBinding
    private var cameraUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.let { mDialog ->
            mDialog.window?.let { window ->
                window.setBackgroundDrawableResource(R.drawable.dialog_background)
            }

        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = PhotoSelectorDialogBinding.inflate(layoutInflater)
        val view = binding.root
        setupClicks()
        setupAnimation()
        val builder = AlertDialog.Builder(requireContext()).setView(view)
        return builder.create()
    }

    private fun setupAnimation() {
        binding.camera.implementSpringAnimationTrait()
        binding.galley.implementSpringAnimationTrait()
    }

    private fun setupClicks() {
        binding.camera.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                getTmpFileUri().let { uri ->
                    cameraUri = uri
                    cameraLauncher.launch(uri)
                }
            }
        }

        binding.galley.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
    }

        private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if(result){
                cameraUri?.let { listener.onSelectorPhotoReceived(it) }
            }
        }
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                it.path?.let { path ->
                    listener.onSelectorPhotoReceived(it)
                }
            }
        }
    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", requireActivity().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(mContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }
}