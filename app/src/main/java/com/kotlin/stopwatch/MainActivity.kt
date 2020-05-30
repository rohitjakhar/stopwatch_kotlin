package com.kotlin.stopwatch

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var hour: TextView? = null
    var minute: TextView? = null
    var second: TextView? = null
    var milliSecond: TextView? = null
    var controllButton: Button? = null
    var resetButton: Button? = null


    var flag = false
    var handler: Handler? = null

    private var MillisecondTime: Long = 0
    private var StartTime: Long = 0
    private var TimeBuff: Long = 0
    private var UpdateTime = 0L

    private var Seconds: Int = 0
    private var Minutes: Int = 0
    private var Hour: Int = 0
    private var MilliSeconds: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hour = findViewById(R.id.hour)
        minute = findViewById(R.id.minute)
        second = findViewById(R.id.second)
        milliSecond = findViewById(R.id.milisecond)
        controllButton = findViewById(R.id.control)



        controllButton?.setOnClickListener {
            StartTime = SystemClock.elapsedRealtime()
            if (flag){
                handler?.removeCallbacks(runnable)
                controllButton?.text = "START"
                controllButton?.setBackgroundResource(R.drawable.start_button_background)
                flag = false
            }
            else {
                handler?.postDelayed(runnable,0)
                controllButton?.text = "STOP"
                StartTime = SystemClock.elapsedRealtime()
                controllButton?.setBackgroundResource(R.drawable.stop_button_background)
                flag = true
            }
        }


        handler = Handler()
    }

    private var runnable: Runnable = object : Runnable{
        @SuppressLint("SetTextI18n")
        override fun run() {
            MillisecondTime = SystemClock.elapsedRealtime() - StartTime
            UpdateTime = TimeBuff + MillisecondTime
            Seconds = (UpdateTime  / 1000).toInt()
            Minutes = Seconds / 60
            Hour = Minutes / 60
            Seconds %= 60
            MilliSeconds = (UpdateTime % 1000).toInt()

            when(MilliSeconds.toString().length){
                0 -> milliSecond?.text = "000"
                1 -> milliSecond?.text = "00$MilliSeconds"
                2 -> milliSecond?.text = "0$MilliSeconds"
                3 -> milliSecond?.text = "$MilliSeconds"
            }
            if(Seconds.toString().length <2 ) {
                second?.text = "0$Seconds"
            } else {
                second?.text = Seconds.toString()
            }
            if (Minutes.toString().length < 2){
                minute?.text = "0$Minutes"
            } else {
                minute?.text = Minutes.toString()
            }
            if (Hour.toString().length < 2){
                hour?.text = "0$Hour"
            } else {
                hour?.text = Hour.toString()
            }
            handler?.postDelayed(this, 0)
        }
    }

}
