package com.training.trainingzonescalculator.models

import androidx.compose.ui.graphics.Color

data class ZoneDetails(
    var minLimit: Float,
    var maxLimit: Float,
    var zoneColor: Color,
    var zoneText: String
)

data class MinMaxPairs(
    var minLimit: Float,
    var maxLimit: Float
)
