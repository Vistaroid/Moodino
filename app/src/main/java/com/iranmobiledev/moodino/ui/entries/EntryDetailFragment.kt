package com.iranmobiledev.moodino.ui.entries

import android.net.Uri
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
import com.iranmobiledev.moodino.data.EntryState
import com.iranmobiledev.moodino.utlis.implementSpringAnimationTrait
import com.iranmobiledev.moodino.databinding.EntryDetailFragmentBinding
import com.iranmobiledev.moodino.ui.entries.adapter.ActivityContainerAdapter
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import org.greenrobot.eventbus.EventBus
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntryDetailFragment() : BaseFragment(), ActivityContainerAdapter.AdapterItemCallback{

    private lateinit var binding : EntryDetailFragmentBinding
    private lateinit var activitiesContainerAdapter : ActivityContainerAdapter
    private lateinit var activitiesRv : RecyclerView
    private lateinit var save : ViewGroup
    private lateinit var saveFab : FloatingActionButton
    private lateinit var entryImage : ImageView
    private var imageUri : Uri? = null
    private lateinit var entryImageContainer : MaterialCardView
    private lateinit var noteEt : EditText
    private val entryViewModel : EntryViewModel by viewModel()
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
        initViews()
        activitiesRvImpl()
        makeSpringAnimation()

        save.setOnClickListener(saveOnClick)
        saveFab.setOnClickListener(saveOnClick)

        binding.selectImageLayout.setOnClickListener{

            PickImageDialog.build(setupPhotoDialog()) {
                if(it.error == null){
                    imageUri = it.uri
                    entryImageContainer.visibility = View.VISIBLE
                    entryImage.setImageURI(it.uri)
                }
            }.show(parentFragmentManager)
        }
        return binding.root
    }
    private val saveOnClick = View.OnClickListener {
        entry.state = EntryState.ADD
        noteEt.text?.let {
            entry.note = it.toString()
        }
        imageUri?.let {
            entry.photo = imageUri.toString()
        }
        EventBus.getDefault().postSticky(entry)
        Navigation.findNavController(requireActivity(), R.id.fragmentContainer).navigate(R.id.action_entryDetailFragment_to_entriesFragment)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entryViewModel.activitiesLiveData().observe(viewLifecycleOwner){
            activitiesContainerAdapter.addActivitiesList(it)
        }
    }
    private fun setupPhotoDialog() : PickSetup{
        return PickSetup().apply {
            cameraIcon = R.drawable.ic_camera
            galleryIcon = R.drawable.ic_gallery
            buttonOrientation = LinearLayout.HORIZONTAL
            isSystemDialog = false
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
        activitiesContainerAdapter = ActivityContainerAdapter(entryViewModel.getActivities(), this, requireContext())
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
}