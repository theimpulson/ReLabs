package io.aayush.relabs.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan
import android.text.style.LineBackgroundSpan

class DesignQuoteSpan : LeadingMarginSpan, LineBackgroundSpan {

    private val gap = 20F

    override fun getLeadingMargin(first: Boolean): Int {
        return (0F + gap).toInt()
    }

    override fun drawLeadingMargin(
        c: Canvas,
        p: Paint,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout
    ) {
        val style = p.style
        val paintColor = p.color
        p.style = Paint.Style.FILL
        p.color = Color.TRANSPARENT
        c.drawRect(x.toFloat(), top.toFloat(), x + dir * 1F, bottom.toFloat(), p)
        p.style = style
        p.color = paintColor
    }

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val paintColor = paint.color
        paint.color = Color.GRAY
        canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        paint.color = paintColor
    }
}
