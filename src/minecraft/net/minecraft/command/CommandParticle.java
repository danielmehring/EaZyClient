package net.minecraft.command;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class CommandParticle extends CommandBase {

public static final int EaZy = 946;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002341";

	@Override
	public String getCommandName() {
		return "particle";
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
		return "commands.particle.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 8) {
			throw new WrongUsageException("commands.particle.usage", new Object[0]);
		} else {
			boolean var3 = false;
			EnumParticleTypes var4 = null;
			final EnumParticleTypes[] var5 = EnumParticleTypes.values();
			final int var6 = var5.length;

			for (int var7 = 0; var7 < var6; ++var7) {
				final EnumParticleTypes var8 = var5[var7];

				if (var8.func_179343_f()) {
					if (args[0].startsWith(var8.func_179346_b())) {
						var3 = true;
						var4 = var8;
						break;
					}
				} else if (args[0].equals(var8.func_179346_b())) {
					var3 = true;
					var4 = var8;
					break;
				}
			}

			if (!var3) {
				throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
			} else {
				final String var30 = args[0];
				final Vec3 var31 = sender.getPositionVector();
				final double var32 = (float) func_175761_b(var31.xCoord, args[1], true);
				final double var9 = (float) func_175761_b(var31.yCoord, args[2], true);
				final double var11 = (float) func_175761_b(var31.zCoord, args[3], true);
				final double var13 = (float) parseDouble(args[4]);
				final double var15 = (float) parseDouble(args[5]);
				final double var17 = (float) parseDouble(args[6]);
				final double var19 = (float) parseDouble(args[7]);
				int var21 = 0;

				if (args.length > 8) {
					var21 = parseInt(args[8], 0);
				}

				boolean var22 = false;

				if (args.length > 9 && "force".equals(args[9])) {
					var22 = true;
				}

				final World var23 = sender.getEntityWorld();

				if (var23 instanceof WorldServer) {
					final WorldServer var24 = (WorldServer) var23;
					final int[] var25 = new int[var4.func_179345_d()];

					if (var4.func_179343_f()) {
						final String[] var26 = args[0].split("_", 3);

						for (int var27 = 1; var27 < var26.length; ++var27) {
							try {
								var25[var27 - 1] = Integer.parseInt(var26[var27]);
							} catch (final NumberFormatException var29) {
								throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
							}
						}
					}

					var24.func_180505_a(var4, var22, var32, var9, var11, var21, var13, var15, var17, var19, var25);
					notifyOperators(sender, this, "commands.particle.success",
							new Object[] { var30, Math.max(var21, 1) });
				}
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, EnumParticleTypes.func_179349_a())
				: args.length > 1 && args.length <= 4 ? func_175771_a(args, 1, pos)
						: args.length == 9 ? getListOfStringsMatchingLastWord(args, new String[] { "normal", "force" })
								: null;
	}
}
