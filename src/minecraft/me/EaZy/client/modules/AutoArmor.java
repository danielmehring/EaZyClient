package me.EaZy.client.modules;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiInventory;

public class AutoArmor extends Module {

public static AutoArmor mod;


    public static final int EaZy = 94;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private int delay = 0;
    private int openDelay = 0;

    public AutoArmor() {
        super(new String(new byte[]{0b1000001, 0b1110101, 0b1110100, 0b1101111, 0b1000001, 0b1110010, 0b1101101,
            0b1101111, 0b1110010}), 76, "aa",
                Category.COMBAT,
                new String(new byte[]{0b1000001, 0b1110101, 0b1110100, 0b1101111, 0b1101101, 0b1100001, 0b1110100,
                    0b1101001, 0b1100011, 0b1100001, 0b1101100, 0b1101100, 0b1111001, 0b100000, 0b1100111,
                    0b1100101, 0b1110100, 0b1110011, 0b100000, 0b1111001, 0b1101111, 0b1110101})
                + "\n"
                + new String(new byte[]{0b1110100, 0b1101000, 0b1100101, 0b100000, 0b1100010, 0b1100101,
                    0b1110011, 0b1110100, 0b100000, 0b1000001, 0b1110010, 0b1101101, 0b1101111, 0b1110010,
                    0b100001}));
        Client.setmgr.rSetting(new Setting("OnlyInventory", this, false));
        Client.setmgr.rSetting(new Setting("Delay", this, 3, 0, 5, true));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "AutomatischeRüstung";
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

        if (delay <= 0) {
            if (Client.setmgr.getSettingByName(this, "OnlyInventory").getValBoolean()
                    ? Minecraft.currentScreen instanceof GuiInventory
                    : Minecraft.currentScreen == null || Minecraft.currentScreen instanceof GuiInventory
                    || Minecraft.currentScreen instanceof ClickGUI) {
                int i = 0;
                while (i < 4) {
                    int bestSlot = InventoryUtil.findBestArmorSlot(ArmorType.byId(i));
                    if (bestSlot != ArmorType.byId(i).slotOffset + 36) {
                        if (bestSlot < 9) {
                            bestSlot += 36;
                        }
                        InventoryUtil.switchItemsInSlots(bestSlot, 5 + i);
                        delay = (int) Client.setmgr.getSettingByName(this, "Delay").getValFloat();
                        return;
                    }
                    ++i;
                }
            }
        } else {
            --delay;
        }

        super.onUpdate();
    }

    public static enum ArmorType {
        BOOTS(0, 3), LEGGINGS(1, 2), CHESTPLATE(2, 1), HELMET(3, 0);

        public final int slotOffset;
        public final int armorType;

        private ArmorType(final int slotOffset, final int armorType) {
            this.slotOffset = slotOffset;
            this.armorType = armorType;
        }

        public static ArmorType byId(final int id) {
            final ArmorType[] arrarmorType = ArmorType.values();
            final int n = arrarmorType.length;
            int n2 = 0;
            while (n2 < n) {
                final ArmorType type = arrarmorType[n2];
                if (type.armorType == id) {
                    return type;
                }
                ++n2;
            }
            return null;
        }
    }
}
