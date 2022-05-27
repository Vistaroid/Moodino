package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.*
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.RecyclerViewData
import com.iranmobiledev.moodino.databinding.BottomRvTextBinding
import com.iranmobiledev.moodino.databinding.ItemEntryContainerBinding
import com.iranmobiledev.moodino.callback.AddEntryCardViewListener
import com.iranmobiledev.moodino.callback.EntryEventLister
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

const val ENTRY_DEFAULT = 0
const val ENTRY_CARD = 1
const val BOTTOM_TEXT = 2

class EntryContainerAdapter(
    private val context: Context,
    private val entryEventListener: EntryEventLister,
    private val addEntryCardEventLister: AddEntryCardViewListener,
    private var data: List<RecyclerViewData>,
    private val language: Int
) : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {
    var specifyDay = -1
    private val emptyStateVisibility = MutableLiveData<Boolean>()
    var copyData = mutableListOf<RecyclerViewData>()
    var hasTodayEntry = true

    private val differCallback = object: DiffUtil.ItemCallback<RecyclerViewData>(){
        override fun areItemsTheSame(
            oldItem: RecyclerViewData,
            newItem: RecyclerViewData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecyclerViewData,
            newItem: RecyclerViewData
        ): Boolean {
            return when{
                oldItem.adapter != newItem.adapter -> false
                oldItem.entries != newItem.entries -> false
                oldItem.viewType != newItem.viewType -> false
                oldItem.date != newItem.date -> false
                else -> true
            }
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    inner class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private var itemEntryContainerBinding: ItemEntryContainerBinding? = null
        private var itemBottomRvTextBinding: BottomRvTextBinding? = null

        init {
            if (viewType == ENTRY_DEFAULT)
                itemEntryContainerBinding = ItemEntryContainerBinding.bind(itemView)
            if (viewType == BOTTOM_TEXT)
                itemBottomRvTextBinding = BottomRvTextBinding.bind(itemView)
        }

        @SuppressLint("SetTextI18n")
        fun bind(mData: RecyclerViewData) {
            if (mData.viewType == ENTRY_DEFAULT) {
                itemEntryContainerBinding?.let {
                    if (mData.entries.isNotEmpty()) {
                        setupUi(mData)
                        setupNestedAdapter(mData)
                    }
                    if (mData.entries.size == 1)
                        it.entriesLable.visibility = View.GONE
                    else
                        it.entriesLable.visibility = View.VISIBLE
                }
            }
            if (mData.viewType == BOTTOM_TEXT) {
                itemBottomRvTextBinding?.let {
                    it.bottomText.text = context.getString(R.string.its_time_to_play_memories)
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
                val adapter =
                    ChildRecyclerView(
                        entryEventListener,
                        listOf(),
                        context,
                        language
                    )
                adapter.updateData(data.entries)
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
                    "j F Y",
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


    fun setData(data: MutableList<RecyclerViewData>) {
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
            BOTTOM_TEXT -> R.layout.bottom_rv_text
            else -> R.layout.item_entry_container
        }
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(view, parent, false),
            viewType
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (specifyDay == -1){
            val data = differ.currentList[position]
            holder.bind(data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (differ.currentList[position].viewType == ENTRY_CARD) {
            hasTodayEntry = false
            return ENTRY_CARD
        }
        if (position == differ.currentList.lastIndex)
            return BOTTOM_TEXT
        return ENTRY_DEFAULT
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindSpecificDay(day: Int) {
        data = data.filter {
            it.viewType == ENTRY_DEFAULT
        } as MutableList<RecyclerViewData>
        data = data.filter { it.entries[0].date.day == day }
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

    fun entryPositionOf(entry: Entry): Int {
        val data = differ.currentList.find { it.date == entry.date } ?: return -1
        return differ.currentList.indexOf(data)
    }

    fun findDataWithPosition(position: Int): EntryDate {
        return differ.currentList[position].date
    }
}

