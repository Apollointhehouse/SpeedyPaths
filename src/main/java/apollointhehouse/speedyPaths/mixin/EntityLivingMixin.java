package apollointhehouse.speedyPaths.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EntityLiving.class)
public abstract class EntityLivingMixin extends Entity {
    @Shadow(remap = false) protected float moveForward;
    @Shadow(remap = false) protected float moveStrafing;

    public EntityLivingMixin(World world) {
        super(world);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), remap = false)
    @SuppressWarnings("all")
    private void onUpdate(CallbackInfo ci) {
        if (!(((EntityLiving) (Object) this) instanceof EntityPlayer)) {
            return;
        }

        int blockX = MathHelper.floor_double(this.posX);
        int blockY = MathHelper.floor_double(this.posY - 1.8);
        int blockZ = MathHelper.floor_double(this.posZ);

        int blockID = worldObj.getBlockId(blockX, blockY, blockZ);

        boolean isMovingKey = (this.moveForward != 0 || this.moveStrafing != 0);
        boolean isPathBlock = (blockID == Block.pathDirt.blockID || blockID == Block.cobbleStone.blockID || blockID == Block.gravel.blockID);

        double MAX_SPEED = 0.2;

        double[] result = apollointhehouse.speedyPaths.SpeedRegulator.regulateSpeed(MAX_SPEED, motionX, motionZ, isPathBlock, isMovingKey);
        this.motionX = result[0];
        this.motionZ = result[1];
    }
}
