package com.maddog.d20roller.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun D20Screen() {
    var rollHistory by remember { mutableStateOf<List<D20RollResult>>(emptyList()) }
    var abilityScores by remember { mutableStateOf<List<AbilityRoll>>(emptyList()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("D20 Roller") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            D20RollSection(
                onRollComplete = { result ->
                    rollHistory = (listOf(result) + rollHistory).take(20)
                }
            )

            HorizontalDivider()

            AbilityScoresSection(
                scores = abilityScores,
                onRollAll = { newScores ->
                    abilityScores = newScores
                },
                onReRoll = { index ->
                    abilityScores = abilityScores.toMutableList().apply {
                        this[index] = rollAbilityScore()
                    }
                }
            )

            if (rollHistory.isNotEmpty()) {
                HorizontalDivider()
                HistorySection(history = rollHistory)
            }
        }
    }
}
