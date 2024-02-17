package com.example.spen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = with(Paint()) {
        strokeWidth = 10f
        color = android.graphics.Color.BLACK
        this
    }
    private val handPaint = with(Paint()) {
        strokeWidth = 10f
        color = android.graphics.Color.RED
        this
    }

    private val points = mutableListOf<List<Point>>()
    private var currentPoints = mutableListOf<Point>()
    var allowHandDrawing = true

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        points.forEach {
            val isStylus = it.firstOrNull()?.isStylus ?: false

            for (i in 0 until it.size - 1) {
                val p1 = it[i]
                val p2 = it[i + 1]

                canvas.drawLine(p1.x, p1.y, p2.x, p2.y, if (isStylus) paint else handPaint)
            }
        }

        for (i in 0 until currentPoints.size - 1) {
            val p1 = currentPoints[i]
            val p2 = currentPoints[i + 1]

            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, if (p1.isStylus) paint else handPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y

                if (event.getToolType(0) == MotionEvent.TOOL_TYPE_STYLUS) {
                    currentPoints.add(Point(x, y, true))
                } else if (allowHandDrawing) {
                    currentPoints.add(Point(x, y, false))
                }

                invalidate()
            }
            // Handle case where the motion stopped
            MotionEvent.ACTION_UP -> {
                // clone current points and add to points
                points.add(currentPoints.toList())
                currentPoints.clear()
                invalidate()
            }
        }
        return true
    }

    fun clear() {
        points.clear()
        currentPoints.clear()
        invalidate()
    }
}

class Point(val x: Float, val y: Float, val isStylus: Boolean)
