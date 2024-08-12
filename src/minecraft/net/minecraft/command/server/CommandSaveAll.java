package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase {

public static final int EaZy = 987;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000826";

	@Override
	public String getCommandName() {
		return "save-all";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.save.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		final MinecraftServer var3 = MinecraftServer.getServer();
		sender.addChatMessage(new ChatComponentTranslation("commands.save.start", new Object[0]));

		if (var3.getConfigurationManager() != null) {
			var3.getConfigurationManager().saveAllPlayerData();
		}

		try {
			int var4;
			WorldServer var5;
			boolean var6;

			for (var4 = 0; var4 < var3.worldServers.length; ++var4) {
				if (var3.worldServers[var4] != null) {
					var5 = var3.worldServers[var4];
					var6 = var5.disableLevelSaving;
					var5.disableLevelSaving = false;
					var5.saveAllChunks(true, (IProgressUpdate) null);
					var5.disableLevelSaving = var6;
				}
			}

			if (args.length > 0 && "flush".equals(args[0])) {
				sender.addChatMessage(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));

				for (var4 = 0; var4 < var3.worldServers.length; ++var4) {
					if (var3.worldServers[var4] != null) {
						var5 = var3.worldServers[var4];
						var6 = var5.disableLevelSaving;
						var5.disableLevelSaving = false;
						var5.saveChunkData();
						var5.disableLevelSaving = var6;
					}
				}

				sender.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
			}
		} catch (final MinecraftException var7) {
			notifyOperators(sender, this, "commands.save.failed", new Object[] { var7.getMessage() });
			return;
		}

		notifyOperators(sender, this, "commands.save.success", new Object[0]);
	}
}
