package wiam.wiamautoswaplowdurabilityelytra.manager;


import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wiam.wiamautoswaplowdurabilityelytra.config.ModConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutoSwapElytraManager {

    public volatile static boolean isSwapProcessing = false;

    public volatile static int randomDurability = 0;

    public static Random rand = new Random();

    private static void swapSlots(int slotA, int slotB, ClientPlayerEntity player) {

        // Convert inventory slot to menu slot
        DefaultedList<Slot> slots = player.playerScreenHandler.slots;
        int slotAMenu = -1;
        int slotBMenu = -1;
        for (int i = 5; i < slots.size(); i++) {  // Start at 5 to skip crafting grid
            if (slots.get(i).getIndex() == slotA) slotAMenu = i;
            if (slots.get(i).getIndex() == slotB) slotBMenu = i;
            if (slotAMenu > -1 && slotBMenu > -1) break;
        }

        // Swap using ClickType.PICKUP as ClickType.SWAP only works in hotbar since 1.20.4: https://github.com/NimajnebEC/auto-elytra/issues/10
        if(MinecraftClient.getInstance().interactionManager == null) return;
        int finalSlotAMenu = slotAMenu;
        int finalSlotBMenu = slotBMenu;
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        new Thread(() -> {
            isSwapProcessing = true;
            try {
                int delay1 = rand.nextInt(config.swapRandomMaxMillisecond - config.swapRandomMinMillisecond) + config.swapRandomMinMillisecond;
                int delay2 = rand.nextInt(config.swapRandomMaxMillisecond - config.swapRandomMinMillisecond) + config.swapRandomMinMillisecond;
                MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().interactionManager.clickSlot(player.playerScreenHandler.syncId, finalSlotAMenu, 0, SlotActionType.PICKUP, player));
                Thread.sleep(delay1);
                MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().interactionManager.clickSlot(player.playerScreenHandler.syncId, finalSlotBMenu, 0, SlotActionType.PICKUP, player));
                Thread.sleep(delay2);
                MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().interactionManager.clickSlot(player.playerScreenHandler.syncId, finalSlotAMenu, 0, SlotActionType.PICKUP, player));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                isSwapProcessing = false;
                randomDurability = rand.nextInt(config.swapRandomDurability);
                Text message = Text.translatable("message.wiamautoswaplowdurabilityelytra.success_swap").formatted(Formatting.BLUE);
                player.sendMessage(message);
            }
        }).start();

    }

    private static int getLowestDurability(){
        if(MinecraftClient.getInstance().player == null) return 0;
        int maxDurability = MinecraftClient.getInstance().player.getInventory().armor.get(2).getMaxDamage();
        int lowestDurabilityWhenSwap = AutoConfig.getConfigHolder(ModConfig.class).getConfig().lowestDurabilityWhenSwap;
        return maxDurability - lowestDurabilityWhenSwap - randomDurability;
    }


    public static void execute(CallbackInfo ci) {
        if(!check()) return;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < player.getInventory().main.size(); i++)
            if ((player.getInventory().main.get(i).getItem() instanceof ElytraItem) && player.getInventory().main.get(i).getDamage() < getLowestDurability()) list.add(i);
        if(list.isEmpty()) return;
        swapSlots(list.get(rand.nextInt(list.size())), 38, player);
    }

    private static boolean check(){
        assert MinecraftClient.getInstance().player != null;
        if(!(AutoConfig.getConfigHolder(ModConfig.class).getConfig()).isAutoSwapOn) return false;
        if (!(MinecraftClient.getInstance().player.getInventory().armor.get(2).getItem() instanceof ElytraItem)) return false;
        if(MinecraftClient.getInstance().player.getInventory().armor.get(2).getDamage() <= getLowestDurability()) return false;
        if(isSwapProcessing) return false;
        return true;
    }
}
