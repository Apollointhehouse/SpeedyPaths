package apollointhehouse.SpeedyPaths.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class SpeedyPathsMixin {
    @Inject(method = "onEntityWalking", at = @At("HEAD"), remap = false)
    public void onEntityWalking(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {

        int blockX = MathHelper.floor_double(entity.posX);
        int blockY = MathHelper.floor_double(entity.posY - 0.20000000298023224D - (double)entity.yOffset);
        int blockZ = MathHelper.floor_double(entity.posZ);

        if (world.getBlockId(blockX, blockY, blockZ) == Block.cobbleStone.blockID && entity.motionX != 0 && entity.motionZ != 0) {
            double speedLimit = 1; // Set the speed limit to 1 blocks per tick
            double newSpeedX = entity.motionX * 3.0; // Triple the entity's speed
            double newSpeedZ = entity.motionZ * 3.0;
            double newSpeed = Math.sqrt(newSpeedX * newSpeedX + newSpeedZ * newSpeedZ); // Calculate the new speed
            if (newSpeed > speedLimit) {
                // If the new speed exceeds the limit, normalize the speed vector and multiply it by the limit
                double normalizationFactor = speedLimit / newSpeed;
                entity.motionX *= normalizationFactor;
                entity.motionZ *= normalizationFactor;
            } else {
                // Otherwise, set the entity's new speed
                entity.motionX = newSpeedX;
                entity.motionZ = newSpeedZ;
            }
        }
    }
}
