package com.iranmobiledev.moodino.ui.states.customView.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.iranmobiledev.moodino.R
import androidx.compose.ui.unit.sp

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

//    for(i in persianMonthsName){
//        Log.d("df234das", "DaysInYearComposable: $i")
//    }

    Column(
        modifier = Modifier
            .padding(
                bottom = 4.dp,
                start = 12.dp,
                end = 12.dp,
                top = 16.dp
            )
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        DaysInYearHeader()
        MonthsName(persianMonthsName)
        DaysInYearDetail(monthsLength)

        Spacer(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(id = R.color.gray))
        )

        MoodsChart()
    }

}

@Composable
fun MoodsChart() {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(top = 8.dp)
        .fillMaxWidth()) {
        ChartElement(2, colorResource(id = R.color.pink))
        ChartElement(4, colorResource(id = R.color.green))
        ChartElement(6, colorResource(id = R.color.gray))
        ChartElement(12, colorResource(id = R.color.orange))
        ChartElement(0, colorResource(id = R.color.blue_dark))
    }
}

@Composable
fun ChartElement(number: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeaderText(text = number.toString(), color = color)
        Spacer(modifier = Modifier
            .padding(top = 8.dp)
            .width(48.dp)
            .height(8.dp)
            .background(color = color))
    }
}

@Composable
fun DaysInYearHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        HeaderText("Year In Pixel")
        Icon(
            painter = painterResource(id = R.drawable.ic_share), contentDescription = "share",
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
    }
}

@Composable
fun HeaderText(text: String,color: Color = colorResource(id = R.color.black)) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = color
        )
    )
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


        //compose
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
            .width(12.dp)
            .height(12.dp)
            .background(color = colorResource(id = R.color.gray), shape = CircleShape),
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
            .height(12.dp),
        fontSize = 10.sp,
        style = TextStyle(
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.gray)
        ),
    )
}
























