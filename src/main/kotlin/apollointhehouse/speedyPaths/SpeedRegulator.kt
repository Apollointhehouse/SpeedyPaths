package apollointhehouse.speedyPaths

import net.minecraft.client.Minecraft
import net.minecraft.src.Block
import net.minecraft.src.MathHelper
import kotlin.math.sqrt

object SpeedRegulator {
    @JvmStatic
    fun regulateSpeed() {
        val mc = Minecraft.getMinecraft()
        val player = mc.thePlayer
        val world = mc.theWorld

        val blockX = MathHelper.floor_double(player.posX)
        val blockY = MathHelper.floor_double(player.posY - 1.8)
        val blockZ = MathHelper.floor_double(player.posZ)
        val blockID: Int = world.getBlockId(blockX, blockY, blockZ)

        val isMovingKey = player.movementInput.moveForward != 0f || player.movementInput.moveStrafe != 0f
        val isPathBlock = blockID == Block.pathDirt.blockID || blockID == Block.cobbleStone.blockID || blockID == Block.gravel.blockID
        val isSingleplayer: Boolean = !world.isMultiplayerAndNotHost
        val isMoving = player.motionX + player.motionZ != 0.0
        val speed = sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ)

        if (!isPathBlock || !isMovingKey || !isSingleplayer || !isMoving) {
            return
        }

        val diff = 0.6 - speed
        val acceleration = diff / 10

        player.motionX = player.motionX + acceleration * player.motionX / speed
        player.motionZ = player.motionZ + acceleration * player.motionZ / speed

        if (speed > 0.6) {
            player.motionX = player.motionX * 0.6 / speed
            player.motionZ = player.motionZ * 0.6 / speed
        }
    }
}
