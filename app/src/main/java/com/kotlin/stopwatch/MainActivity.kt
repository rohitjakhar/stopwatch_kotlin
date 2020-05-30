package com.kotlin.stopwatch

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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

        control.setOnClickListener {
            StartTime = SystemClock.elapsedRealtime()
            if (flag){
                handler?.removeCallbacks(runnable)
                control.text = "START"
                control.setBackgroundResource(R.drawable.start_button_background)
                flag = false
            }
            else {
                handler?.postDelayed(runnable,0)
                control.text = "STOP"
                StartTime = SystemClock.elapsedRealtime()
                control.setBackgroundResource(R.drawable.stop_button_background)
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
                0 -> millisecond.text = "000"
                1 -> millisecond.text = "00$MilliSeconds"
                2 -> millisecond.text = "0$MilliSeconds"
                3 -> millisecond.text = "$MilliSeconds"
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
