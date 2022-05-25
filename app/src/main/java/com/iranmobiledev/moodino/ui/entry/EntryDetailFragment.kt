package com.iranmobiledev.moodino.ui.entry

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.databinding.EntryDetailFragmentBinding
import com.iranmobiledev.moodino.listener.ActivityItemCallback
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import com.iranmobiledev.moodino.listener.DatePickerDialogEventListener
import com.iranmobiledev.moodino.listener.DialogEventListener
import com.iranmobiledev.moodino.listener.EmojiClickListener
import com.iranmobiledev.moodino.ui.entry.adapter.ParentActivitiesAdapter
import com.iranmobiledev.moodino.ui.view.CustomEmojiView
import com.iranmobiledev.moodino.utlis.*
import com.iranmobiledev.moodino.utlis.dialog.getPersianDialog
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import saman.zamani.persiandate.PersianDate

//TODO for edit mode should implement date and time set

class EntryDetailFragment : BaseFragment(), EmojiClickListener, ActivityItemCallback,
    DatePickerDialogEventListener,
    KoinComponent {

    private lateinit var binding: EntryDetailFragmentBinding
    private val entryDetailViewModel: EntryDetailViewModel by viewModel()
    private val imageLoader: ImageLoadingService by inject()
    private var entry = Entry()
    private var editMode = false
    private val sharedPref: SharedPreferences by inject()
    private var activities = mutableListOf<Activity>()
    private val args: EntryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EntryDetailFragmentBinding.inflate(inflater, container, false)
        entry = EntryDetailFragmentArgs.fromBundle(requireArguments()).entry
        activities = entry.activities
        setupUi(EmojiFactory.create(requireContext()))
        setupUtil()
        setupObserver()
        setupClicks()
        return binding.root
    }

    private fun setupObserver() {
        entryDetailViewModel.getActivities().observe(viewLifecycleOwner) {
            binding.parentActivityRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.parentActivityRv.adapter = ParentActivitiesAdapter(it, this, entry.activities)
        }
    }

    private fun setupUi(emojiFactory: EmojiInterface) {
        if (entry.note.isEmpty())
            binding.addPhotoTv.setText(R.string.tap_to_add_photo)
        else
            binding.addPhotoTv.setText(R.string.change_photo)

        editMode = EntryDetailFragmentArgs.fromBundle(requireArguments()).edit
        if (editMode)
            setupEditMode()
//        val icon = when (entry.emojiValue) {
//            1 -> emojiFactory.getEmoji(entry.emojiValue)
//            2 -> emojiFactory.getEmoji(entry.emojiValue)
//            3 -> emojiFactory.getEmoji(entry.emojiValue)
//            4 -> emojiFactory.getEmoji(entry.emojiValue)
//            5 -> emojiFactory.getEmoji(entry.emojiValue)
//            else -> null
//        }
        val language = sharedPref.getInt(LANGUAGE, PERSIAN)
        if (language == PERSIAN)
            binding.backIv.rotation = 180f
    }

    private fun setupEditMode() {
        setupDate()
        binding.emojiViewEntryDetail.setSelectedEmojiView(entry.emojiValue)
        binding.pageTitle.visibility = View.GONE
        binding.timeDate.visibility = View.VISIBLE
        binding.emojiViewEntryDetail.visibility = View.VISIBLE
        binding.noteEt.setText(entry.note)
        if (entry.photoPath.isNotEmpty())
            binding.entryImageContainer.visibility = View.VISIBLE
        imageLoader.load(requireContext(), entry.photoPath, binding.entryImage)
    }

    private fun setupDate() {
        val persianDate = PersianDate()
        entry.date.let {
            persianDate.shDay = it.day
            persianDate.shMonth = it.month
            persianDate.shYear = it.year
        }
        entry.time.let {
            persianDate.hour = Integer.parseInt(it.hour)
            persianDate.minute = Integer.parseInt(it.minutes)
        }
        binding.timeTv.text = getTime(persianDate)
        binding.dateTv.text =
            getDate(pattern = "j F Y", date = persianDate)
    }

    private fun setupUtil() {
        args.entry.date.let { entry.date = it }
        args.entry.time.let { entry.time = it }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed)
    }

    private fun setupClicks() {
        binding.emojiViewEntryDetail.setEmojiClickListener(this)
        binding.saveLayout.setOnClickListener(saveOnClick)
        binding.saveEntryFab.setOnClickListener(saveOnClick)
        binding.selectImageLayout.setOnClickListener {
            createPhotoSelectorDialog()
        }
        binding.date.implementSpringAnimationTrait()
        binding.date.setOnClickListener {
            val persianDate = PersianDate()
            entry.date.let {
                persianDate.shYear = it.year
                persianDate.shMonth = it.month
                persianDate.shDay = it.day
            }
            getPersianDialog(requireContext(), this, persianDate).show()
        }
        binding.backIv.setOnClickListener { requireActivity().onBackPressed() }
        binding.deleteImage.setOnClickListener {
            val dialog = makeDialog(R.string.delete_photo, icon = R.drawable.ic_delete)
            dialog.setItemEventListener(object : DialogEventListener {
                override fun clickedItem(itemId: Int) {
                    when (itemId) {
                        R.id.rightButton -> {
                            imageLoader.remove(requireContext(), binding.entryImage)
                            entry.photoPath = ""
                            binding.addPhotoTv.setText(R.string.tap_to_add_photo)
                            binding.entryImageContainer.visibility = View.GONE
                            dialog.dismiss()
                        }
                        R.id.leftButton -> {
                            dialog.dismiss()
                        }
                    }
                }
            })
            dialog.show(parentFragmentManager, null)
        }
    }

    private fun navigateToEntryFragment(newEntry: Entry) {
        val action = EntryDetailFragmentDirections.actionEntryDetailFragmentToEntriesFragment(newEntry)
        findNavController().navigate(action)
    }

    private fun setupPhotoDialog(): PickSetup {
        return PickSetup().apply {
            cameraIcon = R.drawable.ic_camera
            galleryIcon = R.drawable.ic_gallery
            buttonOrientation = LinearLayout.HORIZONTAL
            isSystemDialog = false
            title = getString(R.string.choose)
            cancelText = getString(R.string.cancel)
            galleryButtonText = getString(R.string.galleyTxt)
            cameraButtonText = getString(R.string.cameraTxt)
            width = 500
            height = 500
        }
    }

    private fun createPhotoSelectorDialog() {
        PickImageDialog.build(setupPhotoDialog()) {
            if (it.error == null) {
                binding.apply {
                    entryImageContainer.visibility = View.VISIBLE
                    addPhotoTv.setText(R.string.change_photo)
                    nestedScrollView.post{
                        nestedScrollView.fullScroll(View.FOCUS_DOWN)
                    }
                }
                entry.photoPath = it.path
                imageLoader.load(requireContext(), entry.photoPath, binding.entryImage)
            }
        }.show(parentFragmentManager)
    }

    private val saveOnClick = View.OnClickListener {
        entry.activities = activities
        binding.noteEt.text?.let {
            entry.note = it.toString()
        }
        if (editMode) entryDetailViewModel.update(entry)
        else entryDetailViewModel.addEntry(entry)
        navigateToEntryFragment(entry)
    }
    private val onBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val action = EntryDetailFragmentDirections.actionEntryDetailFragmentToAddEntryFragment(
                date = entry.date,
                time = entry.time,
                initialFromBackPress = true,
                emojiValue = entry.emojiValue
            )
            val actionGoToEntriesFragment = EntryDetailFragmentDirections.actionEntryDetailFragmentToEntriesFragment(null)
            if (!editMode) {
                findNavController().navigate(action)
            } else findNavController().navigate(actionGoToEntriesFragment)
        }
    }

    override fun onEmojiItemClicked(emojiValue: Int) {
        entry.emojiValue = emojiValue
    }

    override fun onActivityItemClicked(activity: Activity, selected: Boolean) {
        val found = activities.find { it == activity }
        found?.let { if (!selected) activities.remove(activity) }
        if (found == null && selected)
            activities.add(activity)
    }

    override fun onDateSelected(persianPickerDate: PersianPickerDate) {
        entry.date = EntryDate(
            persianPickerDate.persianYear,
            persianPickerDate.persianMonth,
            persianPickerDate.persianDay
        )
        setupDate()
    }
}