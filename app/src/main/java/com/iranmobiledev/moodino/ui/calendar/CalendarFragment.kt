package com.iranmobiledev.moodino.ui.calendar

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.AverageMoodsDialogBinding
import com.iranmobiledev.moodino.databinding.FragmentCalendarBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.Jdn
import com.iranmobiledev.moodino.ui.calendar.calendarpager.isDailyMoods
import com.iranmobiledev.moodino.ui.calendar.toolbar.MainToolbarItemClickListener
import com.iranmobiledev.moodino.utlis.*
import io.github.persiancalendar.calendar.AbstractDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CalendarFragment : BaseFragment(), MainToolbarItemClickListener {
    private lateinit var binding: FragmentCalendarBinding
    private val viewModel: CalendarViewModel by viewModel()
    private var entries: List<Entry>?= null

    private lateinit var materialDialog: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarPager.also {
            it.onDayClicked = { jdn -> bringDate(jdn, monthChange = false) }

            it.setSelectedDay(
                Jdn(viewModel.selectedMonth.value),
                highlight = false,
                smoothScroll = false
            )

            it.onMonthSelected = { viewModel.changeSelectedMonth(it.selectedMonth) }
        }

        viewModel.selectedMonth
            .onEach { updateToolbar(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        if (viewModel.selectedDay != Jdn.today()) {
            bringDate(viewModel.selectedDay, monthChange = false, smoothScroll = false)
        } else {
            bringDate(Jdn.today(), monthChange = false, highlight = false)
        }

        binding.mainToolbar.initialize(this)

        viewModel.fetchEntries()

        // default title
        setFilter(resources.getString(R.string.daily_moods),
            R.drawable.ic_chart ,
            resources.getColor(R.color.primary, context?.theme))

        materialDialog = MaterialAlertDialogBuilder(requireContext(),R.style.MaterialAlertDialog_rounded)
        binding.filterLayout.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val view = AverageMoodsDialogBinding.inflate(layoutInflater, null, false)
        view.dailyMoods.setCount(entries?.size)
        view.averageMoods.setCount(entries?.size)

        view.radMoods.setCount(entries?.filter { it.emojiValue == EmojiValue.RAD }?.size)
        view.goodMoods.setCount(entries?.filter { it.emojiValue == EmojiValue.GOOD }?.size)
        view.mehMoods.setCount(entries?.filter { it.emojiValue == EmojiValue.MEH }?.size)
        view.badMoods.setCount(entries?.filter { it.emojiValue == EmojiValue.BAD }?.size)
        view.awfulMoods.setCount(entries?.filter { it.emojiValue == EmojiValue.AWFUL }?.size)
        val dialog= materialDialog.setView(view.root).create()

        val emojiFactory= EmojiFactory.create(requireContext())
        view.setClickListener { view->
            when(view.id){
                R.id.daily_moods -> {
                    isDailyMoods= true
                    setFilter(resources.getString(R.string.daily_moods),
                        R.drawable.ic_chart ,
                        resources.getColor(R.color.primary,context?.theme))
                    entries?.let { setData(it) }
                }
                R.id.average_moods -> {
                    isDailyMoods= false
                    setFilter(resources.getString(R.string.average_mood),
                        R.drawable.emoji_good,
                        resources.getColor(R.color.good_color,context?.theme))
                    entries?.let { setData(it) }
                }
                R.id.rad_moods ->{
                    val emoji= emojiFactory.getEmoji(EmojiValue.RAD)
                    setFilter(emoji.title, emoji.image, emoji.color)
                    entries?.filter { it.emojiValue == EmojiValue.RAD }
                        ?.let { it1 -> setData(it1) }
                }
                R.id.good_moods -> {
                    val emoji= emojiFactory.getEmoji(EmojiValue.GOOD)
                    setFilter(emoji.title, emoji.image, emoji.color)
                    entries?.filter { it.emojiValue == EmojiValue.GOOD }
                        ?.let { it1 -> setData(it1) }
                }
                R.id.meh_moods -> {
                    val emoji= emojiFactory.getEmoji(EmojiValue.MEH)
                    setFilter(emoji.title, emoji.image, emoji.color)
                    entries?.filter { it.emojiValue == EmojiValue.MEH }
                        ?.let { it1 -> setData(it1) }
                }
                R.id.bad_moods -> {
                    val emoji= emojiFactory.getEmoji(EmojiValue.BAD)
                    setFilter(emoji.title, emoji.image, emoji.color)
                    entries?.filter { it.emojiValue == EmojiValue.BAD }
                        ?.let { it1 -> setData(it1) }
                }
                R.id.awful_moods -> {
                    val emoji= emojiFactory.getEmoji(EmojiValue.AWFUL)
                    setFilter(emoji.title, emoji.image, emoji.color)
                    entries?.filter { it.emojiValue == EmojiValue.AWFUL }
                        ?.let { it1 -> setData(it1) }
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun setFilter(title: String, icon: Int, iconColor: Int){
        binding.filterTitle.text= title
        binding.filterIcon.setImageResource(icon)
        binding.filterIcon.imageTintList= ColorStateList.valueOf(iconColor)
    }

    private fun bringDate(
        jdn: Jdn,
        highlight: Boolean = true,
        monthChange: Boolean = true,
        smoothScroll: Boolean = true
    ) {
        binding.calendarPager.setSelectedDay(jdn, highlight, monthChange, smoothScroll)
        viewModel.changeSelectedDay(jdn)

    }

    private fun updateToolbar(date: AbstractDate) {
        viewModel.entries.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(200)
                val filterList =
                    it.filter { it.date?.year == date.year && it.date?.month == date.month }
                setData(filterList)
                entries= filterList
            }
        }
    }

    private fun setData(entries: List<Entry>){
        binding.calendarPager.setEntries(entries)
        binding.moodCountView.setEntries(entries)
    }

    override fun clickOnAdBtn() {
        Toast.makeText(context, "show ad", Toast.LENGTH_SHORT).show()
    }

    override fun clickOnPreviousBtn() {
        binding.calendarPager.clickOnPreviousMonth()
    }

    override fun clickOnCurrentMonthBtn() {

    }

    override fun clickOnNextMonthBtn() {
        binding.calendarPager.clickOnNextMonth()
    }

    override fun clickOnSearchBtn() {
        Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
    }

}