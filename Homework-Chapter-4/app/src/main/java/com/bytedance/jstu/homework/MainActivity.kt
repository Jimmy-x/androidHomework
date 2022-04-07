package com.bytedance.jstu.homework

import android.animation.TimeAnimator
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.lang.Math.floor
import java.util.*
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val clockView = findViewById<myClock>(R.id.myClock)
		val timeText = findViewById<TextView>(R.id.myClockText)
		if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			(clockView.parent as ViewGroup).visibility = ViewGroup.GONE
		}
		TimeAnimator().apply {
			setTimeListener { _, _, _ ->
				if (!clockView.changeTime)
					clockView.valueNow = Calendar.getInstance().let {
					it.get(Calendar.HOUR) * 3600 + it.get(Calendar.MINUTE) * 60 + it.get(Calendar.SECOND) + it.get(Calendar.MILLISECOND) / 1000f
				}
				clockView.invalidate()
				timeText.text = String.format("%02.0f:%02.0f:%02.0f",
					floor(clockView.valueNow / 3600f),
					floor(clockView.valueNow.mod(3600f) / 60f),
					floor(clockView.valueNow.mod(60f)),
				)
			}
		}.start()
		findViewById<Button>(R.id.myButton).setOnClickListener {
			clockView.changeTime=false;
			clockView.valueNow = Calendar.getInstance().let {
					it.get(Calendar.HOUR) * 3600 + it.get(Calendar.MINUTE) * 60 + it.get(Calendar.SECOND) + it.get(Calendar.MILLISECOND) / 1000f
				}
        }
	}
}