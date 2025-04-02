package wiam.wiamautoswaplowdurabilityelytra.mixin;


import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wiam.wiamautoswaplowdurabilityelytra.manager.AutoSwapElytraManager;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject( at = @At("TAIL"), method = "onDisconnected")
    private void onDisconnected(CallbackInfo info) {
        AutoSwapElytraManager.isSwapProcessing = false;
    }
}
