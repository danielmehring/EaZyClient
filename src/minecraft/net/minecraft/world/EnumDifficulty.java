package net.minecraft.world;

public enum EnumDifficulty {
	PEACEFUL("PEACEFUL", 0, 0, "options.difficulty.peaceful"), EASY("EASY", 1, 1, "options.difficulty.easy"), NORMAL(
			"NORMAL", 2, 2, "options.difficulty.normal"), HARD("HARD", 3, 3, "options.difficulty.hard");
	private static final EnumDifficulty[] difficultyEnums = new EnumDifficulty[values().length];
	private final int difficultyId;
	private final String difficultyResourceKey;

	private EnumDifficulty(final String p_i45312_1_, final int p_i45312_2_, final int p_i45312_3_,
			final String p_i45312_4_) {
		difficultyId = p_i45312_3_;
		difficultyResourceKey = p_i45312_4_;
	}

	public int getDifficultyId() {
		return difficultyId;
	}

	public static EnumDifficulty getDifficultyEnum(final int p_151523_0_) {
		return difficultyEnums[p_151523_0_ % difficultyEnums.length];
	}

	public String getDifficultyResourceKey() {
		return difficultyResourceKey;
	}

	static {
		final EnumDifficulty[] var0 = values();
		final int var1 = var0.length;

		for (int var2 = 0; var2 < var1; ++var2) {
			final EnumDifficulty var3 = var0[var2];
			difficultyEnums[var3.difficultyId] = var3;
		}
	}
}
