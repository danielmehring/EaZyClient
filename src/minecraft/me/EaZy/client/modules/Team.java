package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.EnumChatFormatting;

public class Team extends Module {

public static Team mod;


    public static final int EaZy = 187;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static EnumChatFormatting color;
    public String displayName;

    public Team() {
        super("Team", 0, "", Category.OTHER, "Auto-Friends your\nTeam-Mates.");
        color = EnumChatFormatting.RESET;
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Einheit";
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
        if (Minecraft.thePlayer.getDisplayName().getFormattedText().substring(0, 1).equals("§")) {
            final EnumChatFormatting[] arrenumChatFormatting = EnumChatFormatting.values();
            final int n = arrenumChatFormatting.length;
            int n2 = 0;
            while (n2 < n) {
                final EnumChatFormatting chatColor = arrenumChatFormatting[n2];
                if (new Character(chatColor.formattingCode).toString()
                        .equalsIgnoreCase(Minecraft.thePlayer.getDisplayName().getFormattedText().substring(1, 2))) {
                    color = chatColor;
                    displayName = Minecraft.thePlayer.getDisplayName().getFormattedText();
                }
                ++n2;
            }
        }
    }
}
