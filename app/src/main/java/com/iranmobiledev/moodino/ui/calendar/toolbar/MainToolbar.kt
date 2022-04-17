package com.iranmobiledev.moodino.ui.calendar.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.MainToolbarBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.*
import io.github.persiancalendar.calendar.AbstractDate

class MainToolbar(context: Context, attr: AttributeSet) : LinearLayoutCompat(context, attr) {

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
                R.id.before_monthBtn -> beforeMonth()
                R.id.month_name -> monthClick()
                R.id.next_monthBtn -> nextMonth()
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

    private fun ad() {
        mainToolbarItemClickListener?.clickOnAdBtn()
    }

    private fun beforeMonth() {
        mainToolbarItemClickListener?.clickOnPreviousBtn()
        monthPositionGlobal--
        val date= bind(monthPositionGlobal)
        changeCurrentMonth?.changeCurrentMonth(date)
    }

    private fun monthClick() {
        mainToolbarItemClickListener?.clickOnCurrentMonthBtn()
    }

    private fun nextMonth() {
        mainToolbarItemClickListener?.clickOnNextMonthBtn()
        monthPositionGlobal++
        val date= bind(monthPositionGlobal)
        changeCurrentMonth?.changeCurrentMonth(date)
    }

    private fun search() {
        mainToolbarItemClickListener?.clickOnSearchBtn()
    }


    private fun setMonthName(date: AbstractDate) {
        view?.title = date.monthName + " " + date.year
    }

    fun bind(position: Int): AbstractDate {
        val date=  mainCalendar.getMonthStartFromMonthsDistance(
            baseJdn, -applyOffset(position)
        )
        setMonthName(date)
        return date
    }

    private fun applyOffset(position: Int) = monthLimit / 2 - position

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