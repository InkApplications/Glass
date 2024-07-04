package com.inkapplications.glassconsole

import ink.ui.structures.Sentiment

/**
 * Retrieve a resource sound file associated with a sentiment.
 */
val Sentiment.sound get() = when (this) {
    Sentiment.Primary -> R.raw.primary
    Sentiment.Positive -> R.raw.positive
    Sentiment.Caution -> R.raw.caution
    Sentiment.Negative -> R.raw.negative
    Sentiment.Idle -> R.raw.idle
    else -> R.raw.nominal
}
