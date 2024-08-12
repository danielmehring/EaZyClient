package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandToggleDownfall extends CommandBase {

public static final int EaZy = 957;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001184";

	@Override
	public String getCommandName() {
		return "toggledownfall";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.downfall.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		toggleDownfall();
		notifyOperators(sender, this, "commands.downfall.success", new Object[0]);
	}

	/**
	 * Toggle rain and enable thundering.
	 */
	protected void toggleDownfall() {
		final WorldInfo var1 = MinecraftServer.getServer().worldServers[0].getWorldInfo();
		var1.setRaining(!var1.isRaining());
	}
}
