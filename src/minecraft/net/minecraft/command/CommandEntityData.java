package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandEntityData extends CommandBase {

public static final int EaZy = 935;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002345";

	@Override
	public String getCommandName() {
		return "entitydata";
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
		return "commands.entitydata.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 2) {
			throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
		} else {
			final Entity var3 = func_175768_b(sender, args[0]);

			if (var3 instanceof EntityPlayer) {
				throw new CommandException("commands.entitydata.noPlayers", new Object[] { var3.getDisplayName() });
			} else {
				final NBTTagCompound var4 = new NBTTagCompound();
				var3.writeToNBT(var4);
				final NBTTagCompound var5 = (NBTTagCompound) var4.copy();
				NBTTagCompound var6;

				try {
					var6 = JsonToNBT.parse(getChatComponentFromNthArg(sender, args, 1).getUnformattedText());
				} catch (final NBTException var8) {
					throw new CommandException("commands.entitydata.tagError", new Object[] { var8.getMessage() });
				}

				var6.removeTag("UUIDMost");
				var6.removeTag("UUIDLeast");
				var4.merge(var6);

				if (var4.equals(var5)) {
					throw new CommandException("commands.entitydata.failed", new Object[] { var4.toString() });
				} else {
					var3.readFromNBT(var4);
					notifyOperators(sender, this, "commands.entitydata.success", new Object[] { var4.toString() });
				}
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames())
				: null;
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
