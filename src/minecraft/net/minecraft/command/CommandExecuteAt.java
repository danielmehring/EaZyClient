package net.minecraft.command;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class CommandExecuteAt extends CommandBase {

public static final int EaZy = 937;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002344";

	@Override
	public String getCommandName() {
		return "execute";
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
		return "commands.execute.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 5) {
			throw new WrongUsageException("commands.execute.usage", new Object[0]);
		} else {
			final Entity var3 = func_175759_a(sender, args[0], Entity.class);
			final double var4 = func_175761_b(var3.posX, args[1], false);
			final double var6 = func_175761_b(var3.posY, args[2], false);
			final double var8 = func_175761_b(var3.posZ, args[3], false);
			final BlockPos var10 = new BlockPos(var4, var6, var8);
			byte var11 = 4;

			if ("detect".equals(args[4]) && args.length > 10) {
				final World var12 = sender.getEntityWorld();
				final double var13 = func_175761_b(var4, args[5], false);
				final double var15 = func_175761_b(var6, args[6], false);
				final double var17 = func_175761_b(var8, args[7], false);
				final Block var19 = getBlockByText(sender, args[8]);
				final int var20 = parseInt(args[9], -1, 15);
				final BlockPos var21 = new BlockPos(var13, var15, var17);
				final IBlockState var22 = var12.getBlockState(var21);

				if (var22.getBlock() != var19 || var20 >= 0 && var22.getBlock().getMetaFromState(var22) != var20) {
					throw new CommandException("commands.execute.failed", new Object[] { "detect", var3.getName() });
				}

				var11 = 10;
			}

			final String var24 = func_180529_a(args, var11);
			final ICommandSender var14 = new ICommandSender() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002343";
				@Override
				public String getName() {
					return var3.getName();
				}

				@Override
				public IChatComponent getDisplayName() {
					return var3.getDisplayName();
				}

				@Override
				public void addChatMessage(final IChatComponent message) {
					sender.addChatMessage(message);
				}

				@Override
				public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
					return sender.canCommandSenderUseCommand(permissionLevel, command);
				}

				@Override
				public BlockPos getPosition() {
					return var10;
				}

				@Override
				public Vec3 getPositionVector() {
					return new Vec3(var4, var6, var8);
				}

				@Override
				public World getEntityWorld() {
					return var3.worldObj;
				}

				@Override
				public Entity getCommandSenderEntity() {
					return var3;
				}

				@Override
				public boolean sendCommandFeedback() {
					final MinecraftServer var1 = MinecraftServer.getServer();
					return var1 == null
							|| var1.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput");
				}

				@Override
				public void func_174794_a(final CommandResultStats.Type p_174794_1_, final int p_174794_2_) {
					var3.func_174794_a(p_174794_1_, p_174794_2_);
				}
			};
			final ICommandManager var25 = MinecraftServer.getServer().getCommandManager();

			try {
				final int var16 = var25.executeCommand(var14, var24);

				if (var16 < 1) {
					throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { var24 });
				}
			} catch (final Throwable var23) {
				throw new CommandException("commands.execute.failed", new Object[] { var24, var3.getName() });
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames())
				: args.length > 1 && args.length <= 4 ? func_175771_a(args, 1, pos)
						: args.length > 5 && args.length <= 8 && "detect".equals(args[4]) ? func_175771_a(args, 5, pos)
								: args.length == 9 && "detect".equals(args[4])
										? func_175762_a(args, Block.blockRegistry.getKeys()) : null;
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
