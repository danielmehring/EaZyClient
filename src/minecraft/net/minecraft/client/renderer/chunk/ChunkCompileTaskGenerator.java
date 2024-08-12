package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RegionRenderCacheBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Lists;

public class ChunkCompileTaskGenerator {

public static final int EaZy = 696;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RenderChunk field_178553_a;
	private final ReentrantLock field_178551_b = new ReentrantLock();
	private final List field_178552_c = Lists.newArrayList();
	private final ChunkCompileTaskGenerator.Type field_178549_d;
	private RegionRenderCacheBuilder field_178550_e;
	private CompiledChunk field_178547_f;
	private ChunkCompileTaskGenerator.Status field_178548_g;
	private boolean field_178554_h;
	// private static final String __OBFID = "http://https://fuckuskid00002466";

	public ChunkCompileTaskGenerator(final RenderChunk p_i46208_1_, final ChunkCompileTaskGenerator.Type p_i46208_2_) {
		field_178548_g = ChunkCompileTaskGenerator.Status.PENDING;
		field_178553_a = p_i46208_1_;
		field_178549_d = p_i46208_2_;
	}

	public ChunkCompileTaskGenerator.Status func_178546_a() {
		return field_178548_g;
	}

	public RenderChunk func_178536_b() {
		return field_178553_a;
	}

	public CompiledChunk func_178544_c() {
		return field_178547_f;
	}

	public void func_178543_a(final CompiledChunk p_178543_1_) {
		field_178547_f = p_178543_1_;
	}

	public RegionRenderCacheBuilder func_178545_d() {
		return field_178550_e;
	}

	public void func_178541_a(final RegionRenderCacheBuilder p_178541_1_) {
		field_178550_e = p_178541_1_;
	}

	public void func_178535_a(final ChunkCompileTaskGenerator.Status p_178535_1_) {
		field_178551_b.lock();

		try {
			field_178548_g = p_178535_1_;
		}
		finally {
			field_178551_b.unlock();
		}
	}

	public void func_178542_e() {
		field_178551_b.lock();

		try {
			if (field_178549_d == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK
					&& field_178548_g != ChunkCompileTaskGenerator.Status.DONE) {
				field_178553_a.func_178575_a(true);
			}

			field_178554_h = true;
			field_178548_g = ChunkCompileTaskGenerator.Status.DONE;
			final Iterator var1 = field_178552_c.iterator();

			while (var1.hasNext()) {
				final Runnable var2 = (Runnable) var1.next();
				var2.run();
			}
		}
		finally {
			field_178551_b.unlock();
		}
	}

	public void func_178539_a(final Runnable p_178539_1_) {
		field_178551_b.lock();

		try {
			field_178552_c.add(p_178539_1_);

			if (field_178554_h) {
				p_178539_1_.run();
			}
		}
		finally {
			field_178551_b.unlock();
		}
	}

	public ReentrantLock func_178540_f() {
		return field_178551_b;
	}

	public ChunkCompileTaskGenerator.Type func_178538_g() {
		return field_178549_d;
	}

	public boolean func_178537_h() {
		return field_178554_h;
	}

	public static enum Status {
		PENDING("PENDING", 0, "PENDING", 0), COMPILING("COMPILING", 1, "COMPILING", 1), UPLOADING("UPLOADING", 2,
				"UPLOADING", 2), DONE("DONE", 3, "DONE", 3);
		private Status(final String p_i46385_1_, final int p_i46385_2_, final String p_i46207_1_,
				final int p_i46207_2_) {}
	}

	public static enum Type {
		REBUILD_CHUNK("REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0), RESORT_TRANSPARENCY("RESORT_TRANSPARENCY", 1,
				"RESORT_TRANSPARENCY", 1);
		private Type(final String p_i46386_1_, final int p_i46386_2_, final String p_i46206_1_,
				final int p_i46206_2_) {}
	}
}
