package com.maddog.d20roller.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maddog.d20roller.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistorySection(history: List<D20RollResult>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.history),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                history.forEach { roll ->
                    HistoryItem(roll)
                }
            }
        }
    }
}

@Composable
fun HistoryItem(roll: D20RollResult) {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val timeString = timeFormat.format(Date(roll.timestamp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = timeString,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (roll.die2 != null) {
                Text(
                    text = "${roll.die1}, ${roll.die2}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = roll.result.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            when (roll.mode) {
                RollMode.ADVANTAGE -> Text("(A)", style = MaterialTheme.typography.bodySmall)
                RollMode.DISADVANTAGE -> Text("(D)", style = MaterialTheme.typography.bodySmall)
                else -> {}
            }
        }
    }
}
