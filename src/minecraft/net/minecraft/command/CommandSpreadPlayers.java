package net.minecraft.command;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class CommandSpreadPlayers extends CommandBase {

public static final int EaZy = 953;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001080";

	@Override
	public String getCommandName() {
		return "spreadplayers";
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
		return "commands.spreadplayers.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 6) {
			throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
		} else {
			final byte var3 = 0;
			final BlockPos var4 = sender.getPosition();
			final double var10000 = var4.getX();
			int var17 = var3 + 1;
			final double var5 = func_175761_b(var10000, args[var3], true);
			final double var7 = func_175761_b(var4.getZ(), args[var17++], true);
			final double var9 = parseDouble(args[var17++], 0.0D);
			final double var11 = parseDouble(args[var17++], var9 + 1.0D);
			final boolean var13 = parseBoolean(args[var17++]);
			final ArrayList var14 = Lists.newArrayList();

			while (var17 < args.length) {
				final String var15 = args[var17++];

				if (PlayerSelector.hasArguments(var15)) {
					final List var16 = PlayerSelector.func_179656_b(sender, var15, Entity.class);

					if (var16.isEmpty()) {
						throw new EntityNotFoundException();
					}

					var14.addAll(var16);
				} else {
					final EntityPlayerMP var18 = MinecraftServer.getServer().getConfigurationManager()
							.getPlayerByUsername(var15);

					if (var18 == null) {
						throw new PlayerNotFoundException();
					}

					var14.add(var18);
				}
			}

			sender.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, var14.size());

			if (var14.isEmpty()) {
				throw new EntityNotFoundException();
			} else {
				sender.addChatMessage(new ChatComponentTranslation(
						"commands.spreadplayers.spreading." + (var13 ? "teams" : "players"),
						new Object[] { var14.size(), var11, var5, var7, var9}));
				func_110669_a(sender, var14, new CommandSpreadPlayers.Position(var5, var7), var9, var11,
						((Entity) var14.get(0)).worldObj, var13);
			}
		}
	}

	private void func_110669_a(final ICommandSender p_110669_1_, final List p_110669_2_,
			final CommandSpreadPlayers.Position p_110669_3_, final double p_110669_4_, final double p_110669_6_,
			final World worldIn, final boolean p_110669_9_) throws CommandException {
		final Random var10 = new Random();
		final double var11 = p_110669_3_.field_111101_a - p_110669_6_;
		final double var13 = p_110669_3_.field_111100_b - p_110669_6_;
		final double var15 = p_110669_3_.field_111101_a + p_110669_6_;
		final double var17 = p_110669_3_.field_111100_b + p_110669_6_;
		final CommandSpreadPlayers.Position[] var19 = func_110670_a(var10,
				p_110669_9_ ? func_110667_a(p_110669_2_) : p_110669_2_.size(), var11, var13, var15, var17);
		final int var20 = func_110668_a(p_110669_3_, p_110669_4_, worldIn, var10, var11, var13, var15, var17, var19,
				p_110669_9_);
		final double var21 = func_110671_a(p_110669_2_, worldIn, var19, p_110669_9_);
		notifyOperators(p_110669_1_, this, "commands.spreadplayers.success." + (p_110669_9_ ? "teams" : "players"),
				new Object[] { var19.length, p_110669_3_.field_111101_a, p_110669_3_.field_111100_b});

		if (var19.length > 1) {
			p_110669_1_.addChatMessage(new ChatComponentTranslation(
					"commands.spreadplayers.info." + (p_110669_9_ ? "teams" : "players"), new Object[] {
							String.format("%.2f", new Object[] { var21}), var20}));
		}
	}

	private int func_110667_a(final List p_110667_1_) {
		final HashSet var2 = Sets.newHashSet();
		final Iterator var3 = p_110667_1_.iterator();

		while (var3.hasNext()) {
			final Entity var4 = (Entity) var3.next();

			if (var4 instanceof EntityPlayer) {
				var2.add(((EntityPlayer) var4).getTeam());
			} else {
				var2.add((Object) null);
			}
		}

		return var2.size();
	}

	private int func_110668_a(final CommandSpreadPlayers.Position p_110668_1_, final double p_110668_2_,
			final World worldIn, final Random p_110668_5_, final double p_110668_6_, final double p_110668_8_,
			final double p_110668_10_, final double p_110668_12_, final CommandSpreadPlayers.Position[] p_110668_14_,
			final boolean p_110668_15_) throws CommandException {
		boolean var16 = true;
		double var18 = 3.4028234663852886E38D;
		int var17;

		for (var17 = 0; var17 < 10000 && var16; ++var17) {
			var16 = false;
			var18 = 3.4028234663852886E38D;
			int var22;
			CommandSpreadPlayers.Position var23;

			for (int var20 = 0; var20 < p_110668_14_.length; ++var20) {
				final CommandSpreadPlayers.Position var21 = p_110668_14_[var20];
				var22 = 0;
				var23 = new CommandSpreadPlayers.Position();

				for (int var24 = 0; var24 < p_110668_14_.length; ++var24) {
					if (var20 != var24) {
						final CommandSpreadPlayers.Position var25 = p_110668_14_[var24];
						final double var26 = var21.func_111099_a(var25);
						var18 = Math.min(var26, var18);

						if (var26 < p_110668_2_) {
							++var22;
							var23.field_111101_a += var25.field_111101_a - var21.field_111101_a;
							var23.field_111100_b += var25.field_111100_b - var21.field_111100_b;
						}
					}
				}

				if (var22 > 0) {
					var23.field_111101_a /= var22;
					var23.field_111100_b /= var22;
					final double var30 = var23.func_111096_b();

					if (var30 > 0.0D) {
						var23.func_111095_a();
						var21.func_111094_b(var23);
					} else {
						var21.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
					}

					var16 = true;
				}

				if (var21.func_111093_a(p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_)) {
					var16 = true;
				}
			}

			if (!var16) {
				final CommandSpreadPlayers.Position[] var28 = p_110668_14_;
				final int var29 = p_110668_14_.length;

				for (var22 = 0; var22 < var29; ++var22) {
					var23 = var28[var22];

					if (!var23.func_111098_b(worldIn)) {
						var23.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
						var16 = true;
					}
				}
			}
		}

		if (var17 >= 10000) {
			throw new CommandException("commands.spreadplayers.failure." + (p_110668_15_ ? "teams" : "players"),
					new Object[] { p_110668_14_.length, p_110668_1_.field_111101_a, p_110668_1_.field_111100_b,String.format("%.2f", new Object[]{var18})});
		} else {
			return var17;
		}
	}

	private double func_110671_a(final List p_110671_1_, final World worldIn,
			final CommandSpreadPlayers.Position[] p_110671_3_, final boolean p_110671_4_) {
		double var5 = 0.0D;
		int var7 = 0;
		final HashMap var8 = Maps.newHashMap();

		for (int var9 = 0; var9 < p_110671_1_.size(); ++var9) {
			final Entity var10 = (Entity) p_110671_1_.get(var9);
			CommandSpreadPlayers.Position var11;

			if (p_110671_4_) {
				final Team var12 = var10 instanceof EntityPlayer ? ((EntityPlayer) var10).getTeam() : null;

				if (!var8.containsKey(var12)) {
					var8.put(var12, p_110671_3_[var7++]);
				}

				var11 = (CommandSpreadPlayers.Position) var8.get(var12);
			} else {
				var11 = p_110671_3_[var7++];
			}

			var10.setPositionAndUpdate(MathHelper.floor_double(var11.field_111101_a) + 0.5F,
					var11.func_111092_a(worldIn), MathHelper.floor_double(var11.field_111100_b) + 0.5D);
			double var17 = Double.MAX_VALUE;

			for (final Position element : p_110671_3_) {
				if (var11 != element) {
					final double var15 = var11.func_111099_a(element);
					var17 = Math.min(var15, var17);
				}
			}

			var5 += var17;
		}

		var5 /= p_110671_1_.size();
		return var5;
	}

	private CommandSpreadPlayers.Position[] func_110670_a(final Random p_110670_1_, final int p_110670_2_,
			final double p_110670_3_, final double p_110670_5_, final double p_110670_7_, final double p_110670_9_) {
		final CommandSpreadPlayers.Position[] var11 = new CommandSpreadPlayers.Position[p_110670_2_];

		for (int var12 = 0; var12 < var11.length; ++var12) {
			final CommandSpreadPlayers.Position var13 = new CommandSpreadPlayers.Position();
			var13.func_111097_a(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
			var11[var12] = var13;
		}

		return var11;
	}

	static class Position {
		double field_111101_a;
		double field_111100_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001105";

		Position() {}

		Position(final double p_i1358_1_, final double p_i1358_3_) {
			field_111101_a = p_i1358_1_;
			field_111100_b = p_i1358_3_;
		}

		double func_111099_a(final CommandSpreadPlayers.Position p_111099_1_) {
			final double var2 = field_111101_a - p_111099_1_.field_111101_a;
			final double var4 = field_111100_b - p_111099_1_.field_111100_b;
			return Math.sqrt(var2 * var2 + var4 * var4);
		}

		void func_111095_a() {
			final double var1 = func_111096_b();
			field_111101_a /= var1;
			field_111100_b /= var1;
		}

		float func_111096_b() {
			return MathHelper.sqrt_double(field_111101_a * field_111101_a + field_111100_b * field_111100_b);
		}

		public void func_111094_b(final CommandSpreadPlayers.Position p_111094_1_) {
			field_111101_a -= p_111094_1_.field_111101_a;
			field_111100_b -= p_111094_1_.field_111100_b;
		}

		public boolean func_111093_a(final double p_111093_1_, final double p_111093_3_, final double p_111093_5_,
				final double p_111093_7_) {
			boolean var9 = false;

			if (field_111101_a < p_111093_1_) {
				field_111101_a = p_111093_1_;
				var9 = true;
			} else if (field_111101_a > p_111093_5_) {
				field_111101_a = p_111093_5_;
				var9 = true;
			}

			if (field_111100_b < p_111093_3_) {
				field_111100_b = p_111093_3_;
				var9 = true;
			} else if (field_111100_b > p_111093_7_) {
				field_111100_b = p_111093_7_;
				var9 = true;
			}

			return var9;
		}

		public int func_111092_a(final World worldIn) {
			BlockPos var2 = new BlockPos(field_111101_a, 256.0D, field_111100_b);

			do {
				if (var2.getY() <= 0) {
					return 257;
				}

				var2 = var2.offsetDown();
			}
			while (worldIn.getBlockState(var2).getBlock().getMaterial() == Material.air);

			return var2.getY() + 1;
		}

		public boolean func_111098_b(final World worldIn) {
			BlockPos var2 = new BlockPos(field_111101_a, 256.0D, field_111100_b);
			Material var3;

			do {
				if (var2.getY() <= 0) {
					return false;
				}

				var2 = var2.offsetDown();
				var3 = worldIn.getBlockState(var2).getBlock().getMaterial();
			}
			while (var3 == Material.air);

			return !var3.isLiquid() && var3 != Material.fire;
		}

		public void func_111097_a(final Random p_111097_1_, final double p_111097_2_, final double p_111097_4_,
				final double p_111097_6_, final double p_111097_8_) {
			field_111101_a = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_2_, p_111097_6_);
			field_111100_b = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_4_, p_111097_8_);
		}
	}
}
