/*
 * Decompiled with CFR 0_117.
 */
package me.EaZy.client.gui.alts;

import me.EaZy.client.FileManager;
import me.EaZy.client.alts.Alt;
import me.EaZy.client.alts.AltEditorScreen;
import me.EaZy.client.alts.LoginManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiAltAdd extends AltEditorScreen {

    public static final int EaZy = 57;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public GuiAltAdd(final GuiScreen par1GuiScreen) {
        super(par1GuiScreen);
    }

    @Override
    protected String getDoneButtonText() {
        return "Add";
    }

    @Override
    protected String getEmailBoxText() {

        return Minecraft.session.getUsername();
    }

    @Override
    protected String getPasswordBoxText() {
        return "";
    }

    @Override
    protected void onDoneButtonClick(final GuiButton button) {
        if (altBox.getText().length() != 0 && altBox.getText().split(":").length == 2) {
            displayText = LoginManager.login(altBox.getText().split(":")[0], altBox.getText().split(":")[1]);
            if (displayText.isEmpty()) {
                GuiAltList.alts.add(new Alt(altBox.getText().split(":")[0], altBox.getText().split(":")[1],
                        Minecraft.session.getUsername()));
            }
        } else {
            if (passwordBox.getText().length() == 0) {
                GuiAltList.alts.add(new Alt(emailBox.getText(), null, null));
                displayText = "";
            } else {
                displayText = LoginManager.login(emailBox.getText(), passwordBox.getText());
                if (displayText.isEmpty()) {
                    GuiAltList.alts
                            .add(new Alt(emailBox.getText(), passwordBox.getText(), Minecraft.session.getUsername()));
                }
            }
        }
        if (displayText.isEmpty()) {
            GuiAltList.sortAlts();
            FileManager.saveAltsManager();
            mc.displayGuiScreen(prevMenu);
        } else {
            errorTimer = 8;
        }
    }

    @Override
    protected String getTitle() {
        return "Add Alt";
    }
}
