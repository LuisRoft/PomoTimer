package com.example.pomodoro

import android.os.CountDownTimer

class Timer(private val updateUi: (Long) -> Unit, private val timerFinished: () -> Unit) {
    private var timer: CountDownTimer? = null
    private var isRunning = false
    private var remainingTime: Long = 0

    fun start(duration: Long) {
        isRunning = true
        remainingTime = duration
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                updateUi(millisUntilFinished)
            }

            override fun onFinish() {
                isRunning = false
                timerFinished()
            }
        }.start()

    }

    fun stop() {
        timer?.cancel()
        isRunning = false
    }

    fun resume() {
        if (!isRunning) {
            start(remainingTime)
        }
    }

    fun isRunning() = isRunning
}
