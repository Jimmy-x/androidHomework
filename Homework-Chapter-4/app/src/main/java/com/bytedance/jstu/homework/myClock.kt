package com.bytedance.jstu.homework

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.withRotation
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.min

class myClock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        val surfaceBrush = Paint().apply {
            color = Color.argb(24, 255, 255, 255)
        }
        val edgeBrush = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(128, 255, 255, 255)
            strokeWidth = 3f
            style = Paint.Style.STROKE
        }
        val numBrush = Paint(Paint.SUBPIXEL_TEXT_FLAG).apply {
            textSize = 72f
            color = Color.WHITE
            typeface = Typeface.SERIF
        }
        val bigBrush = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            strokeWidth = 6f
        }
        val smallBrush = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            strokeWidth = 3f
        }
        val hourBrush = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            strokeWidth = 12f
            strokeCap = Paint.Cap.ROUND
        }
        val minBrush = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            strokeWidth = 6f
            strokeCap = Paint.Cap.SQUARE
        }
        val secBrush = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            strokeWidth = 3f
            strokeCap = Paint.Cap.ROUND
        }
    }

    private val rec = Rect()
    var changeTime: Boolean = false
    var valueNow: Float = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        canvas.translate(width / 2f, height / 2f)
        val r = min(width, height) / 2f
        canvas.drawCircle(0f, 0f, r, surfaceBrush)
        canvas.drawCircle(0f, 0f, r - 1.5f, edgeBrush)

        fun drawClock(tempString: String, tempAngle: Float) {
            numBrush.getTextBounds(tempString, 0, tempString.length, rec)
            canvas.withRotation(tempAngle) {
                drawText(tempString, -rec.width() / 2f, -r + rec.height() + 48f, numBrush)
                drawLine(0f, -r, 0f, -r + 32f, bigBrush)
                (6..24 step 6).forEach {
                    withRotation(it.toFloat()) {
                        drawLine(0f, -r, 0f, -r + 24f, smallBrush)
                    }
                }
            }
        }
        drawClock("12", 0f)
        drawClock("1", 30f)
        drawClock("2", 60f)
        drawClock("3", 90f)
        drawClock("4", 120f)
        drawClock("5", 150f)
        drawClock("6", 180f)
        drawClock("7", 210f)
        drawClock("8", 240f)
        drawClock("9", 270f)
        drawClock("10", 300f)
        drawClock("11", 330f)
        canvas.withRotation(valueNow / 120) {
            drawLine(0f, 0f, 0f, -r / 3, hourBrush)
        }
        canvas.withRotation(valueNow.mod(3600f) / 10) {
            drawLine(0f, 0f, 0f, -r / 2, minBrush)
        }
        canvas.withRotation(valueNow.mod(60f) * 6) {
            drawLine(0f, 0f, 0f, -r * .75f, secBrush)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        if (changeTime) {
            val temp = ((atan2(event.y - height / 2, event.x - width / 2) + PI / 2) / (PI * 2))
                .mod(1.0).toFloat()
            valueNow = temp * 43200
        } else {
            changeTime = true
        }
        return true
    }
}
