package wiam.wiamautoswaplowadurabilityelytra.mixin;


import com.mojang.authlib.GameProfile;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ElytraItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wiam.wiamautoswaplowadurabilityelytra.config.ModConfig;

@Mixin(value = LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    @Shadow
    @Final
    protected Minecraft minecraft;

    @Shadow public abstract void sendSystemMessage(Component component);

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }


    //From: https://github.com/brooke-ec/auto-elytra
    @Unique
    public void wiam$swapSlots(int slotA, int slotB) {

        // Convert inventory slot to menu slot
        NonNullList<Slot> slots = this.inventoryMenu.slots;
        int slotAMenu = -1;
        int slotBMenu = -1;
        for (int i = 5; i < slots.size(); i++) {  // Start at 5 to skip crafting grid
            if (slots.get(i).getContainerSlot() == slotA) slotAMenu = i;
            if (slots.get(i).getContainerSlot() == slotB) slotBMenu = i;
            if (slotAMenu > -1 && slotBMenu > -1) break;
        }

        assert slotAMenu > -1;
        assert slotBMenu > -1;
        assert this.minecraft.gameMode != null;

        // Swap using ClickType.PICKUP as ClickType.SWAP only works in hotbar since 1.20.4: https://github.com/NimajnebEC/auto-elytra/issues/10
        this.minecraft.gameMode.handleInventoryMouseClick(this.inventoryMenu.containerId, slotAMenu, 0, ClickType.PICKUP, this);
        this.minecraft.gameMode.handleInventoryMouseClick(this.inventoryMenu.containerId, slotBMenu, 0, ClickType.PICKUP, this);
        this.minecraft.gameMode.handleInventoryMouseClick(this.inventoryMenu.containerId, slotAMenu, 0, ClickType.PICKUP, this);
    }

    @Inject( at = @At("TAIL"), method = "tick")
    public void tickMixin(CallbackInfo ci) {
        if(!(AutoConfig.getConfigHolder(ModConfig.class).getConfig()).isAutoSwapOn) return;
        if (!(this.getInventory().armor.get(2).getItem() instanceof ElytraItem)) return;
        int maxDurability = this.getInventory().armor.get(2).getMaxDamage();
        int lowestDurabilityWhenSwap = AutoConfig.getConfigHolder(ModConfig.class).getConfig().lowestDurabilityWhenSwap;
        if(this.getInventory().armor.get(2).getDamageValue() <= maxDurability - lowestDurabilityWhenSwap) return;
        for (int i = 0; i < this.getInventory().items.size(); i++) {
            if((this.getInventory().items.get(i).getItem() instanceof ElytraItem) && this.getInventory().items.get(i).getDamageValue() < maxDurability - lowestDurabilityWhenSwap) {
                Component message = Component.translatable("message.wiamautoswaplowadurabilityelytra.success_swap").withStyle(ChatFormatting.BLUE);
                this.sendSystemMessage(message);
                wiam$swapSlots(38, i);
                break;
            }
        }
    }
}


