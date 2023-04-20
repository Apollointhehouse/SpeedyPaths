package apollointhehouse.SpeedyPaths.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class SpeedyPathsMixin {

    // This method is called when an entity walks on a block
    @Inject(method = "onEntityWalking", at = @At("HEAD"), remap = false)
    public void onEntityWalking(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {

        // Get the block position and check if it is a path block (either cobblestone or dirt)
        int blockX = MathHelper.floor_double(entity.posX);
        int blockY = MathHelper.floor_double(entity.posY - 0.20000000298023224D - (double)entity.yOffset);
        int blockZ = MathHelper.floor_double(entity.posZ);
        boolean isPathBlock = world.getBlockId(blockX, blockY, blockZ) == Block.cobbleStone.blockID ||
                world.getBlockId(blockX, blockY, blockZ) == Block.pathDirt.blockID;

        // Check if the entity is moving
        boolean isMoving = entity.motionX != 0 && entity.motionZ != 0;

        // Apply speed boost if the entity is on a path block and moving
        if (isPathBlock && isMoving) {

            // Calculate the new speed and limit it to 1 block per tick
            double newSpeedX = entity.motionX * 3.0; // Triple the entity's speed
            double newSpeedZ = entity.motionZ * 3.0;
            double newSpeed = Math.sqrt(newSpeedX * newSpeedX + newSpeedZ * newSpeedZ); // Calculate the new speed
            double speedLimit = 1.0; // Set the speed limit to 1 block per tick

            // If the new speed exceeds the limit, normalize the speed vector and multiply it by the limit
            if (newSpeed > speedLimit) {
                double normalizationFactor = speedLimit / newSpeed;
                entity.motionX *= normalizationFactor;
                entity.motionZ *= normalizationFactor;
            }
            // Otherwise, set the entity's new speed
            else {
                entity.motionX = newSpeedX;
                entity.motionZ = newSpeedZ;
            }
        }
    }
}
