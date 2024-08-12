package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class EntityAIEatGrass extends EntityAIBase {

public static final int EaZy = 1052;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Predicate field_179505_b = BlockStateHelper.forBlock(Blocks.tallgrass)
			.func_177637_a(BlockTallGrass.field_176497_a, Predicates.equalTo(BlockTallGrass.EnumType.GRASS));

	/** The entity owner of this AITask */
	private final EntityLiving grassEaterEntity;

	/** The world the grass eater entity is eating from */
	private final World entityWorld;

	/** Number of ticks since the entity started to eat grass */
	int eatingGrassTimer;
	// private static final String __OBFID = "http://https://fuckuskid00001582";

	public EntityAIEatGrass(final EntityLiving p_i45314_1_) {
		grassEaterEntity = p_i45314_1_;
		entityWorld = p_i45314_1_.worldObj;
		setMutexBits(7);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (grassEaterEntity.getRNG().nextInt(grassEaterEntity.isChild() ? 50 : 1000) != 0) {
			return false;
		} else {
			final BlockPos var1 = new BlockPos(grassEaterEntity.posX, grassEaterEntity.posY, grassEaterEntity.posZ);
			return field_179505_b.apply(entityWorld.getBlockState(var1)) ? true
					: entityWorld.getBlockState(var1.offsetDown()).getBlock() == Blocks.grass;
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		eatingGrassTimer = 40;
		entityWorld.setEntityState(grassEaterEntity, (byte) 10);
		grassEaterEntity.getNavigator().clearPathEntity();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		eatingGrassTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return eatingGrassTimer > 0;
	}

	/**
	 * Number of ticks since the entity started to eat grass
	 */
	public int getEatingGrassTimer() {
		return eatingGrassTimer;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		eatingGrassTimer = Math.max(0, eatingGrassTimer - 1);

		if (eatingGrassTimer == 4) {
			final BlockPos var1 = new BlockPos(grassEaterEntity.posX, grassEaterEntity.posY, grassEaterEntity.posZ);

			if (field_179505_b.apply(entityWorld.getBlockState(var1))) {
				if (entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
					entityWorld.destroyBlock(var1, false);
				}

				grassEaterEntity.eatGrassBonus();
			} else {
				final BlockPos var2 = var1.offsetDown();

				if (entityWorld.getBlockState(var2).getBlock() == Blocks.grass) {
					if (entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
						entityWorld.playAuxSFX(2001, var2, Block.getIdFromBlock(Blocks.grass));
						entityWorld.setBlockState(var2, Blocks.dirt.getDefaultState(), 2);
					}

					grassEaterEntity.eatGrassBonus();
				}
			}
		}
	}
}
