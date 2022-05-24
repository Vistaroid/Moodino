package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.RecyclerViewData
import com.iranmobiledev.moodino.databinding.AddEntryCardViewBinding
import com.iranmobiledev.moodino.databinding.ItemEntryBinding
import com.iranmobiledev.moodino.databinding.ItemEntryContainerBinding
import com.iranmobiledev.moodino.listener.AddEntryCardViewListener
import com.iranmobiledev.moodino.listener.EntryEventLister
import com.iranmobiledev.moodino.utlis.MyDiffUtil
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

const val ENTRY_DEFAULT = 0
const val ENTRY_CARD = 1

class EntryContainerAdapter : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var entryEventListener: EntryEventLister
    private lateinit var data: List<RecyclerViewData>
    private var language: Int = -1
    private lateinit var addEntryCardEventLister: AddEntryCardViewListener
    var specifyDay = -1
    private val emptyStateVisibility = MutableLiveData<Boolean>()
    var copyData = mutableListOf<RecyclerViewData>()
    private var viewType = ENTRY_DEFAULT

    inner class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private var itemEntryContainerBinding: ItemEntryContainerBinding? = null

        init {
            if (viewType == ENTRY_DEFAULT)
                itemEntryContainerBinding = ItemEntryContainerBinding.bind(itemView)
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: RecyclerViewData) {
            if (viewType == ENTRY_DEFAULT) {
                itemEntryContainerBinding?.let {
                    if (data.entries.isNotEmpty()) {
                        setupUi(data)
                        setupNestedAdapter(data)
                    }
                    if (data.entries.size == 1)
                        it.entriesLable.visibility = View.GONE
                    else
                        it.entriesLable.visibility = View.VISIBLE
                }
            }
            itemView.setOnClickListener {
                if (it is CardView)
                    addEntryCardEventLister.onAddEntryCardClicked()
            }
        }

        private fun setupNestedAdapter(data: RecyclerViewData) {
            itemEntryContainerBinding?.let {
                it.entryRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                it.entryRv.itemAnimator = null
                val adapter =
                    ChildRecyclerView(
                        entryEventListener,
                        data.entries as MutableList<Entry>,
                        context,
                        language
                    )
                it.entryRv.adapter = adapter
                data.adapter = adapter
            }
        }

        private fun setupUi(data: RecyclerViewData) {
            itemEntryContainerBinding?.let {
                val result = setupLableColor(data)
                drawLable(result)
                val persianDate = PersianDate()
                persianDate.shMonth = data.entries[0].date.month
                persianDate.shDay = data.entries[0].date.day
                it.entriesDateTitle.text = PersianDateFormat.format(
                    persianDate,
                    "j F",
                    PersianDateFormat.PersianDateNumberCharacter.FARSI
                )
            }
        }

        private fun drawLable(result: Int) {
            itemEntryContainerBinding?.let {
                when (result) {
                    1 -> {
                        setLableColor(R.color.red_light)
                        setLableCircleColor(R.color.red_dark)
                        it.entriesDateTitle.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.red_dark
                            )
                        )
                    }
                    2 -> {
                        setLableColor(R.color.orange_light)
                        setLableCircleColor(R.color.orange_dark)
                        it.entriesDateTitle.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.orange_dark
                            )
                        )
                    }
                    3 -> {
                        setLableColor(R.color.blue_light)
                        setLableCircleColor(R.color.blue_dark)
                        it.entriesDateTitle.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.blue_dark
                            )
                        )
                    }
                    4 -> {
                        setLableColor(R.color.green_light)
                        setLableCircleColor(R.color.green_dark)
                        it.entriesDateTitle.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.green_dark
                            )
                        )
                    }
                    5 -> {
                        setLableColor(R.color.turquoise_light)
                        setLableCircleColor(R.color.turquoise_dark)
                        it.entriesDateTitle.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.turquoise_dark
                            )
                        )
                    }
                }
            }
        }

        private fun setLableColor(@ColorRes color: Int) {
            itemEntryContainerBinding?.let {
                it.entriesLable.backgroundTintList =
                    context.resources.getColorStateList(color, context.theme)
            }
        }

        private fun setLableCircleColor(@ColorRes color: Int) {
            itemEntryContainerBinding?.let {
                it.lableCircle.setColorFilter(
                    ContextCompat.getColor(context, color),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
        }

        private fun setupLableColor(data: RecyclerViewData): Int {
            var sum = 0
            data.entries.forEach {
                sum += it.emojiValue
            }
            return sum / data.entries.size
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<RecyclerViewData>) {
        val sortedByDay = data.sortedByDescending { it.date.day }
        val sortedByMonth = sortedByDay.sortedByDescending { it.date.month }
        val sortedByYear = sortedByMonth.sortedByDescending { it.date.year }
        val diffUtil = MyDiffUtil(this.data, sortedByYear)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.data = sortedByYear
        if (data.isNotEmpty())
            copyData = sortedByYear as MutableList<RecyclerViewData>
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            ENTRY_DEFAULT -> R.layout.item_entry_container
            ENTRY_CARD -> R.layout.add_entry_card_view
            else -> R.layout.item_entry_container
        }
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(view, parent, false),
            viewType
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (specifyDay == -1)
            holder.bind(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        val persianDate = PersianDate()
        val entryDate = EntryDate(persianDate.shYear, persianDate.shMonth, persianDate.shDay)
        val today = data.find { it.date == entryDate }
        if (today == null && position == 0){
            viewType = ENTRY_CARD
            return ENTRY_CARD
        }
        viewType = ENTRY_DEFAULT
        return ENTRY_DEFAULT
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun create(
        context: Context,
        entryEventListener: EntryEventLister,
        data: List<RecyclerViewData>,
        addEntryCardEventLister: AddEntryCardViewListener,
        language: Int
    ) {
        this.context = context
        this.entryEventListener = entryEventListener
        this.data = data as MutableList<RecyclerViewData>
        this.language = language
        this.copyData = data
        this.addEntryCardEventLister = addEntryCardEventLister
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindSpecificDay(day: Int) {
        data = data.filter {
            it.entries[0].date.day == day
        } as MutableList<RecyclerViewData>
        emptyStateVisibility.value = data.isEmpty()
        notifyDataSetChanged()
    }

    fun getEmptyStateLiveData(): LiveData<Boolean> {
        return emptyStateVisibility
    }

    fun positionOf(mDate: EntryDate, scrollToDay: Boolean): Int {
        val found =
            if (scrollToDay) data.find {
                mDate.year == it.date.year &&
                        mDate.month == it.date.month &&
                        mDate.day == it.date.day
            } else data.find { mDate.year == it.date.year && mDate.month == it.date.month }
        found?.let { return data.indexOf(it) }
        return -1
    }

    fun findDataWithPosition(position: Int): EntryDate {
        return data[position].date
    }
}

