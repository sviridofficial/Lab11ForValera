package com.example.lab11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DrawSurface : SurfaceView, SurfaceHolder.Callback {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
        context: Context, attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    private var radius: Float = 50f
    private var cx = radius
    private var cy = radius
    private var dx = 10
    private var dy = 10

    private var paint = Paint()
    private lateinit var job: Job

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int,
        width: Int, height: Int
    ) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        job = GlobalScope.launch {
            var canvas: Canvas?
            var counter = 0;
            while (true) {
                if (counter > 1) {
                    counter = 0;
                }
                canvas = holder.lockCanvas(null)
                if (canvas != null) {
                    canvas.drawColor(Color.argb(255, 0, 192, 192))
                    if (counter == 0) {
                        paint.color = Color.WHITE
                        canvas.drawCircle(cx, cy, radius, paint)
                    } else if (counter == 1) {
                        paint.color = Color.GREEN
                        canvas.drawRect(cx-radius, cy-radius, cx+radius, cy+radius, paint)
                    }
                    holder.unlockCanvasAndPost(canvas)
                }
                // Перемещение шарика
                cx += dx
                cy += dy
                if (cx > width - radius || cx < radius) {
                    dx = -dx
                    counter++
                }
                if (cy > height - radius || cy < radius) {
                    dy = -dy
                    counter++
                }
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        job.cancel()
    }

    init {
        holder.addCallback(this)
    }
}
