package com.example.pomodoro

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.example.pomodoro.databinding.ActivityMainBinding
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var timer: Timer
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var count = 0;


        timer = Timer { remainingTime ->
            runOnUiThread {
                val minutes = (remainingTime / 1000 / 60).toString().padStart(2, '0')
                val seconds = (remainingTime / 1000 % 60).toString().padStart(2, '0')
                binding.txtTime.text = "$minutes: $seconds"
            }
        }


        binding.buttonApp.setOnClickListener {
            binding.buttonApp.isEnabled = false
            binding.stopButton.isEnabled = true
            if (count % 2 == 0 && count != 0) {
                timer.start(1 * 60 * 1000)
                binding.txtCount.text = "$count/4"
                binding.root.setBackgroundColor(R.color.red)
                binding.txtTime.setBackgroundColor(R.color.light_red)
            } else {
                timer.start(2 * 60 * 1000)
                binding.txtCount.text = "$count/4"
                binding.root.setBackgroundColor(R.color.blue)
                binding.txtTime.setBackgroundColor(R.color.light_blue)
            }
            count++
        }

        binding.stopButton.setOnClickListener {
            binding.stopButton.isEnabled = false
            binding.buttonApp.isEnabled = true
            timer.stop()
        }

    }

    private fun onTimerFinished() {
            runOnUiThread {
        }
    }
}