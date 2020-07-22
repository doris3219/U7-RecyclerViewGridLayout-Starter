package com.example.android.trackmysleepquality.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

////@BindingAdapter告知有關此綁定適配器的數據綁定,sleepDurationFormatted是屬性的適配器
@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight) {
    //將數據綁定到視圖ViewHolder.bind()。通話convertDurationToFormatted()，然後設置text的TextView，以格式化文本
    text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)


    //設置睡眠質量SleepNight。創建一個名為setSleepQualityString()on 的擴展函數TextView，並傳入SleepNight
    //將數據綁定到視圖ViewHolder.bind()。呼叫convertNumericQualityToString並設置text
    @BindingAdapter("sleepQualityString")
    fun TextView.setSleepQualityString(item: SleepNight) {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }


    @BindingAdapter("sleepImage")
    fun ImageView.setSleepImage(item: SleepNight) {
        setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }
}