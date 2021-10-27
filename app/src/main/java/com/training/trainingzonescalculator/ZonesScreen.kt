package com.training.trainingzonescalculator

import android.util.Log
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
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
    var minutes by remember { mutableStateOf("") }
    var seconds by remember { mutableStateOf("") }
    var value by remember { mutableStateOf(ArrayList<String>()) }
    value = arrayListOf( "0", "0" )
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

        if (selectedOption == stringResource(id = R.string.ftp) && sport == "Run") {
            Log.i("ZONES_LOG", "4 Minutes: $minutes, seconds: $seconds")
            value = arrayListOf(minutes, seconds)
            PacePicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                value = value,
                onValueChange = { value = it },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
        } else {
            Log.i("ZONES_LOG", "4 Minutes: $minutes, seconds: $seconds")
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
                        },
                        color = colorResource(id = R.color.purple_700)
                    )
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 24.sp),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() },
                    onNext = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = if (selectedOption == stringResource(id = R.string.ftp) &&
                        sport == "Run"
                    )
                        KeyboardType.Number
                    else
                        KeyboardType.Number
                )
            )
        }

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
                    minutes = value[0]
                    seconds = value[1]
                    zones = calculateZones(userInput, calcType, sport, minutes, seconds)
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
                        text =
                        if (sport == "Run" && selectedOption == ftpStr) {
                            "${
                                (zone.minLimit.roundToInt() / 60)
                                    .toString().padStart(2, '0')
                            }:${
                                (zone.minLimit.roundToInt() % 60)
                                    .toString().padStart(2, '0')
                            }"
                        } else {
                            zone.minLimit.roundToInt().toString()
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .weight(1.0f),
                        text =
                        if (sport == "Run" && selectedOption == ftpStr) {
                            "${
                                (zone.maxLimit.roundToInt() / 60)
                                    .toString().padStart(2, '0')
                            }:${
                                (zone.maxLimit.roundToInt() % 60)
                                    .toString().padStart(2, '0')
                            }"
                        } else {
                            zone.maxLimit.roundToInt().toString()
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun PacePicker(
    modifier: Modifier,
    value: ArrayList<String>,
    onValueChange: (ArrayList<String>) -> Unit,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center
) {
    val focusManager = LocalFocusManager.current
    var minutes by remember { mutableStateOf(value[0]) }
    var seconds by remember { mutableStateOf(value[1]) }

    Log.i("ZONES_LOG", "3 Minutes: $minutes, seconds: $seconds")
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.27f)
                .padding(end = 24.dp),
            value = minutes,
            onValueChange = {
                if (it.length <= 2) {
                    minutes = it
                    value[0] = it
                }
            },
            label = {
                Text(
                    text = "min",
                    color = colorResource(id = R.color.purple_700)
                )
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) },
                onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth(0.27f),
            value = seconds,
            onValueChange = {
                if (it.length <= 2) {
                    seconds = it
                    value[1] = it
                }
            },
            label = {
                Text(
                    text = "sec",
                    color = colorResource(id = R.color.purple_700)
                )
            },
            singleLine = true,

            textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() },
                onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            )
        )

    }
}

private fun calculateZones(
    userInput: String,
    calcType: Int,
    sport: String,
    minutes: String,
    seconds: String
): ArrayList<ZoneDetails> {

    Log.i("ZONES_LOG", "calcType: $calcType, sport: $sport")
    try {
        return when (calcType) {
            1 -> {
                obtainZones(
                    userInput = userInput.toFloat(),
                    calcType = calcType,
                    limitsList = maxHrList,
                    minutes = 0,
                    seconds = 0
                )
            }
            2 -> {
                obtainZones(
                    userInput = userInput.toFloat(),
                    calcType = calcType,
                    limitsList =
                    if (sport == "Run") runLthrList else bikeLthrList,
                    minutes = 0,
                    seconds = 0
                )
            }
            3 -> {
                Log.i("ZONES_LOG", "1 Minutes: $minutes, seconds: $seconds")
                obtainZones(
                    userInput = if (sport == "Run") 0.0f else userInput.toFloat(),
                    calcType = calcType,
                    limitsList = if (sport == "Run") runFtpList else bikeFtpList,
                    minutes = if (sport == "Run") minutes.toInt() else 0,
                    seconds = if (sport == "Run") seconds.toInt() else 0
                )
            }
            else -> ArrayList()
        }
    } catch (e: Exception) {
        Log.e("ZONES_LOG", "Error: ${e.message}")
        return ArrayList()
    }
}

// Calculate the training zones based on the tables defined at the top of this file
private fun obtainZones(
    userInput: Float,
    calcType: Int,
    minutes: Int,
    seconds: Int,
    limitsList: ArrayList<MinMaxPairs>
): ArrayList<ZoneDetails> {
    var inputData: Float = userInput
    val zonesList = ArrayList<ZoneDetails>()
    val limitsListSize = limitsList.size

    Log.i("ZONES_LOG", "2 Minutes: $minutes, seconds: $seconds")
    if (minutes > 0 && seconds > 0) {
        inputData = ((minutes * 60) + seconds).toFloat()
    }
    Log.i("ZONES_LOG", "inputData: $inputData")

    for (index in limitsList.indices) {
        zonesList.add(
            ZoneDetails(
                minLimit = inputData * limitsList[index].minLimit,
                maxLimit = inputData * limitsList[index].maxLimit,
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

/*
@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultZonesScreenPreviews() {
    PacePicker(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = arrayListOf("04", "30")
    )
}
*/

