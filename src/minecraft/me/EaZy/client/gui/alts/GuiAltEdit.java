/*
 * Decompiled with CFR 0_117.
 */
package me.EaZy.client.gui.alts;

import me.EaZy.client.FileManager;
import me.EaZy.client.alts.Alt;
import me.EaZy.client.alts.AltEditorScreen;
import me.EaZy.client.alts.GuiEmailField;
import me.EaZy.client.alts.GuiPasswordField;
import me.EaZy.client.alts.LoginManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

public class GuiAltEdit extends AltEditorScreen {

    public static final int EaZy = 58;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final Alt editedAlt;

    public GuiAltEdit(final GuiScreen par1GuiScreen, final Alt editedAlt) {
        super(par1GuiScreen);
        this.editedAlt = editedAlt;
    }

    @Override
    protected String getDoneButtonText() {
        return "Save";
    }

    @Override
    protected String getEmailBoxText() {
        return editedAlt.getEmail();
    }

    @Override
    protected String getPasswordBoxText() {
        return editedAlt.getPassword();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 72 + 12 + 40, getDoneButtonText()));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12 + 40, "Cancel"));
        emailBox = new GuiEmailField(0, fontRendererObj, width / 2 - 100, 60, 200, 20);
        emailBox.setMaxStringLength(48);
        emailBox.setFocused(true);
        emailBox.setText(getEmailBoxText());
        altBox = new GuiEmailField(1, fontRendererObj, width / 2 - 100, 140, 200, 20);
        altBox.setMaxStringLength(100);
        altBox.setText(getEmailBoxText() + ":" + getPasswordBoxText());
        passwordBox = new GuiPasswordField(fontRendererObj, width / 2 - 100, 100, 200, 20);
        passwordBox.setFocused(false);
        passwordBox.setText(getPasswordBoxText());
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
                displayText = "";
                GuiAltList.alts.set(GuiAltList.alts.indexOf(editedAlt),
                        new Alt(emailBox.getText(), null, null, editedAlt.isStarred()));
            } else {
                displayText = LoginManager.login(emailBox.getText(), passwordBox.getText());
                if (displayText.isEmpty()) {
                    GuiAltList.alts.set(GuiAltList.alts.indexOf(editedAlt), new Alt(emailBox.getText(),
                            passwordBox.getText(), Minecraft.session.getUsername(), editedAlt.isStarred()));
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
        return "Edit Alt";
    }
}
