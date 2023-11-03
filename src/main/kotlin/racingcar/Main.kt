package racingcar

import racingcar.domain.MovingCondition
import racingcar.domain.RacingPlayers
import racingcar.domain.RacingPlayersValidation
import racingcar.domain.RacingRule
import racingcar.domain.RacingStadium
import racingcar.domain.RandomRacingRule
import racingcar.util.Message
import racingcar.view.ResultView
import racingcar.view.View

object Racing {
    const val RANDOM_START = 0
    const val RANDOM_END = 9
    const val MOVE_FORWARD_CONDITION = 4
}

fun main() {
    val nameOfCars = View.printAndGetLine(Message.NAME_OF_CARS_INPUT, Message.INPUT_EXCEPTION)
    val numberOfTrials = View.printAndGetLineToInt(Message.NUMBER_OF_TRIALS_INPUT, Message.INPUT_NUMBER_EXCEPTION)
    val nameLengthIsLessThan = RacingPlayersValidation { it -> it.names.all { RacingPlayersValidation.NAME_MAX_LENGTH > it.length } }
    val players = RacingPlayers(
        nameOfCars = nameOfCars,
        numberOfTrials = numberOfTrials,
        validation = nameLengthIsLessThan
    )

    val intRandomGenerator = { start: Int, end: Int -> IntRange(start, end).random() }
    val property = MovingCondition(Racing.RANDOM_START, Racing.RANDOM_END, Racing.MOVE_FORWARD_CONDITION)
    val randomRacingRule: RacingRule = RandomRacingRule(intRandomGenerator, property)

    val result = RacingStadium(players, randomRacingRule).gameStart()
    ResultView.racingResultPrint(result)
}
