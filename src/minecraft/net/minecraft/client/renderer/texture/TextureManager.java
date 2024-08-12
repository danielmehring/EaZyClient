package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import optifine.Config;
import optifine.RandomMobs;
import shadersmod.client.ShadersTex;

public class TextureManager implements ITickable, IResourceManagerReloadListener {

public static final int EaZy = 825;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private final Map mapTextureObjects = Maps.newHashMap();
	private final List listTickables = Lists.newArrayList();
	private final Map mapTextureCounters = Maps.newHashMap();
	private final IResourceManager theResourceManager;
	// private static final String __OBFID = "http://https://fuckuskid00001064";

	public TextureManager(final IResourceManager p_i1284_1_) {
		theResourceManager = p_i1284_1_;
	}

	public void bindTexture(ResourceLocation resource) {
		if (Config.isRandomMobs()) {
			resource = RandomMobs.getTextureLocation(resource);
		}

		Object var2 = mapTextureObjects.get(resource);

		if (var2 == null) {
			var2 = new SimpleTexture(resource);
			loadTexture(resource, (ITextureObject) var2);
		}

		if (Config.isShaders()) {
			ShadersTex.bindTexture((ITextureObject) var2);
		} else {
			TextureUtil.bindTexture(((ITextureObject) var2).getGlTextureId());
		}
	}

	public boolean loadTickableTexture(final ResourceLocation p_110580_1_, final ITickableTextureObject p_110580_2_) {
		if (loadTexture(p_110580_1_, p_110580_2_)) {
			listTickables.add(p_110580_2_);
			return true;
		} else {
			return false;
		}
	}

	public boolean loadTexture(final ResourceLocation p_110579_1_, final ITextureObject p_110579_2_) {
		boolean var3 = true;
		Object p_110579_2_2 = p_110579_2_;

		try {
			p_110579_2_.loadTexture(theResourceManager);
		} catch (final IOException var8) {
			logger.warn("Failed to load texture: " + p_110579_1_, var8);
			p_110579_2_2 = TextureUtil.missingTexture;
			mapTextureObjects.put(p_110579_1_, p_110579_2_2);
			var3 = false;
		} catch (final Throwable var9) {
			final CrashReport var5 = CrashReport.makeCrashReport(var9, "Registering texture");
			final CrashReportCategory var6 = var5.makeCategory("Resource location being registered");
			var6.addCrashSection("Resource location", p_110579_1_);
			var6.addCrashSectionCallable("Texture object class", new Callable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00001065";
				@Override
				public String call() {
					return p_110579_2_.getClass().getName();
				}
			});
			throw new ReportedException(var5);
		}

		mapTextureObjects.put(p_110579_1_, p_110579_2_2);
		return var3;
	}

	public ITextureObject getTexture(final ResourceLocation p_110581_1_) {
		return (ITextureObject) mapTextureObjects.get(p_110581_1_);
	}

	public ResourceLocation getDynamicTextureLocation(final String p_110578_1_, DynamicTexture p_110578_2_) {
		if (p_110578_1_.equals("logo")) {
			p_110578_2_ = Config.getMojangLogoTexture(p_110578_2_);
		}

		Integer var3 = (Integer) mapTextureCounters.get(p_110578_1_);

		if (var3 == null) {
			var3 = 1;
		} else {
			var3 = var3 + 1;
		}

		mapTextureCounters.put(p_110578_1_, var3);
		final ResourceLocation var4 = new ResourceLocation(
				String.format("dynamic/%s_%d", new Object[] { p_110578_1_, var3 }));
		loadTexture(var4, p_110578_2_);
		return var4;
	}

	@Override
	public void tick() {
		final Iterator var1 = listTickables.iterator();

		while (var1.hasNext()) {
			final ITickable var2 = (ITickable) var1.next();
			var2.tick();
		}
	}

	public void deleteTexture(final ResourceLocation p_147645_1_) {
		final ITextureObject var2 = getTexture(p_147645_1_);

		if (var2 != null) {
			TextureUtil.deleteTexture(var2.getGlTextureId());
		}
	}

	@Override
	public void onResourceManagerReload(final IResourceManager resourceManager) {
		Config.dbg("*** Reloading textures ***");
		Config.log("Resource packs: " + Config.getResourcePackNames());
		final Iterator it = mapTextureObjects.keySet().iterator();

		while (it.hasNext()) {
			final ResourceLocation var2 = (ResourceLocation) it.next();

			if (var2.getResourcePath().startsWith("mcpatcher/")) {
				final ITextureObject var3 = (ITextureObject) mapTextureObjects.get(var2);

				if (var3 instanceof AbstractTexture) {
					final AbstractTexture at = (AbstractTexture) var3;
					at.deleteGlTexture();
				}

				it.remove();
			}
		}

		final Iterator var21 = mapTextureObjects.entrySet().iterator();

		while (var21.hasNext()) {
			final Entry var31 = (Entry) var21.next();
			loadTexture((ResourceLocation) var31.getKey(), (ITextureObject) var31.getValue());
		}
	}
}
