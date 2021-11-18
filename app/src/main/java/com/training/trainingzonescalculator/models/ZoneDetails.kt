package com.training.trainingzonescalculator.models

import androidx.compose.ui.graphics.Color

data class ZoneDetails(
    val minLimit: Float,
    val maxLimit: Float,
    val zoneColor: Color,
    val zoneText: String,
    val zoneDescription: String
)

data class MinMaxPairs(
    var minLimit: Float,
    var maxLimit: Float
)

val maxHrList = arrayListOf(
        MinMaxPairs(0.5f, 0.6f),
        MinMaxPairs(0.6f, 0.7f),
        MinMaxPairs(0.7f, 0.8f),
        MinMaxPairs(0.8f, 0.9f),
        MinMaxPairs(0.9f, 1.0f)
)

val runLthrList = arrayListOf(
        MinMaxPairs(0.5f, 0.85f),
        MinMaxPairs(0.852f, 0.89f),
        MinMaxPairs(0.9f, 0.94f),
        MinMaxPairs(0.95f, 0.99f),
        MinMaxPairs(1.0f, 1.02f),
        MinMaxPairs(1.03f, 1.06f),
        MinMaxPairs(1.07f, 1.5f)
)

val bikeLthrList = arrayListOf(
        MinMaxPairs(0.5f, 0.81f),
        MinMaxPairs(0.812f, 0.89f),
        MinMaxPairs(0.9f, 0.93f),
        MinMaxPairs(0.94f, 0.99f),
        MinMaxPairs(1.0f, 1.02f),
        MinMaxPairs(1.03f, 1.06f),
        MinMaxPairs(1.07f, 1.5f)
)
val runFtpList = arrayListOf(
        MinMaxPairs(1.5f, 1.29f),
        MinMaxPairs(1.28f, 1.14f),
        MinMaxPairs(1.13f, 1.06f),
        MinMaxPairs(1.05f, 0.99f),
        MinMaxPairs(1.0f, 0.97f),
        MinMaxPairs(0.96f, 0.9f),
        MinMaxPairs(0.9f, 0.75f)
)

val bikeFtpList = arrayListOf(
        MinMaxPairs(0.3f, 0.55f),
        MinMaxPairs(0.555f, 0.74f),
        MinMaxPairs(0.75f, 0.89f),
        MinMaxPairs(0.9f, 1.04f),
        MinMaxPairs(1.05f, 1.2f),
        MinMaxPairs(1.2f, 6.0f)
)
