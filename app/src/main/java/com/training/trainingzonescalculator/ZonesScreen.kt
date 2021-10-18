package com.training.trainingzonescalculator

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.training.trainingzonescalculator.models.ZoneDetails

val maxHrList = arrayListOf(
    MinMaxPairs(0.5f, 0.6f),
    MinMaxPairs(0.6f, 0.7f),
    MinMaxPairs(0.7f, 0.8f),
    MinMaxPairs(0.8f, 0.9f),
    MinMaxPairs(0.9f, 1.0f)
)
val lthrList = arrayListOf(
    MinMaxPairs(0.5f, 0.85f),
    MinMaxPairs(0.852f, 0.89f),
    MinMaxPairs(0.9f, 0.94f),
    MinMaxPairs(0.95f, 0.99f),
    MinMaxPairs(1.0f, 1.02f),
    MinMaxPairs(1.03f, 1.06f),
    MinMaxPairs(1.07f, 1.5f)
)
val ftpList = arrayListOf(
    MinMaxPairs(1.5f, 1.29f),
    MinMaxPairs(1.28f, 1.14f),
    MinMaxPairs(1.13f, 1.06f),
    MinMaxPairs(1.05f, 0.99f),
    MinMaxPairs(1.0f, 0.97f),
    MinMaxPairs(0.96f, 0.9f),
    MinMaxPairs(0.9f, 0.75f)
)
val zoneNames = arrayListOf("Z1", "Z2", "Z3", "Z4", "Z5", "Z5b", "Z5c")
val zoneColors = arrayListOf( Color.Cyan, Color.Blue, Color.Green, Color.Yellow, Color.Magenta, Color.Red, Color.LightGray)

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
    var userInput by remember { mutableStateOf("") }
    var zones by remember { mutableStateOf(ArrayList<ZoneDetails>()) }
    val maxHrStr = stringResource(id = R.string.max_hr)
    val lthrStr = stringResource(id = R.string.lthr)
    val ftpStr = stringResource(id = R.string.ftp)

    var calcType = 0
    val errorStr = stringResource(id = R.string.wrong_data)
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
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(id = R.string.zones),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .height(35.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screenreaders
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
                            stringResource(id = R.string.ftp) + " " + stringResource(id = R.string.min_x_km)
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
                onDone = { focusManager.moveFocus(FocusDirection.Down) },
                onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            )
        )

        Text(
            text = stringResource(id = R.string.calculate),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 24.dp)
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
                    zones = calculateZones(userInput, calcType, errorStr)
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

        LazyColumn() {
            items(zones) { zone ->
                Row(
                    modifier = Modifier
                        .background(zone.zoneColor)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
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
                        text = Math.round(zone.minLimit).toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .weight(1.0f),
                        text = Math.round(zone.maxLimit).toString(),
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
    errorStr: String
): ArrayList<ZoneDetails> {

    try {
        return when (calcType) {
            1 -> {
                obtainZones(userInput.toFloat(), calcType, maxHrList)
            }
            2 -> {
                obtainZones(userInput.toFloat(), calcType, lthrList)
            }
            3 -> {
                obtainZones(userInput.toFloat(), calcType, ftpList)
            }
            else -> ArrayList<ZoneDetails>()
        }
    } catch (e: Exception) {
        return ArrayList<ZoneDetails>()
    }
}

private fun obtainZones(
    userInput: Float,
    calcType: Int,
    limitsList: ArrayList<MinMaxPairs>
): ArrayList<ZoneDetails> {
    val zonesList = ArrayList<ZoneDetails>()
    for (index in limitsList.indices) {
        zonesList.add(
            ZoneDetails(
                minLimit = userInput * limitsList[index].minLimit,
                maxLimit = userInput * limitsList[index].maxLimit,
                zoneColor = zoneColors[index],
                zoneText = if (calcType != 1 && index == 4)
                    zoneNames[index] + "a"
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
