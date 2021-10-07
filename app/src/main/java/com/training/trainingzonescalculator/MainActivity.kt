package com.training.trainingzonescalculator

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.training.trainingzonescalculator.ui.theme.TrainingZonesCalculatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainingZonesCalculatorTheme {
                TrainingZonesCalcApp()
            }
        }
    }
}

@Composable
fun TrainingZonesCalcApp() {
    val activity = (LocalContext.current as? Activity)
    var expanded by remember { mutableStateOf(false)}

    // A surface container using the 'background' color from the theme
    Surface(color = colorResource(id = R.color.light_grey)) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.Menu, contentDescription = "menu")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        modifier = Modifier
                            .background(color = Color.White),
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            /* Handle refresh! */
                        }) {
                            Text(
                                text = stringResource(id = R.string.max_hr),
                                fontSize = 15.sp
                            )
                        }
                        DropdownMenuItem(onClick = {
                            /* Handle settings! */
                        }) {
                            Text(
                                text = stringResource(id = R.string.lthr),
                                fontSize = 15.sp)
                        }
                        DropdownMenuItem(onClick = {
                            /* Handle send feedback! */
                        }) {
                            Text(
                                text = stringResource(id = R.string.ftp),
                                fontSize = 15.sp)
                        }
                        //Divider()
                        DropdownMenuItem(onClick = {
                            /* Handle send feedback! */
                        }) {
                            Text(
                                text = stringResource(id = R.string.help),
                                fontSize = 15.sp)
                        }
                        Divider(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                            thickness = 2.dp)
                        DropdownMenuItem(onClick = {
                            activity?.finish()
                        }) {
                            Text(
                                text = stringResource(id = R.string.exit),
                                fontSize = 15.sp)
                        }
                    }
                },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(Icons.Filled.Close, contentDescription = "Localized description")
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrainingZonesCalculatorTheme {
        TrainingZonesCalcApp()
    }
}