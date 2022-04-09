package com.iranmobiledev.moodino.ui.states.customView.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun DaysInYearComposable() {

    var monthsLength = arrayListOf<Int>(
        31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 29, 29
    )

    var persianMonthsName = arrayListOf(
        "FA",
        "OR",
        "KH",
        "TI",
        "MO",
        "SH",
        "ME",
        "AB",
        "AZ",
        "DA",
        "BA",
        "ES",
    )

    Column(
        modifier = Modifier
            .padding(bottom = 4.dp,
                start = 12.dp,
                end = 12.dp,
                top = 16.dp
            )
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        MonthsName(persianMonthsName)
        DaysInYearDetail(monthsLength)
    }

}

@Composable
fun DaysInYearDetail(monthsLength: ArrayList<Int>) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Column {
            for (i in 0..31) {
                MonthNumber(i)
            }
        }

        monthsLength.forEach {
            MonthComposable(monthLength = it)
        }
    }
}

@Composable
fun MonthComposable(monthLength: Int) {
    Column(
    ) {
        for (i in 0..monthLength) {
            DayDots()
        }
    }
}

@Composable
fun DayDots() {
    Box(
        Modifier
            .padding(bottom = 4.dp)
            .width(10.dp)
            .height(10.dp)
            .background(color = Color.Gray, shape = CircleShape),

        )
}

@Composable
fun MonthNumber(i: Int) {
    DaysInYearText(text = i.toString(), paddingEnd = 4.dp, paddingBottom = 4.dp)
}

@Composable
fun MonthsName(persianMonthsName: ArrayList<String>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(start = 36.dp, end = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
    ) {
        persianMonthsName.forEach {
            DaysInYearText(text = it)
        }
    }
}

@Composable
fun DaysInYearText(
    text: String,
    paddingBottom: Dp = 0.dp,
    paddingTop: Dp = 0.dp,
    paddingStart: Dp = 0.dp,
    paddingEnd: Dp = 0.dp,
) {
    Text(
        text = text,
        modifier = Modifier
            .padding(
                bottom = paddingBottom,
                end = paddingEnd,
                start = paddingStart,
                top = paddingTop
            )
            .height(10.dp)
        ,
        fontSize = 8.sp,
    )
}


























