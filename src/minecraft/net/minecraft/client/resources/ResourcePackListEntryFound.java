package net.minecraft.client.resources;

import me.EaZy.client.gui.mc.ResourcePackListEntryNew;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenResourcePacks;

public class ResourcePackListEntryFound extends ResourcePackListEntryNew {

public static final int EaZy = 897;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ResourcePackRepository.Entry field_148319_c;
	// private static final String __OBFID = "http://https://fuckuskid00000823";

	public ResourcePackListEntryFound(final GuiScreenResourcePacks p_i45053_1_,
			final ResourcePackRepository.Entry p_i45053_2_) {
		super(p_i45053_1_);
		field_148319_c = p_i45053_2_;
	}

	@Override
	protected void func_148313_c() {
		field_148319_c.bindTexturePackIcon(Minecraft.getTextureManager());
	}

	@Override
	protected String func_148311_a() {
		return field_148319_c.getTexturePackDescription();
	}

	@Override
	protected String func_148312_b() {
		return field_148319_c.getResourcePackName();
	}

	public ResourcePackRepository.Entry func_148318_i() {
		return field_148319_c;
	}
}
