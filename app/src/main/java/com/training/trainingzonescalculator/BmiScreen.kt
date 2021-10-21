package com.training.trainingzonescalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@Composable
fun BmiScreen() {
    /*
    *
    * Text for the title (search for a nice decoration)
    * 2 radio buttons to choose the unit system (metric or English)
    * TextField to type the height in centimeters or inches
    * TextField to type the weight in kilograms or pounds
    * Button/Clickable Text to calculate
    * Text to show the results
    *
    * */
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bmi by remember { mutableStateOf("") }
    val imperialStr = stringResource(id = R.string.imperial)
    val errorStr = stringResource(id = R.string.wrong_data)
    val radioOptions =
        listOf(stringResource(id = R.string.metric), stringResource(id = R.string.imperial))
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
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        TextField(
            modifier = Modifier
                .padding(top = 16.dp),
            value = height,
            onValueChange = { height = it },
            label = {
                Text(
                    text = if (selectedOption == stringResource(id = R.string.imperial)) {
                        stringResource(id = R.string.height) + " " + stringResource(id = R.string.inches)
                    } else {
                        stringResource(id = R.string.height) + " " + stringResource(id = R.string.centimeters)
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

        TextField(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp),
            value = weight,
            onValueChange = { weight = it },
            label = {
                Text(
                    text = if (selectedOption == stringResource(id = R.string.imperial)) {
                        stringResource(id = R.string.weight) + " " + stringResource(id = R.string.pounds)
                    } else {
                        stringResource(id = R.string.weight) + " " + stringResource(id = R.string.kg)
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
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.primarySurface)
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .clickable {
                    keyboardController?.hide()
                    bmi = calculateBmi(weight, height, selectedOption, imperialStr, errorStr)
                },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.your_bmi),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            text = bmi,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            fontSize = 82.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

/*
    Formula (Metric System):  [weight (kg) / height (cm) / height (cm)] x 10000
    Formula (English System): [weight (lb) / height (in) / height (in)] x 703
*/
private fun calculateBmi(
    weight: String,
    height: String,
    selectedOption: String,
    imperialStr: String,
    errorStr: String
): String {
    val bmiCalculated = try {
        if (selectedOption == imperialStr) {
            "%.2f".format(((weight.toFloat() / height.toFloat() / height.toFloat()) * 703))
        } else {
            "%.2f".format(((weight.toFloat() / height.toFloat() / height.toFloat()) * 10000))
        }
    } catch (e: Exception) {
        errorStr
    }
    return bmiCalculated
}

