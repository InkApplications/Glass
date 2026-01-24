package com.inkapplications.glassconsole.structures.elements

import ink.ui.structures.Sentiment
import ink.ui.structures.elements.UiElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("weather")
data class WeatherElement(
    val temperature: String,
    val condition: Condition,
    val secondaryText: String? = null,
    val title: String? = null,
    val daytime: Boolean = true,
    val sentiment: Sentiment? = null,
    val compact: Boolean = false,
): UiElement.Static {
    @Serializable
    enum class Condition
    {
        @SerialName("clear")
        Clear,
        @SerialName("cloudy")
        Cloudy,
        @SerialName("rainy")
        Rainy,
        @SerialName("snowy")
        Snowy,
    }
}
