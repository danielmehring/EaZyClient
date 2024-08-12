package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AutoPotion extends Module {

public static AutoPotion mod;


    public static final int EaZy = 95;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private int delay;
    private static int potions;
    private String suffix;

    public AutoPotion() {
        super(new String(new byte[]{0b1000001, 0b1110101, 0b1110100, 0b1101111, 0b1010000, 0b1101111, 0b1110100,
            0b1101001, 0b1101111, 0b1101110}), 79, "pot",
                Category.COMBAT,
                new String(new byte[]{0b1000001, 0b1110101, 0b1110100, 0b1101111, 0b1101101, 0b1100001, 0b1110100,
                    0b1101001, 0b1100011, 0b1100001, 0b1101100, 0b1101100, 0b1111001, 0b100000, 0b1101000,
                    0b1100101, 0b1100001, 0b1101100, 0b1110011, 0b100000, 0b1111001, 0b1101111, 0b1110101})
                + "\n"
                + new String(new byte[]{0b1110111, 0b1101001, 0b1110100, 0b1101000, 0b100000, 0b1101000,
                    0b1100101, 0b1100001, 0b1101100, 0b101101, 0b1110000, 0b1101111, 0b1110100, 0b1101001,
                    0b1101111, 0b1101110, 0b1110011, 0b101110}));
        mod = this;
    }

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                if (Minecraft.thePlayer == null) {
                    suffix = new String(new byte[]{0b1000001, 0b1110101, 0b1110100, 0b1101111, 0b1010000, 0b1101111,
                        0b1110100, 0b1101001, 0b1101111, 0b1101110});
                } else {
                    suffix = new String(new byte[]{0b1000001, 0b1110101, 0b1110100, 0b1101111, 0b1010000, 0b1101111,
                        0b1110100, 0b1101001, 0b1101111, 0b1101110, 0b100000, 0b1011011})
                            + getTotalPots() + new String(new byte[]{0b1011101});
                }
            } else {
                suffix = "AutomatischeGetränke";
            }

            if (Minecraft.thePlayer == null) {
                return suffix;
            }
            return suffix;
        } else {

            if (Configs.suffix) {
                if (Minecraft.thePlayer == null) {
                    suffix = new String(new byte[]{0b1000001, 0b1110101, 0b1110100, 0b1101111, 0b1010000, 0b1101111,
                        0b1110100, 0b1101001, 0b1101111, 0b1101110});
                } else {
                    suffix = new String(new byte[]{0b1000001, 0b1110101, 0b1110100, 0b1101111, 0b1010000, 0b1101111,
                        0b1110100, 0b1101001, 0b1101111, 0b1101110, 0b100000, 0b1011011})
                            + getTotalPots() + new String(new byte[]{0b1011101});
                }
            } else {
                suffix = "AutoPotion";
            }

            return suffix;
        }
    }

    @Override
    public void onUpdate() {
        if (!isToggled()) {
            if (togglecmd) {
                setToggled(true);
                onEnable();
            }
            return;
        }
        if (isToggled() && !togglecmd) {
            setToggled(false);
            onDisable();
            return;
        }
        if (Minecraft.thePlayer == null) {
            return;
        }
        if (shouldHeal()) {
            ++delay;
        }
        if (isPotionOnHotbar() && shouldHeal() && delay >= 5) {
            throwPotion();
            delay = 0;
        } else if (!isPotionOnHotbar() && shouldHeal() && getTotalPots() > 0) {
            getPotion();
        }
        super.onUpdate();
    }

    private boolean isStackPotion(final ItemStack stack, final int index) {
        ItemPotion potion;
        if (stack == null) {
            return false;
        }
        final ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(index).getStack();
        final Item item = is.getItem();
        if (stack.getItem() instanceof ItemPotion && (potion = (ItemPotion) item).getEffects(is) != null) {
            for (final Object o : potion.getEffects(is)) {
                final PotionEffect effect = (PotionEffect) o;
                if (effect.getPotionID() != Potion.heal.id || !ItemPotion.isSplash(is.getItemDamage())) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    private boolean shouldHeal() {
        return Minecraft.thePlayer.getHealth() <= 12.0f;
    }

    public boolean isPotionOnHotbar() {
        int index = 36;
        while (index < 45) {
            final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && isStackPotion(stack, index)) {
                return true;
            }
            ++index;
        }
        return false;
    }

    private void getPotion() {
        if (!(Minecraft.currentScreen instanceof GuiChest)) {
            int index = 9;
            while (index < 36) {
                final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(index).getStack();
                if (!isStackPotion(stack, index)) {
                    dropLastSlot();
                }
                if (stack != null && isStackPotion(stack, index)) {
                    Minecraft.playerController.windowClick(0, index, 0, 1, Minecraft.thePlayer);
                    break;
                }
                ++index;
            }
        }
    }

    private void dropLastSlot() {
        int i1 = 0;
        while (i1 < 45) {
            final ItemStack itemStack = Minecraft.thePlayer.inventoryContainer.getSlot(i1).getStack();
            if (itemStack != null && i1 > 43 && i1 <= 44) {
                try {
                    Minecraft.playerController.windowClick(0, i1, 0, 0, Minecraft.thePlayer);
                    Minecraft.playerController.windowClick(0, 45, 0, 0, Minecraft.thePlayer);
                    break;
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            ++i1;
        }
    }

    private void throwPotion() {
        int slot = 36;
        while (slot < 45) {
            final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(slot).getStack();
            if (stack != null && isStackPotion(stack, slot)) {
                final int lastSlot = Minecraft.thePlayer.inventory.currentItem;
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(
                        Minecraft.thePlayer.rotationYaw, 90.0f, Minecraft.thePlayer.onGround));
                Minecraft.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(slot - 36));
                Minecraft.playerController.updateController();
                Minecraft.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(stack));
                Minecraft.playerController.windowClick(0, slot, 0, 1, Minecraft.thePlayer);
                Minecraft.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(lastSlot));
                break;
            }
            ++slot;
        }
        updateMS();
        if (hasTimePassedM(80)) {
            updateLastMS();
        }
    }

    public int getTotalPots() {
        potions = 0;
        int index = 9;
        while (index < 45) {
            final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (isStackPotion(stack, index)) {
                potions += stack.stackSize;
            }
            ++index;
        }
        return potions;
    }
}
