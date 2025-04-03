package wiam.wiamautoswaplowdurabilityelytra.mixin;


import com.mojang.authlib.GameProfile;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ElytraItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wiam.wiamautoswaplowdurabilityelytra.config.ModConfig;
import wiam.wiamautoswaplowdurabilityelytra.feature.AutoSwapElytra;

@Mixin(value = ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient client;

    @Shadow public abstract void sendMessage(Text message);

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    @Inject( at = @At("TAIL"), method = "tick")
    public void autoSwapElytra(CallbackInfo ci) {
        AutoSwapElytra.execute();
    }

    @Inject( at = @At("TAIL"), method = "tick")
    public void logOffWhenElytraNotGood(CallbackInfo ci) {
        if(!(AutoConfig.getConfigHolder(ModConfig.class).getConfig()).isAutoLogOutOn) return;
        if (!(this.getInventory().armor.get(2).getItem() instanceof ElytraItem)) return;
        int maxDurability = this.getInventory().armor.get(2).getMaxDamage();
        int lowestDurabilityWhenLogOut = AutoConfig.getConfigHolder(ModConfig.class).getConfig().lowestDurabilityWhenLogOut;
        if(this.getInventory().armor.get(2).getDamage() <= maxDurability - lowestDurabilityWhenLogOut) return;
        wiam$executeAutoLogOff();
    }

    @Inject( at = @At("TAIL"), method = "tick")
    public void logOffWnenYCoordinateTooLow(CallbackInfo ci) {
        if(!(AutoConfig.getConfigHolder(ModConfig.class).getConfig()).isAutoLogOutOn) return;
        if(this.getY() >= AutoConfig.getConfigHolder(ModConfig.class).getConfig().lowestYCoordinateWhenLogOut) return;
        wiam$executeAutoLogOff();
    }

    @Unique
    private void wiam$executeAutoLogOff() {
        ConfigHolder<ModConfig> configHolder = AutoConfig.getConfigHolder(ModConfig.class);
        configHolder.getConfig().isAutoLogOutOn = false;
        configHolder.save();
        client.execute(() ->{
            if(client.getNetworkHandler() == null) return;
            client.getNetworkHandler().getConnection().disconnect(Text.translatable("message.wiamautoswaplowdurabilityelytra.disconnect").formatted(Formatting.BLUE, Formatting.BOLD));
            client.setScreen(new TitleScreen());
        });
    }
}


