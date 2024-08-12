package me.EaZy.client.modules;

import java.util.ArrayList;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.InventoryUtil;
import me.EaZy.client.utils.TimeHelper2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class InvCleaner extends Module {

public static InvCleaner mod;


    public static final int EaZy = 128;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public InvCleaner() {
        super("InvCleaner", 0, "", Category.COMBAT);
        Client.setmgr.rSetting(new Setting("Delay", this, 50, 0, 100, true));
        Client.setmgr.rSetting(new Setting("OnlyInventory", this, false));
        Client.setmgr.rSetting(new Setting("Armor", this, true));
        Client.setmgr.rSetting(new Setting("Swords", this, true));
        Client.setmgr.rSetting(new Setting("Unstackables", this, true));
        Client.setmgr.rSetting(new Setting("Mostly Unused", this, true));
        unusedItems.add(Item.getItemById(287));
        unusedItems.add(Item.getItemById(50));
        unusedItems.add(Item.getItemById(69));
        unusedItems.add(Item.getItemById(352));
        unusedItems.add(Item.getItemById(367));
        unusedItems.add(Item.getItemById(76));
        unusedItems.add(Item.getItemById(344));
        unusedItems.add(Item.getItemById(332));
        unusedItems.add(Item.getItemById(263));
        unusedItems.add(Item.getItemById(81));
        unusedItems.add(Item.getItemById(397));
        mod = this;
    }

    private final TimeHelper2 timer = new TimeHelper2();
    private int openDelay = 0;

    private final ArrayList<Item> unusedItems = new ArrayList<>();

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "InventarPutzmittel";
        } else {
            return super.getRenderName();
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

        if (Minecraft.currentScreen == null) {
            openDelay = 0;
        }

        if (openDelay < 5 && Client.setmgr.getSettingByName(this, "OnlyInventory").getValBoolean()
                && Minecraft.currentScreen instanceof GuiInventory) {
            openDelay++;
            return;
        }

        if (Client.setmgr.getSettingByName(this, "OnlyInventory").getValBoolean()
                ? Minecraft.currentScreen instanceof GuiInventory
                : Minecraft.currentScreen == null || Minecraft.currentScreen instanceof GuiInventory
                || Minecraft.currentScreen instanceof ClickGUI) {

            // 0 = helmet, 1 = chest, 2 = leggins, 3 = boots
            final int[] armor = new int[4];

            if (Client.setmgr.getSettingByName(this, "Armor").getValBoolean()) {
                int i1 = 0;
                while (i1 < 4) {
                    final ItemStack bestArmor = Minecraft.thePlayer.inventory.armorItemInSlot(3 - i1);
                    if (bestArmor != null && bestArmor.getItem() instanceof ItemArmor) {
                        armor[i1] = ((ItemArmor) bestArmor.getItem()).getArmorMaterial().getAutoArmor();
                    }
                    ++i1;
                }
            }

            int best = -1;

            if (Client.setmgr.getSettingByName(this, "Swords").getValBoolean()) {
                int i = 0;
                while (i < 36) {
                    final ItemStack itemStack = Minecraft.thePlayer.inventory.getStackInSlot(i);
                    if (itemStack != null) {
                        if (itemStack.getItem() instanceof ItemSword) {
                            final ItemSword isword = (ItemSword) itemStack.getItem();
                            final int asd = getSwordLevel(isword);
                            if (asd > best) {
                                best = asd;
                            }
                        }
                    }
                    ++i;
                }
            }
            if (best == -1 && Client.setmgr.getSettingByName(this, "Swords").getValBoolean()) {
                int i = 0;
                while (i < 36) {
                    final ItemStack itemStack = Minecraft.thePlayer.inventory.getStackInSlot(i);
                    if (itemStack != null) {
                        if (itemStack.getItem() instanceof ItemAxe) {
                            final ItemAxe isword = (ItemAxe) itemStack.getItem();
                            final int asd = getAxeLevel(isword);
                            if (asd > best) {
                                best = asd;
                            }
                        }
                    }
                    ++i;
                }
            }

            final boolean isBestInHotbar = isInHotbar(best);

            int i = 0;
            while (i < 36) {
                final ItemStack itemStack = Minecraft.thePlayer.inventory.getStackInSlot(i);
                if (itemStack != null) {
                    if (timer.isDelayComplete(Client.setmgr.getSettingByName(this, "Delay").getValFloat())) {
                        if (!(itemStack.getItem() instanceof ItemBow || itemStack.getItem() instanceof ItemPotion)) {
                            if (itemStack.getItem() instanceof ItemArmor
                                    && Client.setmgr.getSettingByName(this, "Armor").getValBoolean()) {
                                final ItemArmor iarmor = (ItemArmor) itemStack.getItem();
                                if (armor[iarmor.armorType] != 0
                                        && armor[iarmor.armorType] >= iarmor.getArmorMaterial().getAutoArmor()) {
                                    InventoryUtil.dropSlot(i < 9 ? i + 36 : i);
                                    timer.reset();
                                }
                            } else if ((itemStack.getItem() instanceof ItemSword
                                    || itemStack.getItem() instanceof ItemAxe)
                                    && Client.setmgr.getSettingByName(this, "Swords").getValBoolean()) {
                                if (itemStack.getItem() instanceof ItemSword) {
                                    final ItemSword isword = (ItemSword) itemStack.getItem();
                                    if (isBestInHotbar) {
                                        if (i > 8) {
                                            if (getSwordLevel(isword) <= best) {
                                                InventoryUtil.dropSlot(i);
                                                timer.reset();
                                            }
                                        } else {
                                            if (getSwordLevel(isword) < best) {
                                                InventoryUtil.dropSlot(i + 36);
                                                timer.reset();
                                            }
                                        }
                                    } else {
                                        if (i > 8 && getSwordLevel(isword) < best) {
                                            InventoryUtil.dropSlot(i);
                                            timer.reset();
                                        }
                                    }
                                } else if (itemStack.getItem() instanceof ItemAxe) {
                                    final ItemAxe isword = (ItemAxe) itemStack.getItem();
                                    if (isBestInHotbar) {
                                        if (i > 8) {
                                            if (getAxeLevel(isword) <= best) {
                                                InventoryUtil.dropSlot(i);
                                                timer.reset();
                                            }
                                        } else {
                                            if (getAxeLevel(isword) < best) {
                                                InventoryUtil.dropSlot(i + 36);
                                                timer.reset();
                                            }
                                        }
                                    } else {
                                        if (i > 8 && getAxeLevel(isword) < best) {
                                            InventoryUtil.dropSlot(i);
                                            timer.reset();
                                        }
                                    }
                                }
                            } else if (!(itemStack.getItem() instanceof ItemArmor
                                    || itemStack.getItem() instanceof ItemSword) && itemStack.getMaxStackSize() == 1
                                    && Client.setmgr.getSettingByName(this, "Unstackables").getValBoolean()) {
                                if (best == -1) {
                                    if (!(itemStack.getItem() instanceof ItemTool)) {
                                        InventoryUtil.dropSlot(i < 9 ? i + 36 : i);
                                        timer.reset();
                                    }
                                } else {
                                    InventoryUtil.dropSlot(i < 9 ? i + 36 : i);
                                    timer.reset();
                                }
                            } else if (Client.setmgr.getSettingByName(this, "Mostly Unused").getValBoolean()) {
                                if (itemStack.getItem() instanceof ItemDye
                                        || unusedItems.contains(itemStack.getItem())) {
                                    InventoryUtil.dropSlot(i < 9 ? i + 36 : i);
                                    timer.reset();
                                }
                            }
                        }
                    }
                }
                ++i;
            }
        }
        super.onUpdate();
    }

    public boolean isInHotbar(final int level) {
        int i = 0;
        while (i < 9) {
            final ItemStack itemStack = Minecraft.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemSword) {
                    final ItemSword isword = (ItemSword) itemStack.getItem();
                    if (getSwordLevel(isword) == level) {
                        return true;
                    }
                }
            }
            ++i;
        }
        return false;
    }

    public int getSwordLevel(final ItemSword isword) {
        return isword.repairMaterial == ToolMaterial.WOOD ? 1
                : isword.repairMaterial == ToolMaterial.GOLD ? 2
                        : isword.repairMaterial == ToolMaterial.STONE ? 3
                                : isword.repairMaterial == ToolMaterial.IRON ? 4
                                        : isword.repairMaterial == ToolMaterial.EMERALD ? 5 : -1;
    }

    public int getAxeLevel(final ItemAxe isword) {
        return isword.getToolMaterial() == ToolMaterial.WOOD ? 0
                : isword.getToolMaterial() == ToolMaterial.GOLD ? 1
                : isword.getToolMaterial() == ToolMaterial.STONE ? 2
                : isword.getToolMaterial() == ToolMaterial.IRON ? 3
                : isword.getToolMaterial() == ToolMaterial.EMERALD ? 4 : -1;
    }

    public void drop(final int slotID) {
        Minecraft.playerController.windowClick(0, slotID, 0, 0, Minecraft.thePlayer);
        Minecraft.playerController.windowClick(0, -999, 0, 0, Minecraft.thePlayer);
    }

}
