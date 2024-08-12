package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandClearInventory extends CommandBase {

public static final int EaZy = 927;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000218";

	@Override
	public String getCommandName() {
		return "clear";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.clear.usage";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		final EntityPlayerMP var3 = args.length == 0 ? getCommandSenderAsPlayer(sender) : getPlayer(sender, args[0]);
		final Item var4 = args.length >= 2 ? getItemByText(sender, args[1]) : null;
		final int var5 = args.length >= 3 ? parseInt(args[2], -1) : -1;
		final int var6 = args.length >= 4 ? parseInt(args[3], -1) : -1;
		NBTTagCompound var7 = null;

		if (args.length >= 5) {
			try {
				var7 = JsonToNBT.parse(func_180529_a(args, 4));
			} catch (final NBTException var9) {
				throw new CommandException("commands.clear.tagError", new Object[] { var9.getMessage() });
			}
		}

		if (args.length >= 2 && var4 == null) {
			throw new CommandException("commands.clear.failure", new Object[] { var3.getName() });
		} else {
			final int var8 = var3.inventory.func_174925_a(var4, var5, var6, var7);
			var3.inventoryContainer.detectAndSendChanges();

			if (!var3.capabilities.isCreativeMode) {
				var3.updateHeldItem();
			}

			sender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, var8);

			if (var8 == 0) {
				throw new CommandException("commands.clear.failure", new Object[] { var3.getName() });
			} else {
				if (var6 == 0) {
					sender.addChatMessage(new ChatComponentTranslation("commands.clear.testing",
							new Object[] { var3.getName(), var8}));
				} else {
					notifyOperators(sender, this, "commands.clear.success",
							new Object[] { var3.getName(), var8});
				}
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, func_147209_d())
				: args.length == 2 ? func_175762_a(args, Item.itemRegistry.getKeys()) : null;
	}

	protected String[] func_147209_d() {
		return MinecraftServer.getServer().getAllUsernames();
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
