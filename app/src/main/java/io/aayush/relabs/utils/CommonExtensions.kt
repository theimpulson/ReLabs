package io.aayush.relabs.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.style.QuoteSpan
import androidx.core.text.toSpanned

fun Spanned.formatBlockQuotes(designQuoteSpan: DesignQuoteSpan): Spanned {
    val spannableString = SpannableString(this)
    spannableString.apply {
        val quoteSpans = this.getSpans(0, this.length, QuoteSpan::class.java)
        quoteSpans.forEach {
            val start = this.getSpanStart(it)
            val end = this.getSpanEnd(it)
            val flags = this.getSpanFlags(it)
            this.removeSpan(it)
            this.setSpan(designQuoteSpan, start, end, flags)
        }
    }
    return spannableString.toSpanned()
}
