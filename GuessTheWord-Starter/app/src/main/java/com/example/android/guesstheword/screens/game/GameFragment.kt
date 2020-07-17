/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    //透過引用將ViewModel與UI控制器關聯
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.i("GameFragment", "Called ViewModelProviders.of")
        //獲得viewModel,(調用ViewModelProviders.of()方法來創建GameViewModel)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        /** Setting up LiveData observation relationship **/
        //使用observe()方法，並將代碼放在初始化之後viewModel。使用lambda表達式簡化代碼
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            //當觀察LiveData對象保存的數據發生更改時，剛剛創建的觀察者將收到一個事件。在觀察者內部，TextView用新分數更新分數
            binding.scoreText.text = newScore.toString()
        })

        /** Setting up LiveData observation relationship **/
        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })

        // Observer for the Game finished event
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished ->
            if (hasFinished) gameFinished()
        })

        //使用綁定變量binding。在點擊監聽器中，調用方法
        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }


        return binding.root

    }

    /**
     * Resets the list of words and randomizes the order
     */

    /** Methods for buttons presses **/
    ////onSkip()方法是“ 跳過”按鈕的單擊處理程序。它將分數降低1，然後使用nextWord()方法顯示下一個單詞
    private fun onSkip() {
        viewModel.onSkip()
    }

    //onCorrect()方法“ 獲得”按鈕的單擊處理程序,答對分數+1
    private fun onCorrect() {
        viewModel.onCorrect()
    }



    //onEndGame()當用戶點擊“ 結束遊戲”按鈕時，將調用該方法
    private fun onEndGame() {
        gameFinished()
    }

    /**
     * Called when the game is finished
     * 使用Safe Args傳遞分數作為參數
     */
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value?:0
        findNavController(this).navigate(action)
        viewModel.onGameFinishComplete()
    }
}
