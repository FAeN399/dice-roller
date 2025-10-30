package com.maddog.d20roller.ui

data class D20RollResult(
    val result: Int,
    val die1: Int,
    val die2: Int? = null,
    val mode: RollMode,
    val timestamp: Long = System.currentTimeMillis()
)

enum class RollMode {
    NORMAL, ADVANTAGE, DISADVANTAGE
}

data class AbilityRoll(
    val dice: List<Int>,
    val droppedIndex: Int,
    val total: Int
)
