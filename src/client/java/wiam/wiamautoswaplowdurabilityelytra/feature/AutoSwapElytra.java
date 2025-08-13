package wiam.wiamautoswaplowdurabilityelytra.feature;


import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import wiam.wiamautoswaplowdurabilityelytra.config.ModConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class AutoSwapElytra {

    public volatile static boolean isSwapProcessing = false;

    public volatile static int randomDurability = 0;

    public static Random rand = new Random();

    private static void swapSlots(int slotA, int slotB, ClientPlayerEntity player) {

        isSwapProcessing = true;
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
                MinecraftClient.getInstance().execute(() -> {
                    Text message = Text.translatable("message.wiamautoswaplowdurabilityelytra.success_swap").formatted(Formatting.BLUE);
                    player.sendMessage(message , false);
                });
            }
        }).start();

    }

    //感谢 D 老师开源
    private static int getRemainingDurability(ItemStack stack) {
        if (stack == null) return 0;
        int max = stack.getMaxDamage();       // 最大耐久值
        int used = stack.getDamage();         // 已损耗值
        return max - used;                   // 剩余耐久值
    }

    public static void execute() {
        if (!check()) return;

        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        // D 老师的神奇修复
        List<Integer> validSlots = new ArrayList<>();
        for (int i = 0; i < player.getInventory().getMainStacks().size(); i++) {
            ItemStack stack = player.getInventory().getMainStacks().get(i);
            if (stack.getItem() != Items.ELYTRA) continue;

            int stackRemaining = getRemainingDurability(stack);

            if (stackRemaining > AutoConfig.getConfigHolder(ModConfig.class).getConfig().lowestDurabilityWhenSwap + AutoConfig.getConfigHolder(ModConfig.class).getConfig().swapRandomDurability) {
                validSlots.add(i);
            }
        }

        if (!validSlots.isEmpty()) {
            int swapSlot = validSlots.get(rand.nextInt(validSlots.size()));
            swapSlots(swapSlot, 38, player); // 38=胸甲槽
        }
    }

    private static boolean check(){
        if (MinecraftClient.getInstance().player == null) return false;
        if(!(AutoConfig.getConfigHolder(ModConfig.class).getConfig()).isAutoSwapOn) return false;
        if (!(MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA)) return false;
        if(isSwapProcessing) return false;
        // D 老师的神奇修复
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ItemStack chestStack = player.getEquippedStack(EquipmentSlot.CHEST);
        int remaining = getRemainingDurability(chestStack);
        int threshold = AutoConfig.getConfigHolder(ModConfig.class).getConfig().lowestDurabilityWhenSwap;
        return (remaining <= threshold + randomDurability);
    }
}

