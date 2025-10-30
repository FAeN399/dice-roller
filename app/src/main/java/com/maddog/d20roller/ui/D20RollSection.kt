package com.maddog.d20roller.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maddog.d20roller.R
import kotlin.random.Random

@Composable
fun D20RollSection(
    onRollComplete: (D20RollResult) -> Unit
) {
    var rollResult by remember { mutableStateOf<D20RollResult?>(null) }
    var advantageEnabled by remember { mutableStateOf(false) }
    var disadvantageEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = advantageEnabled,
                onClick = {
                    advantageEnabled = !advantageEnabled
                    if (advantageEnabled) disadvantageEnabled = false
                },
                label = { Text(stringResource(R.string.advantage)) }
            )
            FilterChip(
                selected = disadvantageEnabled,
                onClick = {
                    disadvantageEnabled = !disadvantageEnabled
                    if (disadvantageEnabled) advantageEnabled = false
                },
                label = { Text(stringResource(R.string.disadvantage)) }
            )
        }

        Button(
            onClick = {
                val mode = when {
                    advantageEnabled -> RollMode.ADVANTAGE
                    disadvantageEnabled -> RollMode.DISADVANTAGE
                    else -> RollMode.NORMAL
                }
                val result = rollD20(mode)
                rollResult = result
                onRollComplete(result)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text(
                text = stringResource(R.string.roll_d20),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        AnimatedVisibility(visible = rollResult != null) {
            rollResult?.let { result ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = result.result.toString(),
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        if (result.die2 != null) {
                            Text(
                                text = "Rolled: ${result.die1}, ${result.die2}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = when (result.mode) {
                                    RollMode.ADVANTAGE -> "Kept higher"
                                    RollMode.DISADVANTAGE -> "Kept lower"
                                    else -> ""
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

fun rollD20(mode: RollMode): D20RollResult {
    val die1 = Random.nextInt(1, 21)
    return when (mode) {
        RollMode.NORMAL -> D20RollResult(die1, die1, null, mode)
        RollMode.ADVANTAGE -> {
            val die2 = Random.nextInt(1, 21)
            val result = maxOf(die1, die2)
            D20RollResult(result, die1, die2, mode)
        }
        RollMode.DISADVANTAGE -> {
            val die2 = Random.nextInt(1, 21)
            val result = minOf(die1, die2)
            D20RollResult(result, die1, die2, mode)
        }
    }
}
