/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.input.Keyboard org.lwjgl.opengl.GL11
 */
package me.EaZy.client.alts;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public abstract class AltEditorScreen extends GuiScreen {

    public static final int EaZy = 24;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    protected GuiScreen prevMenu;
    protected GuiEmailField emailBox;
    protected GuiPasswordField passwordBox;
    protected GuiEmailField altBox;
    protected String displayText = "";
    protected int errorTimer;

    protected long lastNameCheck;

    public AltEditorScreen(final GuiScreen par1GuiScreen) {
        prevMenu = par1GuiScreen;
    }

    @Override
    public void updateScreen() {
        emailBox.updateCursorCounter();
        altBox.updateCursorCounter();
        passwordBox.updateCursorCounter();
        if (altBox.getText().length() != 0 && altBox.getText().split(":").length == 2) {
            ((GuiButton) buttonList.get(0)).enabled = true;
        } else {
            ((GuiButton) buttonList.get(0)).enabled = emailBox.getText().trim().length() > 0
                    || passwordBox.getText().length() != 0;
        }
    }

    protected abstract String getDoneButtonText();

    protected abstract String getEmailBoxText();

    protected abstract String getPasswordBoxText();

    protected String getName() {
        return emailBox.getText();
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
        altBox.setText("");
        passwordBox = new GuiPasswordField(fontRendererObj, width / 2 - 100, 100, 200, 20);
        passwordBox.setFocused(false);
        passwordBox.setText(getPasswordBoxText());
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected abstract void onDoneButtonClick(GuiButton var1);

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id == 1) {
                mc.displayGuiScreen(prevMenu);
            } else if (button.id == 0) {
                onDoneButtonClick(button);
            }
        }
    }

    @Override
    protected void keyTyped(final char par1, final int par2) {
        emailBox.textboxKeyTyped(par1, par2);
        altBox.textboxKeyTyped(par1, par2);
        passwordBox.textboxKeyTyped(par1, par2);
        if (par2 == 28 || par2 == 156) {
            actionPerformed((GuiButton) buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        emailBox.mouseClicked(par1, par2, par3);
        altBox.mouseClicked(par1, par2, par3);
        passwordBox.mouseClicked(par1, par2, par3);
        if (emailBox.isFocused() || passwordBox.isFocused()) {
            displayText = "";
        }
    }

    protected abstract String getTitle();

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground(par1, par2);
        AltRenderer.drawAltBack(emailBox.getText(), (width / 2 - 100) / 2 - 64, height / 2 - 128, 128, 256);
        AltRenderer.drawAltBody(emailBox.getText(), width - (width / 2 - 100) / 2 - 64, height / 2 - 128, 128, 256);
        drawCenteredString(fontRendererObj, getTitle(), width / 2, 20, 16777215);
        drawString(fontRendererObj, "Name or E-Mail", width / 2 - 100, 47, 10526880);
        drawString(fontRendererObj, "Password", width / 2 - 100, 87, 10526880);
        drawString(fontRendererObj, "E-Mail:Password", width / 2 - 100, 127, 10526880);
        drawCenteredString(fontRendererObj, displayText, width / 2, 142, 16777215);
        emailBox.drawTextBox();
        altBox.drawTextBox();
        passwordBox.drawTextBox();
        if (errorTimer > 0) {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0f, 0.0f, 0.0f, errorTimer / 16.0f);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(0.0, 0.0);
            GL11.glVertex2d(width, 0.0);
            GL11.glVertex2d(width, height);
            GL11.glVertex2d(0.0, height);
            GL11.glEnd();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            --errorTimer;
        }
        super.drawScreen(par1, par2, par3);
    }
}
