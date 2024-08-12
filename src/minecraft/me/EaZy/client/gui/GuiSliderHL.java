package me.EaZy.client.gui;

import me.EaZy.client.utils.TimeHelper2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiSliderHL extends GuiButton {

    public static final int EaZy = 76;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public float sliderValue;
    public boolean dragging = false;
    public float multyply;
    public String oldDisplayString;

    /**
     * Values for the Animation
     */
    public double chSize = 0;
    public int color = 80;
    public int mincolor = 80;
    public int maxcolor = 180;
    public int colorstep = 10;

    TimeHelper2 th2 = new TimeHelper2();

    /**
     * Sets up a slider.
     */
    public GuiSliderHL(final int buttonID, final int x, final int y, final int width, final int height,
            final String label, final float initialValue, final float multyply) {
        super(buttonID, x, y, width, height, label);
        this.multyply = multyply;
        sliderValue = initialValue / multyply;
        oldDisplayString = label;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of
     * MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
    protected void mouseDragged(final Minecraft par1Minecraft, final int x, final int y) {
        if (dragging) {
            sliderValue = limitToRange((float) (x - (xPosition + 4)) / (float) (width - 8));
            if (hovered) {
                displayString = "" + getSliderValue();
            } else {
                displayString = oldDisplayString;
            }
        } else {
            displayString = oldDisplayString;
        }

        /**
         * Rendering
         */
        Minecraft.getTextureManager().bindTexture(buttonTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)), yPosition, 0, 66, 4, 20);
        drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)) + 4, yPosition, 196, 66, 4, 20);

    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of
     * MouseListener.mousePressed(MouseEvent e).
     */
    @Override
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        if (super.mousePressed(mc, x, y)) {
            dragging = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of
     * MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public void mouseReleased(final int par1, final int par2) {
        dragging = false;
    }

    private float limitToRange(final float value) {
        if (value < 0) {
            return 0;
        } else if (value > 1) {
            return 1;
        } else {
            return value;
        }
    }

    public float getSliderValue() {
        return sliderValue * multyply;
    }
}
