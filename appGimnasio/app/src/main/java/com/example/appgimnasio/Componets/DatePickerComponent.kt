package com.example.appgimnasio.Componets

import android.app.AlertDialog
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerFieldToModal(
    titulo: String,
    descripcion: String,
    modifier: Modifier = Modifier,
    onDateSelected: (String) -> Unit
) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    TextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        label = { Text(text = titulo, fontSize = 20.sp) },
        readOnly = true,
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = descripcion)
        },
        modifier = modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        colors = outlinedTextFieldColors(
            focusedTextColor = Color.Black,
            focusedLabelColor = Color(0xFF0F4C5C),
            focusedBorderColor = Color(0xFF0F4C5C)
        )
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { millis ->
                selectedDate = millis
                millis?.let {
                    onDateSelected(convertMillisToDate(it))
                }
            },
            onDismiss = { showModal = false }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return formatter.format(Date(millis))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    val colores = DatePickerDefaults.colors(
        titleContentColor = Color(0xFF0F4C5C),
        headlineContentColor = Color(0xFF0F4C5C),
        weekdayContentColor = Color(0xFF0F4C5C),
        selectedDayContainerColor = Color(0xFF0F4C5C),
        selectedDayContentColor = Color.White,
        todayDateBorderColor = Color(0xFF0F4C5C),
        dayContentColor = Color(0xFF0F4C5C)
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK", color = Color(0xFF02584F))
            }
        },
        colors = colores,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color(0xFF02584F))
            }
        }
    ) {
        DatePicker(state = datePickerState, colors = colores)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthYearPickerField(
    titulo: String,
    modifier: Modifier = Modifier,
    onMonthYearSelected: (String) -> Unit
) {
    var selectedDateText by remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(false) }

    TextField(
        value = selectedDateText,
        onValueChange = { },
        label = { Text(text = titulo, fontSize = 20.sp) },
        readOnly = true,
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = null)
        },
        modifier = modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = outlinedTextFieldColors(
            focusedTextColor = Color.Black,
            focusedLabelColor = Color(0xFF0F4C5C),
            focusedBorderColor = Color(0xFF0F4C5C)
        )
    )

    if (showModal) {
        MonthYearPickerDialog(
            onDismiss = { showModal = false },
            onConfirm = { month, year ->
                val formatted = "${month.toString().padStart(2, '0')}/$year"
                selectedDateText = formatted
                onMonthYearSelected(formatted)
                showModal = false
            }
        )
    }
}

@Composable
fun MonthYearPickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    val calendar = java.util.Calendar.getInstance()
    var selectedMonth by remember { mutableStateOf(calendar.get(java.util.Calendar.MONTH) + 1) }
    var selectedYear by remember { mutableStateOf(calendar.get(java.util.Calendar.YEAR)) }


    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedMonth, selectedYear) }) {
                Text("Aceptar", color = Color(0xFF02584F))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color(0xFF02584F))
            }
        },
        title = { Text("Selecciona Mes y Año", color = Color(0xFF0F4C5C)) },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                NumberPicker(
                    value = selectedMonth,
                    range = 1..12,
                    label = "Mes",
                    onValueChange = { selectedMonth = it }
                )

                NumberPicker(
                    value = selectedYear,
                    range = calendar.get(java.util.Calendar.YEAR)..2070,
                    label = "Año",
                    onValueChange = { selectedYear = it }
                )
            }
        }
    )
}

@Composable
fun NumberPicker(
    value: Int,
    range: IntRange,
    label: String,
    onValueChange: (Int) -> Unit
) {
    androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        IconButton(onClick = { if (value < range.last) onValueChange(value + 1) }) {
            Icon(Icons.Default.ArrowForward , contentDescription = null)
        }
        Text(text = value.toString(), fontSize = 24.sp, color = Color(0xFF0F4C5C))
        IconButton(onClick = { if (value > range.first) onValueChange(value - 1) }) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }
    }
}