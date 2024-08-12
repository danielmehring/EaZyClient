package net.minecraft.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable {

public static final int EaZy = 1186;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected EntityAISit aiSit = new EntityAISit(this);
	// private static final String __OBFID = "http://https://fuckuskid00001561";

	public EntityTameable(final World worldIn) {
		super(worldIn);
		func_175544_ck();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, "");
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);

		if (func_152113_b() == null) {
			tagCompound.setString("OwnerUUID", "");
		} else {
			tagCompound.setString("OwnerUUID", func_152113_b());
		}

		tagCompound.setBoolean("Sitting", isSitting());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		String var2 = "";

		if (tagCompund.hasKey("OwnerUUID", 8)) {
			var2 = tagCompund.getString("OwnerUUID");
		} else {
			final String var3 = tagCompund.getString("Owner");
			var2 = PreYggdrasilConverter.func_152719_a(var3);
		}

		if (var2.length() > 0) {
			func_152115_b(var2);
			setTamed(true);
		}

		aiSit.setSitting(tagCompund.getBoolean("Sitting"));
		setSitting(tagCompund.getBoolean("Sitting"));
	}

	/**
	 * Play the taming effect, will either be hearts or smoke depending on
	 * status
	 */
	protected void playTameEffect(final boolean p_70908_1_) {
		EnumParticleTypes var2 = EnumParticleTypes.HEART;

		if (!p_70908_1_) {
			var2 = EnumParticleTypes.SMOKE_NORMAL;
		}

		for (int var3 = 0; var3 < 7; ++var3) {
			final double var4 = rand.nextGaussian() * 0.02D;
			final double var6 = rand.nextGaussian() * 0.02D;
			final double var8 = rand.nextGaussian() * 0.02D;
			worldObj.spawnParticle(var2, posX + rand.nextFloat() * width * 2.0F - width,
					posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var4, var6,
					var8, new int[0]);
		}
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 7) {
			playTameEffect(true);
		} else if (p_70103_1_ == 6) {
			playTameEffect(false);
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	public boolean isTamed() {
		return (dataWatcher.getWatchableObjectByte(16) & 4) != 0;
	}

	public void setTamed(final boolean p_70903_1_) {
		final byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (p_70903_1_) {
			dataWatcher.updateObject(16, (byte) (var2 | 4));
		} else {
			dataWatcher.updateObject(16, (byte) (var2 & -5));
		}

		func_175544_ck();
	}

	protected void func_175544_ck() {}

	public boolean isSitting() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setSitting(final boolean p_70904_1_) {
		final byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (p_70904_1_) {
			dataWatcher.updateObject(16, (byte) (var2 | 1));
		} else {
			dataWatcher.updateObject(16, (byte) (var2 & -2));
		}
	}

	@Override
	public String func_152113_b() {
		return dataWatcher.getWatchableObjectString(17);
	}

	public void func_152115_b(final String p_152115_1_) {
		dataWatcher.updateObject(17, p_152115_1_);
	}

	public EntityLivingBase func_180492_cm() {
		try {
			final UUID var1 = UUID.fromString(func_152113_b());
			return var1 == null ? null : worldObj.getPlayerEntityByUUID(var1);
		} catch (final IllegalArgumentException var2) {
			return null;
		}
	}

	public boolean func_152114_e(final EntityLivingBase p_152114_1_) {
		return p_152114_1_ == func_180492_cm();
	}

	/**
	 * Returns the AITask responsible of the sit logic
	 */
	public EntityAISit getAISit() {
		return aiSit;
	}

	public boolean func_142018_a(final EntityLivingBase p_142018_1_, final EntityLivingBase p_142018_2_) {
		return true;
	}

	@Override
	public Team getTeam() {
		if (isTamed()) {
			final EntityLivingBase var1 = func_180492_cm();

			if (var1 != null) {
				return var1.getTeam();
			}
		}

		return super.getTeam();
	}

	@Override
	public boolean isOnSameTeam(final EntityLivingBase p_142014_1_) {
		if (isTamed()) {
			final EntityLivingBase var2 = func_180492_cm();

			if (p_142014_1_ == var2) {
				return true;
			}

			if (var2 != null) {
				return var2.isOnSameTeam(p_142014_1_);
			}
		}

		return super.isOnSameTeam(p_142014_1_);
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		if (!worldObj.isRemote && worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages")
				&& hasCustomName() && func_180492_cm() instanceof EntityPlayerMP) {
			((EntityPlayerMP) func_180492_cm()).addChatMessage(getCombatTracker().func_151521_b());
		}

		super.onDeath(cause);
	}

	@Override
	public Entity getOwner() {
		return func_180492_cm();
	}
}
