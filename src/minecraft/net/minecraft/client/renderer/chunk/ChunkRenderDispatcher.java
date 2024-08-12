package net.minecraft.client.renderer.chunk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ChunkRenderDispatcher {

public static final int EaZy = 697;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_178523_a = LogManager.getLogger();
	private static final ThreadFactory field_178521_b = new ThreadFactoryBuilder().setNameFormat("Chunk Batcher %d")
			.setDaemon(true).build();
	private final List field_178522_c = Lists.newArrayList();
	private final BlockingQueue field_178519_d = Queues.newArrayBlockingQueue(100);
	private final BlockingQueue field_178520_e = Queues.newArrayBlockingQueue(5);
	private final WorldVertexBufferUploader field_178517_f = new WorldVertexBufferUploader();
	private final VertexBufferUploader field_178518_g = new VertexBufferUploader();
	private final Queue field_178524_h = Queues.newArrayDeque();
	private final ChunkRenderWorker field_178525_i;
	// private static final String __OBFID = "http://https://fuckuskid00002463";

	public ChunkRenderDispatcher() {
		int var1;

		for (var1 = 0; var1 < 2; ++var1) {
			final ChunkRenderWorker var2 = new ChunkRenderWorker(this);
			final Thread var3 = field_178521_b.newThread(var2);
			var3.start();
			field_178522_c.add(var2);
		}

		for (var1 = 0; var1 < 5; ++var1) {
			field_178520_e.add(new RegionRenderCacheBuilder());
		}

		field_178525_i = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
	}

	public String func_178504_a() {
		return String.format("pC: %03d, pU: %1d, aB: %1d", new Object[] { field_178519_d.size(),
                    field_178524_h.size(), field_178520_e.size() });
	}

	public boolean func_178516_a(final long p_178516_1_) {
		boolean var3 = false;
		long var8;

		do {
			boolean var4 = false;
			synchronized (field_178524_h) {
				if (!field_178524_h.isEmpty()) {
					((ListenableFutureTask) field_178524_h.poll()).run();
					var4 = true;
					var3 = true;
				}
			}

			if (p_178516_1_ == 0L || !var4) {
				break;
			}

			var8 = p_178516_1_ - System.nanoTime();
		}
		while (var8 >= 0L && var8 <= 1000000000L);

		return var3;
	}

	public boolean func_178507_a(final RenderChunk p_178507_1_) {
		p_178507_1_.func_178579_c().lock();
		boolean var4;

		try {
			final ChunkCompileTaskGenerator var2 = p_178507_1_.func_178574_d();
			var2.func_178539_a(new Runnable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002462";
				@Override
				public void run() {
					field_178519_d.remove(var2);
				}
			});
			final boolean var3 = field_178519_d.offer(var2);

			if (!var3) {
				var2.func_178542_e();
			}

			var4 = var3;
		}
		finally {
			p_178507_1_.func_178579_c().unlock();
		}

		return var4;
	}

	public boolean func_178505_b(final RenderChunk p_178505_1_) {
		p_178505_1_.func_178579_c().lock();
		boolean var3;

		try {
			final ChunkCompileTaskGenerator var2 = p_178505_1_.func_178574_d();

			try {
				field_178525_i.func_178474_a(var2);
			} catch (final InterruptedException var8) {
			}

			var3 = true;
		}
		finally {
			p_178505_1_.func_178579_c().unlock();
		}

		return var3;
	}

	public void func_178514_b() {
		func_178513_e();

		while (func_178516_a(0L)) {
		}

		final ArrayList var1 = Lists.newArrayList();

		while (var1.size() != 5) {
			try {
				var1.add(func_178515_c());
			} catch (final InterruptedException var3) {
			}
		}

		field_178520_e.addAll(var1);
	}

	public void func_178512_a(final RegionRenderCacheBuilder p_178512_1_) {
		field_178520_e.add(p_178512_1_);
	}

	public RegionRenderCacheBuilder func_178515_c() throws InterruptedException, InterruptedException {
		return (RegionRenderCacheBuilder) field_178520_e.take();
	}

	public ChunkCompileTaskGenerator func_178511_d() throws InterruptedException, InterruptedException {
		return (ChunkCompileTaskGenerator) field_178519_d.take();
	}

	public boolean func_178509_c(final RenderChunk p_178509_1_) {
		p_178509_1_.func_178579_c().lock();
		boolean var4;

		try {
			final ChunkCompileTaskGenerator var2 = p_178509_1_.func_178582_e();
			boolean var3;

			if (var2 == null) {
				var3 = true;
				return var3;
			}

			var2.func_178539_a(new Runnable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002461";
				@Override
				public void run() {
					field_178519_d.remove(var2);
				}
			});
			var3 = field_178519_d.offer(var2);
			var4 = var3;
		}
		finally {
			p_178509_1_.func_178579_c().unlock();
		}

		return var4;
	}

	public ListenableFuture func_178503_a(final EnumWorldBlockLayer p_178503_1_, final WorldRenderer p_178503_2_,
			final RenderChunk p_178503_3_, final CompiledChunk p_178503_4_) {
		if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
			if (OpenGlHelper.func_176075_f()) {
				func_178506_a(p_178503_2_, p_178503_3_.func_178565_b(p_178503_1_.ordinal()));
			} else {
				func_178510_a(p_178503_2_, ((ListedRenderChunk) p_178503_3_).func_178600_a(p_178503_1_, p_178503_4_),
						p_178503_3_);
			}

			p_178503_2_.setTranslation(0.0D, 0.0D, 0.0D);
			return Futures.immediateFuture((Object) null);
		} else {
			final ListenableFutureTask var5 = ListenableFutureTask.create(new Runnable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002460";
				@Override
				public void run() {
					ChunkRenderDispatcher.this.func_178503_a(p_178503_1_, p_178503_2_, p_178503_3_, p_178503_4_);
				}
			}, (Object) null);
			synchronized (field_178524_h) {
				field_178524_h.add(var5);
				return var5;
			}
		}
	}

	private void func_178510_a(final WorldRenderer p_178510_1_, final int p_178510_2_, final RenderChunk p_178510_3_) {
		GL11.glNewList(p_178510_2_, GL11.GL_COMPILE);
		GlStateManager.pushMatrix();
		p_178510_3_.func_178572_f();
		field_178517_f.draw(p_178510_1_, p_178510_1_.func_178976_e());
		GlStateManager.popMatrix();
		GL11.glEndList();
	}

	private void func_178506_a(final WorldRenderer p_178506_1_, final VertexBuffer p_178506_2_) {
		field_178518_g.func_178178_a(p_178506_2_);
		field_178518_g.draw(p_178506_1_, p_178506_1_.func_178976_e());
	}

	public void func_178513_e() {
		while (!field_178519_d.isEmpty()) {
			final ChunkCompileTaskGenerator task = (ChunkCompileTaskGenerator) field_178519_d.poll();

			if (task != null) {
				task.func_178542_e();
			}
		}
	}
}
