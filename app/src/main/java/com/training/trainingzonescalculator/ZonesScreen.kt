package com.training.trainingzonescalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.training.trainingzonescalculator.models.ZoneDetails
import com.training.trainingzonescalculator.ui.theme.TrainingZonesCalculatorTheme
import kotlin.math.roundToInt

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

val zoneNames = arrayListOf("Z1", "Z2", "Z3", "Z4", "Z5", "Z5b", "Z5c")
val zoneColors = arrayListOf(
    Color.Cyan,
    Color.Blue,
    Color.Green,
    Color.Yellow,
    Color.Magenta,
    Color.Red,
    Color.LightGray
)

@ExperimentalComposeUiApi
@Composable
fun ZonesScreen() {
    /*
    *
    * Text for the title (search for a nice decoration)
    * 3 radio buttons to choose calculation method: Max HR, LTHR or FTPace
    * TextField to type the Hr or the pace
    * Button/Clickable Text to calculate
    * LazyColumn to show the different zones, 5 for MaxHr, 7 for the others
    *
    * */
    var sport by remember { mutableStateOf("Run") }
    var userInput by remember { mutableStateOf("") }
    var zones by remember { mutableStateOf(ArrayList<ZoneDetails>()) }
    val maxHrStr = stringResource(id = R.string.max_hr)
    val lthrStr = stringResource(id = R.string.lthr)
    val ftpStr = stringResource(id = R.string.ftp)

    var calcType: Int
    val radioOptions =
        listOf(
            stringResource(id = R.string.max_hr),
            stringResource(id = R.string.lthr),
            stringResource(id = R.string.ftp)
        )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.bike),
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .weight(1.0f)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.primarySurface,
                        shape = RectangleShape
                    )
                    .background(
                        color =
                        if (sport == "Run")
                            colorResource(id = R.color.light_grey)    //MaterialTheme.colors.surface
                        else
                            colorResource(id = R.color.white)     //MaterialTheme.colors.secondary
                    )
                    .clickable {
                        sport = "Bike"
                        userInput = ""
                        zones = ArrayList()
                    }
                    .padding(vertical = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.run),
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .weight(1.0f)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.primarySurface,
                        shape = RectangleShape
                    )
                    .background(
                        color =
                        if (sport == "Run")
                            colorResource(id = R.color.white)     //MaterialTheme.colors.secondary
                        else
                            colorResource(id = R.color.light_grey)   //MaterialTheme.colors.surface
                    )
                    .clickable {
                        sport = "Run"
                        userInput = ""
                        zones = ArrayList()
                    }
                    .padding(vertical = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

        }

        Column(
            modifier = Modifier
                .selectableGroup()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.Start
        ) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .wrapContentWidth(align = Alignment.Start)
                        .height(36.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                userInput = ""
                                zones = ArrayList()
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier
                            .wrapContentWidth(align = Alignment.Start),
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screen readers
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        TextField(
            modifier = Modifier
                .padding(top = 16.dp),
            value = userInput,
            onValueChange = { userInput = it },
            label = {
                Text(
                    text = when (selectedOption) {
                        stringResource(id = R.string.ftp) -> {
                            if (sport == "Run")
                                stringResource(id = R.string.ftp) + " " + stringResource(id = R.string.min_x_km)
                            else
                                stringResource(id = R.string.ftp) + " " + stringResource(id = R.string.watts)
                        }
                        stringResource(id = R.string.max_hr) -> {
                            stringResource(id = R.string.max_hr) + " " + stringResource(id = R.string.hr)
                        }
                        else -> {
                            stringResource(id = R.string.lthr) + " " + stringResource(id = R.string.hr)
                        }
                    }
                )
            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() },
                onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            )
        )

        Text(
            text = stringResource(id = R.string.calculate),
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.primarySurface)
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .clickable {
                    keyboardController?.hide()
                    calcType = when (selectedOption) {
                        maxHrStr -> 1
                        lthrStr -> 2
                        ftpStr -> 3
                        else -> 0
                    }
                    zones = calculateZones(userInput, calcType, sport)
                },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.your_zones),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        LazyColumn {
            items(zones) { zone ->
                Row(
                    modifier = Modifier
                        .background(zone.zoneColor)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .weight(1.0f),
                        text = zone.zoneText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .weight(1.0f),
                        text = zone.minLimit.roundToInt().toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .weight(1.0f),
                        text = zone.maxLimit.roundToInt().toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}

private fun calculateZones(
    userInput: String,
    calcType: Int,
    sport: String
): ArrayList<ZoneDetails> {

    try {
        return when (calcType) {
            1 -> {
                obtainZones(
                    userInput = userInput.toFloat(),
                    calcType = calcType,
                    limitsList = maxHrList
                )
            }
            2 -> {
                obtainZones(
                    userInput = userInput.toFloat(),
                    calcType = calcType,
                    limitsList =
                    if (sport == "Run")
                        runLthrList
                    else
                        bikeLthrList
                )
            }
            3 -> {
                obtainZones(
                    userInput = userInput.toFloat(),
                    calcType = calcType,
                    limitsList =
                    if (sport == "Run")
                        runFtpList
                    else
                        bikeFtpList
                )
            }
            else -> ArrayList()
        }
    } catch (e: Exception) {
        return ArrayList()
    }
}

// Calculate the training zones based on the tables defined at the top of this file
private fun obtainZones(
    userInput: Float,
    calcType: Int,
    limitsList: ArrayList<MinMaxPairs>
): ArrayList<ZoneDetails> {
    val zonesList = ArrayList<ZoneDetails>()
    val limitsListSize = limitsList.size
    for (index in limitsList.indices) {
        zonesList.add(
            ZoneDetails(
                minLimit = userInput * limitsList[index].minLimit,
                maxLimit = userInput * limitsList[index].maxLimit,
                zoneColor = zoneColors[index],
                zoneText =
                if (limitsListSize == 6 && index == 5)       // Description for the 6th element when
                    "Z6"                                     // obtaining 6 different zones
                else if (limitsListSize == 6 && index == 4)  // Description for the 5th element when
                    "Z5"                                     // obtaining 6 different zones
                else
                    if (calcType != 1 && index == 4)         // Description for the 5th element when
                        zoneNames[index] + "a"               // obtaining 7 different zones
                    else
                        zoneNames[index]
            )
        )
    }

    return zonesList
}

data class MinMaxPairs(
    var minLimit: Float,
    var maxLimit: Float
)

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultZonesScreenPreviews() {
    TrainingZonesCalculatorTheme {
        ZonesScreen()
    }
}