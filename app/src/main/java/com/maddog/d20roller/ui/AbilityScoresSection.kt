package com.maddog.d20roller.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maddog.d20roller.R
import kotlin.random.Random

@Composable
fun AbilityScoresSection(
    scores: List<AbilityRoll>,
    onRollAll: (List<AbilityRoll>) -> Unit,
    onReRoll: (Int) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {
                val newScores = List(6) { rollAbilityScore() }
                onRollAll(newScores)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.roll_ability_scores))
        }

        if (scores.isNotEmpty()) {
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    scores.forEachIndexed { index, roll ->
                        AbilityScoreRow(
                            roll = roll,
                            onReRoll = { onReRoll(index) }
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Button(
                        onClick = {
                            val csv = scores.joinToString(",") { it.total.toString() }
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Ability Scores", csv)
                            clipboard.setPrimaryClip(clip)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.copy_scores))
                    }
                }
            }
        }
    }
}

@Composable
fun AbilityScoreRow(
    roll: AbilityRoll,
    onReRoll: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            roll.dice.forEachIndexed { index, die ->
                val isDropped = index == roll.droppedIndex
                Text(
                    text = die.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (isDropped) FontWeight.Normal else FontWeight.Bold
                    ),
                    color = if (isDropped) {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
            Text(
                text = " = ${roll.total}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = onReRoll) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = "Re-roll",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun rollAbilityScore(): AbilityRoll {
    val dice = List(4) { Random.nextInt(1, 7) }
    val minIndex = dice.indices.minByOrNull { dice[it] } ?: 0
    val total = dice.filterIndexed { index, _ -> index != minIndex }.sum()
    return AbilityRoll(dice, minIndex, total)
}
