package com.iranmobiledev.moodino.ui.entry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.Navigation
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.EntryDetailFragmentBinding
import com.iranmobiledev.moodino.utlis.ImageLoadingService
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EntryDetailFragment : BaseFragment(),
    KoinComponent {

    private lateinit var binding: EntryDetailFragmentBinding
    private val entryDetailViewModel: EntryDetailViewModel by viewModel()
    private val imageLoader: ImageLoadingService by inject()
    private var entry = Entry()

    override fun onStart() {
        super.onStart()
        entry = arguments?.getParcelable("entry")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EntryDetailFragmentBinding.inflate(inflater, container, false)
        setupClicks()
        return binding.root
    }
    private fun setupClicks() {
        binding.saveLayout.setOnClickListener(saveOnClick)
        binding.saveEntryFab.setOnClickListener(saveOnClick)
        binding.selectImageLayout.setOnClickListener {
            createPhotoSelectorDialog()
        }
    }
    private fun navigateToEntryFragment(){
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
            .navigate(R.id.action_entryDetailFragment_to_entriesFragment, Bundle().apply {
                putParcelable("entry", entry)
            })
    }
    private fun setupPhotoDialog(): PickSetup {
        return PickSetup().apply {
            cameraIcon = R.drawable.ic_camera
            galleryIcon = R.drawable.ic_gallery
            buttonOrientation = LinearLayout.HORIZONTAL
            isSystemDialog = false
            width = 500
            height = 500
        }
    }

    private fun createPhotoSelectorDialog() {
        PickImageDialog.build(setupPhotoDialog()) {
            if (it.error == null) {
                binding.entryImageContainer.visibility = View.VISIBLE
                entry.photoPath = it.path
                imageLoader.load(requireContext(), entry.photoPath, binding.entryImage)
            }
        }.show(parentFragmentManager)
    }
    private val saveOnClick = View.OnClickListener {
        binding.noteEt.text?.let {
            entry.note = it.toString()
        }
        entryDetailViewModel.addEntry(entry)
        navigateToEntryFragment()
    }
}