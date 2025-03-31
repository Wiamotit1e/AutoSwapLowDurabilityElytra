package wiam.wiamautoswaplowdurabilityelytra.mixin;


import com.mojang.authlib.GameProfile;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ElytraItem;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wiam.wiamautoswaplowdurabilityelytra.config.ModConfig;

@Mixin(value = ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow
    @Final
    protected MinecraftClient client;

    @Shadow public abstract void sendMessage(Text message);

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile gameProfile) {
        super(world, gameProfile);
    }


    @Unique
    public void wiam$swapSlots(int slotA, int slotB) {

        // Convert inventory slot to menu slot
        DefaultedList<Slot> slots = this.playerScreenHandler.slots;
        int slotAMenu = -1;
        int slotBMenu = -1;
        for (int i = 5; i < slots.size(); i++) {  // Start at 5 to skip crafting grid
            if (slots.get(i).getIndex() == slotA) slotAMenu = i;
            if (slots.get(i).getIndex() == slotB) slotBMenu = i;
            if (slotAMenu > -1 && slotBMenu > -1) break;
        }

        assert slotAMenu > -1;
        assert slotBMenu > -1;
        assert this.client.interactionManager != null;

        // Swap using ClickType.PICKUP as ClickType.SWAP only works in hotbar since 1.20.4: https://github.com/NimajnebEC/auto-elytra/issues/10
        this.client.interactionManager.clickSlot(this.playerScreenHandler.syncId, slotAMenu, 0, SlotActionType.PICKUP, this);
        this.client.interactionManager.clickSlot(this.playerScreenHandler.syncId, slotBMenu, 0, SlotActionType.PICKUP, this);
        this.client.interactionManager.clickSlot(this.playerScreenHandler.syncId, slotAMenu, 0, SlotActionType.PICKUP, this);
    }

    @Inject( at = @At("TAIL"), method = "tick")
    public void tickMixin(CallbackInfo ci) {

        if(!(AutoConfig.getConfigHolder(ModConfig.class).getConfig()).isAutoSwapOn) return;
        if (!(this.getInventory().armor.get(2).getItem() instanceof ElytraItem)) return;
        int maxDurability = this.getInventory().armor.get(2).getMaxDamage();
        int lowestDurabilityWhenSwap = AutoConfig.getConfigHolder(ModConfig.class).getConfig().lowestDurabilityWhenSwap;
        if(this.getInventory().armor.get(2).getDamage() <= maxDurability - lowestDurabilityWhenSwap) return;
        for (int i = 0; i < this.getInventory().main.size(); i++) {
            if((this.getInventory().main.get(i).getItem() instanceof ElytraItem) && this.getInventory().main.get(i).getDamage() < maxDurability - lowestDurabilityWhenSwap) {
                Text message = Text.translatable("message.wiamautoswaplowdurabilityelytra.success_swap").formatted(Formatting.BLUE);
                this.sendMessage(message);
                wiam$swapSlots(38, i);
                break;
            }
        }

    }
}


