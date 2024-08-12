package net.minecraft.client.resources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.minecraft.MinecraftSessionService;

public class SkinManager {

public static final int EaZy = 901;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES,
			new LinkedBlockingQueue());
	private final TextureManager textureManager;
	private final File skinCacheDir;
	private final MinecraftSessionService sessionService;
	private final LoadingCache skinCacheLoader;
	// private static final String __OBFID = "http://https://fuckuskid00001830";

	public SkinManager(final TextureManager textureManagerInstance, final File skinCacheDirectory,
			final MinecraftSessionService sessionService) {
		textureManager = textureManagerInstance;
		skinCacheDir = skinCacheDirectory;
		this.sessionService = sessionService;
		skinCacheLoader = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001829";
			public Map func_152786_a(final GameProfile p_152786_1_) {
				return Minecraft.getMinecraft().getSessionService().getTextures(p_152786_1_, false);
			}

			@Override
			public Object load(final Object p_load_1_) {
				return func_152786_a((GameProfile) p_load_1_);
			}
		});
	}

	/**
	 * Used in the Skull renderer to fetch a skin. May download the skin if it's
	 * not in the cache
	 */
	public ResourceLocation loadSkin(final MinecraftProfileTexture p_152792_1_, final Type p_152792_2_) {
		return this.loadSkin(p_152792_1_, p_152792_2_, (SkinManager.SkinAvailableCallback) null);
	}

	/**
	 * May download the skin if its not in the cache, can be passed a
	 * SkinManager#SkinAvailableCallback for handling
	 */
	public ResourceLocation loadSkin(final MinecraftProfileTexture p_152789_1_, final Type p_152789_2_,
			final SkinManager.SkinAvailableCallback p_152789_3_) {
		final ResourceLocation var4 = new ResourceLocation("skins/" + p_152789_1_.getHash());
		final ITextureObject var5 = textureManager.getTexture(var4);

		if (var5 != null) {
			if (p_152789_3_ != null) {
				p_152789_3_.func_180521_a(p_152789_2_, var4, p_152789_1_);
			}
		} else {
			final File var6 = new File(skinCacheDir, p_152789_1_.getHash().substring(0, 2));
			final File var7 = new File(var6, p_152789_1_.getHash());
			final ImageBufferDownload var8 = p_152789_2_ == Type.SKIN ? new ImageBufferDownload() : null;
			final ThreadDownloadImageData var9 = new ThreadDownloadImageData(var7, p_152789_1_.getUrl(),
					DefaultPlayerSkin.func_177335_a(), new IImageBuffer() {
						// private static final String __OBFID =
						// "http://https://fuckuskid00001828";
						@Override
						public BufferedImage parseUserSkin(BufferedImage p_78432_1_) {
							if (var8 != null) {
								p_78432_1_ = var8.parseUserSkin(p_78432_1_);
							}

							return p_78432_1_;
						}

						@Override
						public void func_152634_a() {
							if (var8 != null) {
								var8.func_152634_a();
							}

							if (p_152789_3_ != null) {
								p_152789_3_.func_180521_a(p_152789_2_, var4, p_152789_1_);
							}
						}
					});
			textureManager.loadTexture(var4, var9);
		}

		return var4;
	}

	public void func_152790_a(final GameProfile p_152790_1_, final SkinManager.SkinAvailableCallback p_152790_2_,
			final boolean p_152790_3_) {
		THREAD_POOL.submit(new Runnable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001827";
			@Override
			public void run() {
				final HashMap var1 = Maps.newHashMap();

				try {
					var1.putAll(sessionService.getTextures(p_152790_1_, p_152790_3_));
				} catch (final InsecureTextureException var3) {
				}

				if (var1.isEmpty()
						&& p_152790_1_.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
					var1.putAll(sessionService.getTextures(sessionService.fillProfileProperties(p_152790_1_, false),
							false));
				}

				Minecraft.getMinecraft().addScheduledTask(new Runnable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00001826";
					@Override
					public void run() {
						if (var1.containsKey(Type.SKIN)) {
							SkinManager.this.loadSkin((MinecraftProfileTexture) var1.get(Type.SKIN), Type.SKIN,
									p_152790_2_);
						}

						if (var1.containsKey(Type.CAPE)) {
							SkinManager.this.loadSkin((MinecraftProfileTexture) var1.get(Type.CAPE), Type.CAPE,
									p_152790_2_);
						}
					}
				});
			}
		});
	}

	public Map loadSkinFromCache(final GameProfile p_152788_1_) {
		return (Map) skinCacheLoader.getUnchecked(p_152788_1_);
	}

	public interface SkinAvailableCallback {
		void func_180521_a(Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
	}
}
