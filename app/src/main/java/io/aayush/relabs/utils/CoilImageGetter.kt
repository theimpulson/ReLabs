package io.aayush.relabs.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import coil.Coil
import coil.request.ImageRequest

class CoilImageGetter(
    private val context: Context
) : Html.ImageGetter {

    override fun getDrawable(source: String?): Drawable {
        val drawablePlaceHolder = DrawablePlaceHolder()

        val imageRequest = ImageRequest.Builder(context)
            .data(source)
            .target { drawablePlaceHolder.updateDrawable(it) }
            .crossfade(true)
            .build()
        Coil.imageLoader(context).enqueue(imageRequest)
        return drawablePlaceHolder
    }

    @Suppress("DEPRECATION")
    private class DrawablePlaceHolder : BitmapDrawable() {

        private var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
        }

        fun updateDrawable(drawable: Drawable) {
            this.drawable = drawable
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            drawable.setBounds(0, 0, width, height)
            setBounds(0, 0, width, height)
        }
    }
}
