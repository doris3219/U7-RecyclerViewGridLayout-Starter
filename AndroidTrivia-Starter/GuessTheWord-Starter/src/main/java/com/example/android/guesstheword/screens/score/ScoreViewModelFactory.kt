package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//負責實例化該ScoreViewModel對象,為最終得分添加構造函數參數
 class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {

    //return the newly constructed ScoreViewModel object
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
