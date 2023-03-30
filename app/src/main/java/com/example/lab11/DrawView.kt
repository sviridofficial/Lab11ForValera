package com.example.lab11

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DrawView : View {
    constructor(context : Context) : this(context, null)
    constructor(context : Context, attrs : AttributeSet?) : this(context, attrs, 0)
    constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int) :
            super(context, attrs, defStyleAttr)

    private var w: Float = 0f // Ширина представления
    private var h: Float = 0f // Высота представления
    private var groundY: Float = 0f // Верхняя линия земли
    private var treeWidth: Float = 0f // Ширина дерева
    private var treeHeight: Float = 0f // Высота дерева
    private var crownDiameter: Float = 0f // Диаметр кроны
    private var crownX: Float = 0f // Центр кроны по X
    private var crownY: Float = 0f // Центр кроны по Y

    private var paint = Paint()

    private var _season: Int = 0
    var season: Int
        get() = this._season
        set(value) {
            this._season = value
            invalidate()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h = h.toFloat()
        groundY = this.h - this.h / 3
        treeWidth = this.w / 10
        treeHeight = this.h / 6
        crownDiameter = this.w / 5
        crownX = this.w / 2
        crownY = groundY - treeHeight - crownDiameter
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawColor(Color.argb(255, 0, 128, 255))
        // Земля
        paint.color = if (_season == 0)
            Color.argb(255, 128, 64, 0) // Коричневая земля
        else
            Color.argb(255, 255, 255, 255) // Белый снег
        canvas.drawRect(0f, groundY, w, h, paint)
        // Ствол дерева
        paint.color = Color.argb(255, 64, 32, 0)
        canvas.drawRect(w / 2 - treeWidth / 2, groundY - treeHeight,
            w / 2 + treeWidth / 2, groundY, paint)
        // Крона
        paint.color = if (_season == 0)
            Color.argb(255, 0, 224, 96) // Зеленая крона
        else
            Color.argb(255, 255, 255, 255) // Снежная крона
        canvas.drawCircle(crownX, crownY, crownDiameter, paint)
    }

    fun getBitmap(): Bitmap? {
        var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)

        draw(canvas)
        return bitmap
    }
}

