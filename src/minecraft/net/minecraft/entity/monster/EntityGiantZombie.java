package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityGiantZombie extends EntityMob {

public static final int EaZy = 1157;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001690";

	public EntityGiantZombie(final World worldIn) {
		super(worldIn);
		setSize(width * 6.0F, height * 6.0F);
	}

	@Override
	public float getEyeHeight() {
		return 10.440001F;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0D);
	}

	@Override
	public float func_180484_a(final BlockPos p_180484_1_) {
		return worldObj.getLightBrightness(p_180484_1_) - 0.5F;
	}
}
