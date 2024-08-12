package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandTestFor extends CommandBase {

public static final int EaZy = 996;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001182";

	@Override
	public String getCommandName() {
		return "testfor";
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
		return "commands.testfor.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 1) {
			throw new WrongUsageException("commands.testfor.usage", new Object[0]);
		} else {
			final Entity var3 = func_175768_b(sender, args[0]);
			NBTTagCompound var4 = null;

			if (args.length >= 2) {
				try {
					var4 = JsonToNBT.parse(func_180529_a(args, 1));
				} catch (final NBTException var6) {
					throw new CommandException("commands.testfor.tagError", new Object[] { var6.getMessage() });
				}
			}

			if (var4 != null) {
				final NBTTagCompound var5 = new NBTTagCompound();
				var3.writeToNBT(var5);

				if (!CommandTestForBlock.func_175775_a(var4, var5, true)) {
					throw new CommandException("commands.testfor.failure", new Object[] { var3.getName() });
				}
			}

			notifyOperators(sender, this, "commands.testfor.success", new Object[] { var3.getName() });
		}
	}

	/**
	 * Return whether the specified command parameter index is a username
	 * parameter.
	 */
	@Override
	public boolean isUsernameIndex(final String[] args, final int index) {
		return index == 0;
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames())
				: null;
	}
}
