package net.minecraft.world.gen.structure;

import net.minecraft.world.World;

import java.util.Random;

public class StructureMineshaftStart extends StructureStart {

public static final int EaZy = 1819;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000450";

	public StructureMineshaftStart() {}

	public StructureMineshaftStart(final World worldIn, final Random p_i2039_2_, final int p_i2039_3_,
			final int p_i2039_4_) {
		super(p_i2039_3_, p_i2039_4_);
		final StructureMineshaftPieces.Room var5 = new StructureMineshaftPieces.Room(0, p_i2039_2_,
				(p_i2039_3_ << 4) + 2, (p_i2039_4_ << 4) + 2);
		components.add(var5);
		var5.buildComponent(var5, components, p_i2039_2_);
		updateBoundingBox();
		markAvailableHeight(worldIn, p_i2039_2_, 10);
	}
}
