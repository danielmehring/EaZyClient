package de.Hero.settings;

import me.EaZy.client.Module;

import java.util.ArrayList;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class Setting {

    public static final int EaZy = 21;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final String name;
    private final Module parent;
    public final String mode;

    private String sval;
    private ArrayList<String> options;

    private boolean bval;

    private float dval;
    private float min;
    private float max;
    private boolean onlyint = false;

    public Setting(final String name, final Module parent, final String sval, final ArrayList<String> options) {
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        mode = "Combo";
    }

    public Setting(final String name, final Module parent, final boolean bval) {
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        mode = "Check";
    }

    public Setting(final String name, final Module parent, final float dval, final float min, final float max,
            final boolean onlyint) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        mode = "Slider";
    }

    public String getName() {
        return name;
    }

    public Module getParentMod() {
        return parent;
    }

    public String getValString() {
        return sval;
    }

    public void setValString(final String in) {
        sval = in;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public boolean getValBoolean() {
        return bval;
    }

    public void setValBoolean(final boolean in) {
        bval = in;
    }

    public float getValFloat() {
        if (onlyint) {
            dval = (int) dval;
        }
        return dval;
    }

    public void setValFloat(final float in) {
        dval = in;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public boolean isCombo() {
        return mode.equalsIgnoreCase("Combo");
    }

    public boolean isCheck() {
        return mode.equalsIgnoreCase("Check");
    }

    public boolean isSlider() {
        return mode.equalsIgnoreCase("Slider");
    }

    public boolean onlyInt() {
        return onlyint;
    }
}
