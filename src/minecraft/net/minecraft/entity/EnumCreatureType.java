package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;

public enum EnumCreatureType {
	MONSTER("MONSTER", 0, IMob.class, 70, Material.air, false, false), CREATURE("CREATURE", 1, EntityAnimal.class, 10,
			Material.air, true, true), AMBIENT("AMBIENT", 2, EntityAmbientCreature.class, 15, Material.air, true,
					false), WATER_CREATURE("WATER_CREATURE", 3, EntityWaterMob.class, 5, Material.water, true, false);

	/**
	 * The root class of creatures associated with this EnumCreatureType (IMobs
	 * for aggressive creatures, EntityAnimals for friendly ones)
	 */
	private final Class creatureClass;
	private final int maxNumberOfCreature;
	/** A flag indicating whether this creature type is peaceful. */
	private final boolean isPeacefulCreature;

	/** Whether this creature type is an animal. */
	private final boolean isAnimal;

	private EnumCreatureType(final String p_i1596_1_, final int p_i1596_2_, final Class p_i1596_3_,
			final int p_i1596_4_, final Material p_i1596_5_, final boolean p_i1596_6_, final boolean p_i1596_7_) {
		creatureClass = p_i1596_3_;
		maxNumberOfCreature = p_i1596_4_;
		isPeacefulCreature = p_i1596_6_;
		isAnimal = p_i1596_7_;
	}

	public Class getCreatureClass() {
		return creatureClass;
	}

	public int getMaxNumberOfCreature() {
		return maxNumberOfCreature;
	}

	/**
	 * Gets whether or not this creature type is peaceful.
	 */
	public boolean getPeacefulCreature() {
		return isPeacefulCreature;
	}

	/**
	 * Return whether this creature type is an animal.
	 */
	public boolean getAnimal() {
		return isAnimal;
	}
}
