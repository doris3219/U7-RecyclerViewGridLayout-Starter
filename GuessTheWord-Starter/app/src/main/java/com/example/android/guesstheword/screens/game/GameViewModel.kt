package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // Countdown time
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    //MutableLiveData是一個通用類，因此您需要指定其保存的數據類型
    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>     //創建LiveData類型的公共版本，稱為score
        get() = _score          //覆寫get()中的score對象的方法GameViewModel並返回backing屬性_score


    // 創建_eventGameFinish保留遊戲結束的事件
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private val timer: CountDownTimer

    //創建一個對companion像以保存計時器常量
    companion object {
        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L
    }

    // The String version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init{
        Log.i("GameViewModel", "GameViewModel created!")
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()

        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ONE_SECOND         //該 millisUntilFinished直到計時器以毫秒為單位完成的時間量。轉換millisUntilFinished為秒並將其分配給_currentTime
            }
            override fun onFinish() {       //實施onFinish()以更新_currentTime和触發遊戲結束事件
                _currentTime.value = DONE
                onGameFinish()
            }
        }
        timer.start()
    }


    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        }else{
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }

    }

     fun onSkip() {
         //檢查score是否為null，並使用minus() -1
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
         //檢查score是否為null，並使用plus() +1
        _score.value = (_score.value)?.plus(1)
        nextWord()
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {      //跟踪GameViewModel生命週期
        super.onCleared()
        timer.cancel()
       // Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    /** Method for the game completed event **/
    fun onGameFinish() {
        _eventGameFinish.value = true
    }

    /** Method for the game completed event **/
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

}