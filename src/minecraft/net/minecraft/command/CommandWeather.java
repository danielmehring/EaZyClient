package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

import java.util.List;
import java.util.Random;

public class CommandWeather extends CommandBase {

public static final int EaZy = 959;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001185";

	@Override
	public String getCommandName() {
		return "weather";
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
		return "commands.weather.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length >= 1 && args.length <= 2) {
			int var3 = (300 + new Random().nextInt(600)) * 20;

			if (args.length >= 2) {
				var3 = parseInt(args[1], 1, 1000000) * 20;
			}

			final WorldServer var4 = MinecraftServer.getServer().worldServers[0];
			final WorldInfo var5 = var4.getWorldInfo();

			if ("clear".equalsIgnoreCase(args[0])) {
				var5.func_176142_i(var3);
				var5.setRainTime(0);
				var5.setThunderTime(0);
				var5.setRaining(false);
				var5.setThundering(false);
				notifyOperators(sender, this, "commands.weather.clear", new Object[0]);
			} else if ("rain".equalsIgnoreCase(args[0])) {
				var5.func_176142_i(0);
				var5.setRainTime(var3);
				var5.setThunderTime(var3);
				var5.setRaining(true);
				var5.setThundering(false);
				notifyOperators(sender, this, "commands.weather.rain", new Object[0]);
			} else {
				if (!"thunder".equalsIgnoreCase(args[0])) {
					throw new WrongUsageException("commands.weather.usage", new Object[0]);
				}

				var5.func_176142_i(0);
				var5.setRainTime(var3);
				var5.setThunderTime(var3);
				var5.setRaining(true);
				var5.setThundering(true);
				notifyOperators(sender, this, "commands.weather.thunder", new Object[0]);
			}
		} else {
			throw new WrongUsageException("commands.weather.usage", new Object[0]);
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] { "clear", "rain", "thunder" })
				: null;
	}
}
