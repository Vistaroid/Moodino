package com.iranmobiledev.moodino.ui.calendar.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.MainToolbarBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.monthName
import io.github.persiancalendar.calendar.AbstractDate

class MainToolbar(context: Context, attr: AttributeSet): LinearLayoutCompat(context,attr) {

    private var view: MainToolbarBinding?= null
    private var title: String= ""
    private var mainToolbarItemClickListener: MainToolbarItemClickListener?= null

    init {
        val inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater //LayoutInflater.from(context)
        view= MainToolbarBinding.inflate(inflater,this, true)

        view?.title= title

        view?.clickListener= OnClickListener {
            when(it.id){
                R.id.adBtn -> mainToolbarItemClickListener?.clickOnAdBtn()
                R.id.before_monthBtn -> mainToolbarItemClickListener?.clickOnPreviousBtn()
                R.id.month_name -> mainToolbarItemClickListener?.clickOnCurrentMonthBtn()
                R.id.next_monthBtn -> mainToolbarItemClickListener?.clickOnNextMonthBtn()
                R.id.searchBtn -> mainToolbarItemClickListener?.clickOnSearchBtn()
            }
        }
    }

    fun initialize(mainToolbarItemClickListener: MainToolbarItemClickListener){
        this.mainToolbarItemClickListener= mainToolbarItemClickListener
    }

    private fun ad(){
        Toast.makeText(context, "show ad", Toast.LENGTH_SHORT).show()
    }

    private fun beforeMonth(){

    }

    private fun monthClick(){

    }

    private fun nextMonth(){

    }

    private fun search(){
        Toast.makeText(context, "search", Toast.LENGTH_SHORT).show()
    }

//    fun getCurrentMonth(): String{
//
//        return ""
//    }


    fun setMonth(date: AbstractDate){
        view?.title= date.monthName  + " "+ date.year
    }

}

interface MainToolbarItemClickListener{
    fun clickOnAdBtn()
    fun clickOnPreviousBtn()
    fun clickOnCurrentMonthBtn()
    fun clickOnNextMonthBtn()
    fun clickOnSearchBtn()
}

interface ChangeCurrentMonth{
    fun changeCurrentMonth(month: String)
}