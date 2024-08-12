package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

import java.util.Iterator;

public class CommandDefaultGameMode extends CommandGameMode {

public static final int EaZy = 931;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000296";

	@Override
	public String getCommandName() {
		return "defaultgamemode";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.defaultgamemode.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length <= 0) {
			throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
		} else {
			final WorldSettings.GameType var3 = getGameModeFromCommand(sender, args[0]);
			setGameType(var3);
			notifyOperators(sender, this, "commands.defaultgamemode.success",
					new Object[] { new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]) });
		}
	}

	protected void setGameType(final WorldSettings.GameType p_71541_1_) {
		final MinecraftServer var2 = MinecraftServer.getServer();
		var2.setGameType(p_71541_1_);
		EntityPlayerMP var4;

		if (var2.getForceGamemode()) {
			for (final Iterator var3 = MinecraftServer.getServer().getConfigurationManager().playerEntityList
					.iterator(); var3.hasNext(); var4.fallDistance = 0.0F) {
				var4 = (EntityPlayerMP) var3.next();
				var4.setGameType(p_71541_1_);
			}
		}
	}
}
