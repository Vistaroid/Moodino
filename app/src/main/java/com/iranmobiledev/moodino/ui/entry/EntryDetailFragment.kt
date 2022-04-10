package com.iranmobiledev.moodino.ui.entry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.utlis.implementSpringAnimationTrait
import com.iranmobiledev.moodino.databinding.EntryDetailFragmentBinding
import com.iranmobiledev.moodino.ui.entry.adapter.ActivityContainerAdapter
import com.iranmobiledev.moodino.utlis.BottomNavVisibility
import com.iranmobiledev.moodino.utlis.ImageLoadingService
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EntryDetailFragment() : BaseFragment(), ActivityContainerAdapter.AdapterItemCallback, KoinComponent{

    private lateinit var binding : EntryDetailFragmentBinding
    private lateinit var activitiesContainerAdapter : ActivityContainerAdapter
    private lateinit var activitiesRv : RecyclerView
    private lateinit var save : ViewGroup
    private lateinit var saveFab : FloatingActionButton
    private lateinit var entryImage : ImageView
    private var imagePath : String? = null
    private lateinit var entryImageContainer : MaterialCardView
    private lateinit var noteEt : EditText
    private val entryDetailViewModel : EntryDetailViewModel by viewModel()
    private var entry = Entry(id = null)
    private val imageLoader : ImageLoadingService by inject()
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
        initViews()
        activitiesRvImpl()
        makeSpringAnimation()

        save.setOnClickListener(saveOnClick)
        saveFab.setOnClickListener(saveOnClick)

        binding.selectImageLayout.setOnClickListener{

            PickImageDialog.build(setupPhotoDialog()) {
                if(it.error == null){
                    imagePath = it.path
                    entryImageContainer.visibility = View.VISIBLE
                    entry.photoPath = it.path
                    imageLoader.load(requireContext(), entry.photoPath, entryImage)
                }
            }.show(parentFragmentManager)
        }
        return binding.root
    }
    private val saveOnClick = View.OnClickListener {

        noteEt.text?.let {
            entry.note = it.toString()
        }
        entryDetailViewModel.addEntry(entry)
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_entryDetailFragment_to_entriesFragment)
    }
    private fun setupPhotoDialog() : PickSetup{
        return PickSetup().apply {
            cameraIcon = R.drawable.ic_camera
            galleryIcon = R.drawable.ic_gallery
            buttonOrientation = LinearLayout.HORIZONTAL
            isSystemDialog = false
            width = 500
            height = 500
        }
    }
    private fun makeSpringAnimation(){
        save.implementSpringAnimationTrait()
    }
    private fun activitiesRvImpl(){
        activitiesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        activitiesRv.adapter = activitiesContainerAdapter
    }
    private fun initViews(){
        //TODO make list from list of activities
        activitiesContainerAdapter = ActivityContainerAdapter(entryDetailViewModel.getActivities(), this, requireContext())
        activitiesRv = binding.activitiesContainerRv
        save = binding.saveLayout
        saveFab = binding.saveEntryFab
        entryImage = binding.entryImage
        entryImageContainer = binding.entryImageContainer
        noteEt = binding.noteEt
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