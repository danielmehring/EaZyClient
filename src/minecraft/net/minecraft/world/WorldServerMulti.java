package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;

public class WorldServerMulti extends WorldServer {

public static final int EaZy = 1861;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final WorldServer delegate;
	// private static final String __OBFID = "http://https://fuckuskid00001430";

	public WorldServerMulti(final MinecraftServer server, final ISaveHandler saveHandlerIn, final int dimensionId,
			final WorldServer delegate, final Profiler profilerIn) {
		super(server, saveHandlerIn, new DerivedWorldInfo(delegate.getWorldInfo()), dimensionId, profilerIn);
		this.delegate = delegate;
		delegate.getWorldBorder().addListener(new IBorderListener() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002273";
			@Override
			public void onSizeChanged(final WorldBorder border, final double newSize) {
				WorldServerMulti.this.getWorldBorder().setTransition(newSize);
			}

			@Override
			public void func_177692_a(final WorldBorder border, final double p_177692_2_, final double p_177692_4_,
					final long p_177692_6_) {
				WorldServerMulti.this.getWorldBorder().setTransition(p_177692_2_, p_177692_4_, p_177692_6_);
			}

			@Override
			public void onCenterChanged(final WorldBorder border, final double x, final double z) {
				WorldServerMulti.this.getWorldBorder().setCenter(x, z);
			}

			@Override
			public void onWarningTimeChanged(final WorldBorder border, final int p_177691_2_) {
				WorldServerMulti.this.getWorldBorder().setWarningTime(p_177691_2_);
			}

			@Override
			public void onWarningDistanceChanged(final WorldBorder border, final int p_177690_2_) {
				WorldServerMulti.this.getWorldBorder().setWarningDistance(p_177690_2_);
			}

			@Override
			public void func_177696_b(final WorldBorder border, final double p_177696_2_) {
				WorldServerMulti.this.getWorldBorder().func_177744_c(p_177696_2_);
			}

			@Override
			public void func_177695_c(final WorldBorder border, final double p_177695_2_) {
				WorldServerMulti.this.getWorldBorder().setDamageBuffer(p_177695_2_);
			}
		});
	}

	/**
	 * Saves the chunks to disk.
	 */
	@Override
	protected void saveLevel() throws MinecraftException {}

	@Override
	public World init() {
		mapStorage = delegate.func_175693_T();
		worldScoreboard = delegate.getScoreboard();
		final String var1 = VillageCollection.func_176062_a(provider);
		final VillageCollection var2 = (VillageCollection) mapStorage.loadData(VillageCollection.class, var1);

		if (var2 == null) {
			villageCollectionObj = new VillageCollection(this);
			mapStorage.setData(var1, villageCollectionObj);
		} else {
			villageCollectionObj = var2;
			villageCollectionObj.func_82566_a(this);
		}

		return this;
	}
}
