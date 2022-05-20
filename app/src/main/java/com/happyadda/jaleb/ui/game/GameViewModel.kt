package com.happyadda.jaleb.ui.game

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.happyadda.jaleb.data.game.SlotModel
import com.happyadda.jaleb.utils.SCORE_TO_WON
import com.happyadda.jaleb.utils.enums.SlotType
import kotlin.random.Random

class GameViewModel : ViewModel() {
    var score: Int by mutableStateOf(0)
        private set

    private var rollStarted = false

    val slots = mutableStateListOf(
        SlotModel(SlotType.FIRST),
        SlotModel(SlotType.SECOND),
        SlotModel(SlotType.THIRD),
        SlotModel(SlotType.FOURTH)
    )

    val gameEnd = mutableStateOf(false)


    fun roll() {
        if (!rollStarted) {
            object : CountDownTimer(5000, 50) {
                override fun onTick(millisUntilFinished: Long) {
                    slots.forEach {
                        it.type = randSlotType()
                    }
                    slots.shuffle()
                }

                override fun onFinish() {
                    rollStarted = false
                    processResults()
                }
            }.start()
            rollStarted = true
        }
    }

    fun randSlotType(): SlotType {
        val type = when (Random.nextInt(16)) {
            in 1..4 -> SlotType.FIRST
            in 5..8 -> SlotType.SECOND
            in 9..12 -> SlotType.THIRD
            in 13..16 -> SlotType.FOURTH
            else -> SlotType.FIRST
        }
        return type
    }

    fun processResults() {
        val resultList = slots.toMutableList()
        var maxType = SlotType.FIRST
        resultList.sortBy { it.type.ordinal }
        resultList.forEach { current ->

            var matchesType = 1
            resultList.forEach { searchable ->
                if (current.type == searchable.type) {
                    matchesType++
                    if (matchesType >= 3) {
                        maxType = current.type
                    }
                }
            }
        }

        when (resultList.count { it.type == maxType }) {
            1 -> decreaseScore()
            2 -> increaseScore(25)
            3 -> increaseScore(50)
            4 -> increaseScore(100)
        }
    }

    private fun increaseScore(value: Int) {
        score += value

        if (score >= SCORE_TO_WON) {
            gameEnd.value = true
        }
    }

    private fun decreaseScore() {
        score = if (score <= 50) 0 else score.minus(50)
    }
}