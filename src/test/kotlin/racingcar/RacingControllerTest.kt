package racingcar

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import racingcar.domain.NameValidation
import racingcar.domain.RacingProperty
import racingcar.domain.RacingResult
import racingcar.domain.RacingResult.*
import racingcar.domain.RacingRule
import java.lang.IllegalArgumentException

class RacingControllerTest : BehaviorSpec({
    val intRandomGenerator = { start: Int , end: Int -> IntRange(start, end).random() }
    val isNotNameLengthOver = { names: List<String> -> names.all { it.length <= NameValidation.NAME_MAX_LENGTH }}
    val alwaysForward = RacingProperty(5,9, Racing.MOVE_FORWARD_CONDITION)
    val cantMove = RacingProperty(0,3, Racing.MOVE_FORWARD_CONDITION)

    Given("자동차 이름과 시도 횟수, 경기 규칙을 준비하여") {
        val nameOfCars = "aaa,bbb,ccc"
        val numberOfTrials = 5
        When("자동차 경주를 시작하면") {
            val racingRule = RacingRule(
                intRandomGenerator,
                isNotNameLengthOver,
                alwaysForward
            )
            val result = RacingController(nameOfCars, numberOfTrials).start(racingRule)
            Then("경기 결과를 반환하며 우승자를 확인할 수 있다.") {
                result.getRacingWinners() shouldBe listOf(
                    RacingHistory("aaa", 5),
                    RacingHistory("bbb", 5),
                    RacingHistory("ccc", 5)
                )

                result.allRounds.size shouldBe 5
            }
        }
    }

    Given("자동차의 이름이 5글자를 초과하는 경우") {
        val nameOfCars = "aaaaaaa,b,c"
        val numberOfTrials = 5
        When("자동차 경주를 시작하면") {
            val racingRule = RacingRule(
                intRandomGenerator,
                isNotNameLengthOver,
                alwaysForward
            )
            Then("예외를 던진다.") {
                shouldThrow<IllegalArgumentException> {
                    RacingController(nameOfCars, numberOfTrials).start(racingRule)
                }
            }
        }
    }
})