package edu.temple.viewapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class MyView(context: Context) : View (context){

    val values = FloatArray(3)

    var cx = 50f
    var cy = 50f

    var xDir = 1
    var yDir = 1

    val multiplier = 10


    val radius = 50f

    val redPaint = Paint()
    val bluePaint = Paint().apply { color = Color.BLUE }

    val coords = MutableList<Float>(2){0f}

    init {
        redPaint.color = Color.RED
        redPaint.strokeWidth = 5f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        performClick()
        event?.run {
            coords.add(x)
            coords.add(y)
            coords.add(x)
            coords.add(y)

            invalidate()
        }

        return true
    }

    fun rotationChanged(values: FloatArray) {
        System.arraycopy(values, 0, this.values, 0, values.size)
        this.values[1] = this.values[1] * -2
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLines(coords.toFloatArray(), redPaint)

        canvas?.drawCircle(cx, cy, radius, bluePaint)

        if ((values[2] < 0 && cx > radius) || (values[2] > 0 && cx < width - radius) )
            cx += values[2] * multiplier
        if ((values[1] < 0 && cy > radius) || (values[1] > 0 && cy < height - radius) )
            cy += values[1] * multiplier

        /*
        if (cx >= width - radius) xDir = -1
        if (cx < radius) xDir = 1

        if (cy >= height - radius) yDir = -1
        if (cy < radius) yDir = 1

        cx += xDir * multiplier
        cy += yDir * multiplier
        */

        super.onDraw(canvas)
    }
}