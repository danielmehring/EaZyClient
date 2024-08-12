package me.EaZy.client.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import me.EaZy.client.FileManager;
import me.EaZy.client.alts.Alt;
import me.EaZy.client.gui.alts.GuiAltList;
import me.EaZy.client.gui.alts.GuiAlts;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiCleanUpAltz extends GuiScreen {

    public static final int EaZy = 66;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private boolean Cracked = true;
    private boolean Unchecked = false;
    private boolean Normal = false;
    private boolean Starred = false;

    private final GuiAlts prevMenu;
    private boolean removeAll;
    private final String[] toolTips = new String[]{"",
        "§2Start the Clean Up with\n§2the settings abvoe!\n§eIt might seem, your game\n§edoesn\'t react for a couple of seconds!",
        "§2Cracked Accounts!", "§2Unchecked Alts (they are\n§2on the bottom of the AltManager)!",
        "§2Normal, checked alts without a Star (\u2606)!", "§2All Alts that have a Star (§e\u2606§2)!",
        "§cThis will clear your complete AltManager!\n§4Use with caution!"};

    public GuiCleanUpAltz(final GuiAlts prevMultiplayerMenu) {
        prevMenu = prevMultiplayerMenu;
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 145, "Cancel"));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120, "Clean Up"));
        buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 - 24 + 12, "Cracked: " + removeOrKeep(Cracked)));
        buttonList.add(new GuiButton(3, width / 2 - 100, height / 4 + 0 + 12, "Unchecked: " + removeOrKeep(Unchecked)));
        buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + 12, "Normal: " + removeOrKeep(Normal)));
        buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + 12, "Starred: " + removeOrKeep(Starred)));
        buttonList.add(new GuiButton(6, width / 2 - 100, height / 4 + 72 + 12,
                "§cRemove §4ALL §cAlts: " + yesOrNo(removeAll)));
    }

    private String yesOrNo(final boolean bool) {
        return bool ? "Yes" : "No";
    }

    private String removeOrKeep(final boolean bool) {
        return bool ? "Remove" : "Keep";
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(final GuiButton clickedButton) {
        if (clickedButton.enabled) {
            switch (clickedButton.id) {
                case 0:
                    mc.displayGuiScreen(prevMenu);
                    break;
                case 1:
                    if (removeAll) {
                        GuiAltList.alts.clear();
                        FileManager.saveAltsManager();
                        mc.displayGuiScreen(prevMenu);
                        return;
                    }
                    Alt alt;
                    int i = GuiAltList.alts.size() - 1;
                    while (i >= 0) {
                        alt = GuiAltList.alts.get(i);
                        if (Unchecked && alt.isUnchecked() || Starred && alt.isStarred() || Cracked && alt.isCracked()
                                || Normal && !alt.isStarred() && !alt.isUnchecked()) {
                            GuiAltList.alts.remove(i);
                        }
                        --i;
                    }
                    FileManager.saveAltsManager();
                    mc.displayGuiScreen(prevMenu);
                    break;
                case 2:
                    Cracked = !Cracked;
                    clickedButton.displayString = "Cracked: " + removeOrKeep(Cracked);
                    break;
                case 3:
                    Unchecked = !Unchecked;
                    clickedButton.displayString = "Unchecked: " + removeOrKeep(Unchecked);
                    break;
                case 4:
                    Normal = !Normal;
                    clickedButton.displayString = "Normal: " + removeOrKeep(Normal);
                    break;
                case 5:
                    Starred = !Starred;
                    clickedButton.displayString = "Starred: " + removeOrKeep(Starred);
                    break;
                case 6:
                    removeAll = !removeAll;
                    clickedButton.displayString = "§cRemove §4ALL §cAlts: " + yesOrNo(removeAll);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 28 || par2 == 156) {
            actionPerformed((GuiButton) buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground(par1, par2);
        drawCenteredString(fontRendererObj, "Clean Up", width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
        int i = 0;
        while (i < buttonList.size()) {
            final GuiButton button = (GuiButton) buttonList.get(i);
            if (button.isMouseOver() && !toolTips[button.id].isEmpty()) {
                final ArrayList toolTip = Lists.newArrayList((Object[]) toolTips[button.id].split("\n"));
                drawHoveringText(toolTip, par1, par2);
                break;
            }
            ++i;
        }
    }
}
