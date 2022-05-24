package com.iranmobiledev.moodino.ui.calendar.toolbar

import android.animation.ObjectAnimator
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.animation.doOnEnd
import androidx.core.content.ContentProviderCompat.requireContext
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.databinding.MainToolbarBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.*
import io.github.persiancalendar.calendar.AbstractDate
import kotlinx.coroutines.delay
import saman.zamani.persiandate.PersianDate

class MainToolbar(context: Context, attr: AttributeSet) : LinearLayoutCompat(context, attr) {

    private val animationDuration = 100L
    private var view: MainToolbarBinding? = null
    private var title: String = ""
    private var mainToolbarItemClickListener: MainToolbarItemClickListener? = null

    //  private var monthsLimit = 5000
    // private var monthPosition = monthsLimit / 2
    private val baseJdn = Jdn.today()
    private var changeCurrentMonth: ChangeCurrentMonth? = null

    init {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater //LayoutInflater.from(context)
        view = MainToolbarBinding.inflate(inflater, this, true)

        // view?.title = title
        bind(monthPositionGlobal)

        view?.clickListener = OnClickListener {
            when (it.id) {
                R.id.adBtn -> ad()
                R.id.before_monthBtn -> animatePreviousMonth()
                R.id.month_name -> monthClick()
                R.id.next_monthBtn -> animateNextMonth()
                R.id.searchBtn -> search()
            }
        }
    }

    fun initialize(mainToolbarItemClickListener: MainToolbarItemClickListener) {
        this.mainToolbarItemClickListener = mainToolbarItemClickListener
    }

    fun initialize(changeCurrentMonth: ChangeCurrentMonth) {
        this.changeCurrentMonth = changeCurrentMonth
    }

    fun goToMonth(entryDate: EntryDate){
        val jdn= Jdn(CalendarType.SHAMSI,entryDate.year,entryDate.month,entryDate.day)
        val newPos= applyOffset(position = -mainCalendar.getMonthsDistance(baseJdn, jdn))
        monthPositionGlobal= newPos
        val date = bind(monthPositionGlobal)
        changeCurrentMonth?.changeCurrentMonth(date)
    }

    private fun ad() {
        mainToolbarItemClickListener?.clickOnAdBtn()
    }

    private fun beforeMonth() {
        mainToolbarItemClickListener?.clickOnPreviousBtn()
        monthPositionGlobal--
        val date = bind(monthPositionGlobal)
        changeCurrentMonth?.changeCurrentMonth(date)
    }

    private fun monthClick() {
        mainToolbarItemClickListener?.clickOnCurrentMonthBtn()
    }

    private fun nextMonth() {
        mainToolbarItemClickListener?.clickOnNextMonthBtn()
        monthPositionGlobal++
        val date = bind(monthPositionGlobal)
        changeCurrentMonth?.changeCurrentMonth(date)
    }

    private fun search() {
        mainToolbarItemClickListener?.clickOnSearchBtn()
    }


    private fun setMonthName(date: AbstractDate) {
        view?.title = date.monthName + " " + date.year
    }

    private fun bind(position: Int): AbstractDate {
        val date = mainCalendar.getMonthStartFromMonthsDistance(
            baseJdn, -applyOffset(position)
        )
        setMonthName(date)
        return date
    }

    private fun applyOffset(position: Int) = monthLimit / 2 - position

    private fun animateNextMonth() {
        val anim1 = ObjectAnimator.ofFloat(
            view?.monthName,
            TRANSLATION_X,
            0f,
            -view?.monthName?.width!! / 2f
        ).apply {
            duration = animationDuration
            start()
        }
        anim1.doOnEnd {
            ObjectAnimator.ofFloat(
                view?.monthName,
                TRANSLATION_X,
                view?.monthName?.width!! / 2f,
                0f
            ).apply {
                duration = animationDuration
                nextMonth()
                start()
            }
        }
    }

    private fun animatePreviousMonth() {
        val anim1 = ObjectAnimator.ofFloat(
            view?.monthName,
            TRANSLATION_X,
            0f,
            view?.monthName?.width!! / 2f
        ).apply {
            duration = animationDuration
            start()
        }
        anim1.doOnEnd {
            ObjectAnimator.ofFloat(
                view?.monthName,
                TRANSLATION_X,
                -view?.monthName?.width!! / 2f,
                0f
            ).apply {
                duration = animationDuration
                beforeMonth()
                start()
            }
        }
    }
}

interface MainToolbarItemClickListener {
    fun clickOnAdBtn()
    fun clickOnPreviousBtn()
    fun clickOnCurrentMonthBtn()
    fun clickOnNextMonthBtn()
    fun clickOnSearchBtn()
}

interface ChangeCurrentMonth {
    fun changeCurrentMonth(date: AbstractDate)
}