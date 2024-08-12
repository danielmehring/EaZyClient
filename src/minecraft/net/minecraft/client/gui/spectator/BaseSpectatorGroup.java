package net.minecraft.client.gui.spectator;

import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.gui.spectator.categories.TeleportToTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.List;

import com.google.common.collect.Lists;

public class BaseSpectatorGroup implements ISpectatorMenuView {

public static final int EaZy = 550;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final List field_178671_a = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00001928";

	public BaseSpectatorGroup() {
		field_178671_a.add(new TeleportToPlayer());
		field_178671_a.add(new TeleportToTeam());
	}

	@Override
	public List func_178669_a() {
		return field_178671_a;
	}

	@Override
	public IChatComponent func_178670_b() {
		return new ChatComponentText("Press a key to select a command, and again to use it.");
	}
}
