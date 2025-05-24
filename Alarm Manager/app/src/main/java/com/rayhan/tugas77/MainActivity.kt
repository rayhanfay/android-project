package com.rayhan.tugas77

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rayhan.tugas77.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmService: AlarmService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmService = AlarmService(this)

        binding.setAlarmSekarang.setOnClickListener {
            setAlarm { alarmService.setAlarm(it) }
        }

        binding.setUlangAlarm.setOnClickListener {
            setAlarm { alarmService.setAlarmBerulang(it) }
        }
    }

    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)

            DatePickerDialog(
                this@MainActivity,
                0,
                { _, tahun, bulan, hari ->
                    this.set(Calendar.YEAR, tahun)
                    this.set(Calendar.MONTH, bulan)
                    this.set(Calendar.DAY_OF_MONTH, hari)

                    TimePickerDialog(
                        this@MainActivity,
                        0,
                        { _, jam, menit ->
                            this.set(Calendar.HOUR_OF_DAY, jam)
                            this.set(Calendar.MINUTE, menit)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}