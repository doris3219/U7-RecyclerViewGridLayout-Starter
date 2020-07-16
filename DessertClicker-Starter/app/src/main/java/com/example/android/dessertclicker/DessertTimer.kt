
// * Copyright 2019, The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
package com.example.android.dessertclicker
    import android.os.Handler
    import androidx.lifecycle.Lifecycle
    import androidx.lifecycle.LifecycleObserver
    import androidx.lifecycle.OnLifecycleEvent
    import timber.log.Timber
//
///**
// * This is a class representing a timer that you can start or stop. The secondsCount outputs a count of
// * how many seconds since it started, every one second.
// *
// * -----
// *
// * Handler and Runnable are beyond the scope of this lesson. This is in part because they deal with
// * threading, which is a complex topic that will be covered in a later lesson.
// *
// * If you want to learn more now, you can take a look on the Android Developer documentation on
// * threading:
// *
// * https://developer.android.com/guide/components/processes-and-threads

//構造函數接受一個Lifecycle對象，該對像是計時器正在觀察的生命週期。
//類定義實現LifecycleObserver接口
class DessertTimer(lifecycle: Lifecycle) : LifecycleObserver {

    // The number of seconds counted since the timer started
    var secondsCount = 0

    /**
     * [Handler] is a class meant to process a queue of messages (known as [android.os.Message]s)
     * or actions (known as [Runnable]s)
     */
    private var handler = Handler()
    private lateinit var runnable: Runnable

    init {      //使用addObserver()方法將從所有者（活動）傳入的生命週期對象連接到此類（觀察者）。
        lifecycle.addObserver(this)
    }

    //生命週期觀察者可以觀察到的所有生命週期事件都在Lifecycle.Event該類中
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer()  {
        // Create the runnable action, which prints out a log and increments the seconds counter
        runnable = Runnable {
            secondsCount++
            Timber.i("Timer is at : $secondsCount")
           // postDelayed re-adds the action to the queue of actions the Handler is cycling
            // through. The delayMillis param tells the handler to run the runnable in
            // 1 second (1000ms)
            handler.postDelayed(runnable, 1000)
        }

        // This is what initially starts the timer
       handler.postDelayed(runnable, 1000)

        // Note that the Thread the handler runs on is determined by a class called Looper.
        // In this case, no looper is defined, and it defaults to the main or UI thread.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable)
    }
}