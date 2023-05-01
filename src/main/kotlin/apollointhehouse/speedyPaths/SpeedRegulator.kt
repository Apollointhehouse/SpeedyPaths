package apollointhehouse.speedyPaths

import kotlin.math.sqrt

object SpeedRegulator {
    @JvmStatic
    fun regulateSpeed(maxSpeed: Double, motionX: Double, motionZ: Double, isPathBlock: Boolean, isMovingKey: Boolean): DoubleArray {
        if (!isPathBlock || !isMovingKey) {
            return doubleArrayOf(motionX, motionZ)
        }

        val targetSpeed = maxSpeed * 3

        val speed = sqrt(motionX * motionX + motionZ * motionZ)

        val diff = targetSpeed - speed
        val acceleration = diff / 10

        var newMotionX = motionX + acceleration * motionX / speed
        var newMotionZ = motionZ + acceleration * motionZ / speed

        if (speed > maxSpeed) {
            newMotionX = newMotionX * maxSpeed / speed
            newMotionZ = newMotionZ * maxSpeed / speed
        }

        return doubleArrayOf(newMotionX, newMotionZ)
    }
}
