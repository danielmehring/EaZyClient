package de.Hero.settings;

import me.EaZy.client.Module;

import java.util.ArrayList;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class SettingsManager {

    public static final int EaZy = 22;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final ArrayList<Setting> settings;

    public SettingsManager() {
        settings = new ArrayList<>();
    }

    public void rSetting(final Setting in) {
        settings.add(in);
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public ArrayList<Setting> getSettingsByMod(final Module mod) {
        final ArrayList<Setting> out = new ArrayList<>();
        getSettings().stream().filter((s) -> (s.getParentMod().equals(mod))).forEachOrdered((s) -> {
            out.add(s);
        });
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }

    public Setting getSettingByName(final Module mod, final String name) {
        for (final Setting set : getSettings()) {
            if (set.getParentMod().equals(mod) && set.getName().equalsIgnoreCase(name)) {
                return set;
            }
        }
        System.err.println("[EaZy] Setting NOT found: '" + name + "' (" + mod.getName() + ")!");
        return null;
    }

}
