package me.EaZy.client;

import me.EaZy.client.hooks.InGameGUIHook;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Module {

    public static final int EaZy = 88;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    protected static Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public String alias;
    public int keyCode;
    private boolean toggled;
    private long currentMS;
    private long lastMS;
    private final Category category;
    private final String addon = "";
    public boolean togglecmd;
    public boolean isToggleable = true;
    private String description;
    public int xxx;

    public Module(final String name, final int keyCode, final String alias, final Category category) {
        this.name = name;
        this.keyCode = keyCode;
        this.alias = alias;
        toggled = false;
        this.category = category;
    }

    public Module(final String name, final int keyCode, final String alias, final Category category,
            final String description) {
        this.name = name;
        this.keyCode = keyCode;
        this.alias = alias;
        toggled = false;
        this.category = category;
        this.description = description;
    }

    public Module(final String name, final int keyCode, final String alias, final Category category,
            final boolean toggleAble) {
        this.name = name;
        this.keyCode = keyCode;
        this.alias = alias;
        toggled = false;
        this.category = category;
        isToggleable = toggleAble;
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String getRenderName() {
        return String.valueOf(name) + addon;
    }

    public Category getCategory() {
        return category;
    }

    public final boolean isCategory(final Category s) {
        return s == category;
    }

    public void onEnable() {
        if (Configs.toggleSounds) {
            if (Minecraft.thePlayer != null) {
                Minecraft.thePlayer.playSound("gui.button.press", 0.3f, 0.1f);
            }
        }
        InGameGUIHook.sort();
        FileManager.saveMods();
    }

    public void onDisable() {
        if (Configs.toggleSounds) {
            if (Minecraft.thePlayer != null) {
                Minecraft.thePlayer.playSound("gui.button.press", 0.3f, 0.1f);
            }
        }
        InGameGUIHook.sort();
        FileManager.saveMods();
    }

    public final void enableOnStartup() {
        togglecmd = true;
        toggled = true;
        try {
            onEnable();
        } catch (final Exception e) {
            Client.getLogger().error("Can not enable: " + getName());
            e.printStackTrace();
            togglecmd = false;
            toggled = false;
        }
    }

    public void onUpdate() {

    }

    public void onRender() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(final int keycode) {
        keyCode = keycode;
    }

    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isToggled() {
        return toggled;
    }

    public final void updateMS() {
        currentMS = System.currentTimeMillis();
    }

    public final void updateLastMS() {
        lastMS = System.currentTimeMillis();
    }

    public final boolean hasTimePassedM(final long MS) {
        return currentMS >= lastMS + MS;
    }

    public final boolean hasTimePassedS(final float speed) {
        return currentMS >= lastMS + (long) (1000.0f / speed);
    }

    public static void msg(final String Message) {
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(Client.prefix + Message));
    }

    public final String getDescription() {
        return description;
    }
}
