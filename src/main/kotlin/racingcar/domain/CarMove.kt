package racingcar.domain

class CarMove(
    private val random: NumberGenerator<Int>
) {
    companion object {
        const val CAR_RACING_RANDOM_START = 0
        const val CAR_RACING_RANDOM_END = 9
        private const val CAR_MOVE_FORWARD_CONDITION = 4
    }

    fun canMoveForward(): Boolean = CAR_MOVE_FORWARD_CONDITION <= random.apply(CAR_RACING_RANDOM_START, CAR_RACING_RANDOM_END)
}
