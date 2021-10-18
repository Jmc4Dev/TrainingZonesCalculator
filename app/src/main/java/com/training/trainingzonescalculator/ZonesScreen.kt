package com.training.trainingzonescalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
                    text = if (selectedOption == stringResource(id = R.string.ftp)) {
                        stringResource(id = R.string.ftp) + " " + stringResource(id = R.string.min_x_km)
                    } else if (selectedOption == stringResource(id = R.string.max_hr)) {
                        stringResource(id = R.string.max_hr) + " " + stringResource(id = R.string.hr)
                    } else {
                        stringResource(id = R.string.lthr) + " " + stringResource(id = R.string.hr)
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
                    //bmi = calculateBmi(weight, height, selectedOption, imperialStr, errorStr)
                },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.your_zones),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        // TODO: Here the LazyColumns for the results
        LazyColumn( ){
            item {
                // loop to show the different zones
            }
        }
    }
}