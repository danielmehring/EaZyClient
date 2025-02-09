package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandSetSpawnpoint extends CommandBase {

public static final int EaZy = 951;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001026";

	@Override
	public String getCommandName() {
		return "spawnpoint";
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
		return "commands.spawnpoint.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length > 0 && args.length < 4) {
			throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
		} else {
			final EntityPlayerMP var3 = args.length > 0 ? getPlayer(sender, args[0]) : getCommandSenderAsPlayer(sender);
			final BlockPos var4 = args.length > 3 ? func_175757_a(sender, args, 1, true) : var3.getPosition();

			if (var3.worldObj != null) {
				var3.func_180473_a(var4, true);
				notifyOperators(sender, this, "commands.spawnpoint.success", new Object[] { var3.getName(),
                                    var4.getX(), var4.getY(), var4.getZ() });
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames())
				: args.length > 1 && args.length <= 4 ? func_175771_a(args, 1, pos) : null;
	}

	/**
	 * Return whether the specified command parameter index is a username
	 * parameter.
	 */
	@Override
	public boolean isUsernameIndex(final String[] args, final int index) {
		return index == 0;
	}
}
