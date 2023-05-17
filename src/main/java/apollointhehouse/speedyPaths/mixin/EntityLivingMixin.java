package apollointhehouse.speedyPaths.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.src.World;

import static apollointhehouse.speedyPaths.SpeedRegulator.regulateSpeed;


@Mixin(EntityLiving.class)
public abstract class EntityLivingMixin extends Entity {

    public EntityLivingMixin(World world) {
        super(world);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), remap = false)
    @SuppressWarnings("all")
    private void onUpdate(CallbackInfo ci) {
        if (!(((EntityLiving) (Object) this) instanceof EntityPlayer)) {
            return;
        }
        regulateSpeed();
    }
}
