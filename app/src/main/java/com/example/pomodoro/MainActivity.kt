package com.example.pomodoro

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.example.pomodoro.databinding.ActivityMainBinding
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var timer: Timer
    private lateinit var binding: ActivityMainBinding
    private var count = 1

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        timer = Timer(
            { remainingTime ->
                runOnUiThread {
                    val minutes = (remainingTime / 1000 / 60).toString().padStart(2, '0')
                    val seconds = (remainingTime / 1000 % 60).toString().padStart(2, '0')
                    binding.txtTime.text = "$minutes:$seconds"
                }
            }, { onTimerFinished() }
        )

        binding.resumeButton.isEnabled = false
        binding.stopButton.isEnabled = false


        binding.buttonApp.setOnClickListener {
            binding.resumeButton.isEnabled = false
            binding.buttonApp.isEnabled = false
            binding.stopButton.isEnabled = true
            if (count % 2 == 0 && count != 4) {
                timer.start(5 * 60 * 1000)
            } else if (count == 4) {
                timer.start(15 * 60 * 1000)
            } else {
                timer.start(1 * 60 * 1000)
            }
        }

        binding.stopButton.setOnClickListener {
            binding.stopButton.isEnabled = false
            binding.buttonApp.isEnabled = false
            binding.resumeButton.isEnabled = true
            timer.stop()
        }

        binding.resumeButton.setOnClickListener {
            binding.stopButton.isEnabled = true
            binding.resumeButton.isEnabled = false
            timer.resume()
        }

    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun onTimerFinished() {
        if(count % 2 == 0) {
            binding.root.setBackgroundColor(Color.parseColor("#c76d6d"))
            binding.txtTime.setBackgroundColor(Color.parseColor("#cd7c7c"))
            binding.txtTime.text = "25:00"
            binding.txtTittle.text = "POMO TIMER"
        } else if (count == 5) {
            binding.root.setBackgroundColor(Color.parseColor("#BAD7E9"))
            binding.txtTime.setBackgroundColor(Color.parseColor("#2B3467"))
            binding.txtTime.text = "05:00"
            binding.txtTittle.text = "BREAK"
            count = 1
        } else {
            if (count == 3) {
                binding.txtTime.text = "15:00"
                binding.txtTittle.text = "LONG BREAK"
            } else {
                binding.txtTittle.text = "BREAK"
                binding.txtTime.text = "05:00"
            }
            binding.root.setBackgroundColor(Color.parseColor("#BAD7E9"))
            binding.txtTime.setBackgroundColor(Color.parseColor("#2B3467"))
        }
        binding.txtCount.text = "${count}/4"
        count++
        binding.stopButton.isEnabled = false
        binding.buttonApp.isEnabled = true
    }
}

