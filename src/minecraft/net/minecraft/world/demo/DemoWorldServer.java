package net.minecraft.world.demo;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class DemoWorldServer extends WorldServer {

public static final int EaZy = 1719;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final long demoWorldSeed = "North Carolina".hashCode();
	public static final WorldSettings demoWorldSettings = new WorldSettings(demoWorldSeed,
			WorldSettings.GameType.SURVIVAL, true, false, WorldType.DEFAULT).enableBonusChest();
	// private static final String __OBFID = "http://https://fuckuskid00001428";

	public DemoWorldServer(final MinecraftServer server, final ISaveHandler saveHandlerIn, final WorldInfo worldInfoIn,
			final int dimensionId, final Profiler profilerIn) {
		super(server, saveHandlerIn, worldInfoIn, dimensionId, profilerIn);
		worldInfo.populateFromWorldSettings(demoWorldSettings);
	}
}
