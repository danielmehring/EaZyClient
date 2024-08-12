package net.minecraft.client.resources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

public class ResourcePackRepository {

public static final int EaZy = 898;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_177320_c = LogManager.getLogger();
	private static final FileFilter resourcePackFilter = new FileFilter() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001088";
		@Override
		public boolean accept(final File p_accept_1_) {
			final boolean var2 = p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip");
			final boolean var3 = p_accept_1_.isDirectory() && new File(p_accept_1_, "pack.mcmeta").isFile();
			return var2 || var3;
		}
	};
	private final File dirResourcepacks;
	public final IResourcePack rprDefaultResourcePack;
	private final File field_148534_e;
	public final IMetadataSerializer rprMetadataSerializer;
	private IResourcePack field_148532_f;
	private final ReentrantLock field_177321_h = new ReentrantLock();
	private ListenableFuture field_177322_i;
	private List repositoryEntriesAll = Lists.newArrayList();
	private final List repositoryEntries = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00001087";

	public ResourcePackRepository(final File p_i45101_1_, final File p_i45101_2_, final IResourcePack p_i45101_3_,
			final IMetadataSerializer p_i45101_4_, final GameSettings p_i45101_5_) {
		dirResourcepacks = p_i45101_1_;
		field_148534_e = p_i45101_2_;
		rprDefaultResourcePack = p_i45101_3_;
		rprMetadataSerializer = p_i45101_4_;
		fixDirResourcepacks();
		updateRepositoryEntriesAll();
		final Iterator var6 = p_i45101_5_.resourcePacks.iterator();

		while (var6.hasNext()) {
			final String var7 = (String) var6.next();
			final Iterator var8 = repositoryEntriesAll.iterator();

			while (var8.hasNext()) {
				final ResourcePackRepository.Entry var9 = (ResourcePackRepository.Entry) var8.next();

				if (var9.getResourcePackName().equals(var7)) {
					repositoryEntries.add(var9);
					break;
				}
			}
		}
	}

	private void fixDirResourcepacks() {
		if (!dirResourcepacks.isDirectory() && (!dirResourcepacks.delete() || !dirResourcepacks.mkdirs())) {
			field_177320_c.debug("Unable to create resourcepack folder: " + dirResourcepacks);
		}
	}

	private List getResourcePackFiles() {
		return dirResourcepacks.isDirectory() ? Arrays.asList(dirResourcepacks.listFiles(resourcePackFilter))
				: Collections.emptyList();
	}

	public void updateRepositoryEntriesAll() {
		final ArrayList var1 = Lists.newArrayList();
		Iterator var2 = getResourcePackFiles().iterator();

		while (var2.hasNext()) {
			final File var3 = (File) var2.next();
			final ResourcePackRepository.Entry var4 = new ResourcePackRepository.Entry(var3, null);

			if (!repositoryEntriesAll.contains(var4)) {
				try {
					var4.updateResourcePack();
					var1.add(var4);
				} catch (final Exception var6) {
					var1.remove(var4);
				}
			} else {
				final int var5 = repositoryEntriesAll.indexOf(var4);

				if (var5 > -1 && var5 < repositoryEntriesAll.size()) {
					var1.add(repositoryEntriesAll.get(var5));
				}
			}
		}

		repositoryEntriesAll.removeAll(var1);
		var2 = repositoryEntriesAll.iterator();

		while (var2.hasNext()) {
			final ResourcePackRepository.Entry var7 = (ResourcePackRepository.Entry) var2.next();
			var7.closeResourcePack();
		}

		repositoryEntriesAll = var1;
	}

	public List getRepositoryEntriesAll() {
		return ImmutableList.copyOf(repositoryEntriesAll);
	}

	public List getRepositoryEntries() {
		return ImmutableList.copyOf(repositoryEntries);
	}

	public void func_148527_a(final List p_148527_1_) {
		repositoryEntries.clear();
		repositoryEntries.addAll(p_148527_1_);
	}

	public File getDirResourcepacks() {
		return dirResourcepacks;
	}

	public ListenableFuture func_180601_a(final String p_180601_1_, final String p_180601_2_) {
		String var3;

		if (p_180601_2_.matches("^[a-f0-9]{40}$")) {
			var3 = p_180601_2_;
		} else {
			var3 = p_180601_1_.substring(p_180601_1_.lastIndexOf("/") + 1);

			if (var3.contains("?")) {
				var3 = var3.substring(0, var3.indexOf("?"));
			}

			if (!var3.endsWith(".zip")) {
				return Futures
						.immediateFailedFuture(new IllegalArgumentException("Invalid filename; must end in .zip"));
			}

			var3 = "legacy_" + var3.replaceAll("\\W", "");
		}

		final File var4 = new File(field_148534_e, var3);
		field_177321_h.lock();

		try {
			func_148529_f();

			if (var4.exists() && p_180601_2_.length() == 40) {
				try {
					final String var5 = Hashing.sha1().hashBytes(Files.toByteArray(var4)).toString();

					if (var5.equals(p_180601_2_)) {
						final ListenableFuture var16 = func_177319_a(var4);
						return var16;
					}

					field_177320_c.warn("File " + var4 + " had wrong hash (expected " + p_180601_2_ + ", found " + var5
							+ "). Deleting it.");
					FileUtils.deleteQuietly(var4);
				} catch (final IOException var13) {
					field_177320_c.warn("File " + var4 + " couldn\'t be hashed. Deleting it.", var13);
					FileUtils.deleteQuietly(var4);
				}
			}

			final GuiScreenWorking var15 = new GuiScreenWorking();
			final Map var6 = Minecraft.func_175596_ai();
			final Minecraft var7 = Minecraft.getMinecraft();
			Futures.getUnchecked(var7.addScheduledTask(new Runnable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00001089";
				@Override
				public void run() {
					var7.displayGuiScreen(var15);
				}
			}));
			final SettableFuture var8 = SettableFuture.create();
			field_177322_i = HttpUtil.func_180192_a(var4, p_180601_1_, var6, 52428800, var15, var7.getProxy());
			Futures.addCallback(field_177322_i, new FutureCallback() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002394";
				@Override
				public void onSuccess(final Object p_onSuccess_1_) {
					ResourcePackRepository.this.func_177319_a(var4);
					var8.set((Object) null);
				}

				@Override
				public void onFailure(final Throwable p_onFailure_1_) {
					var8.setException(p_onFailure_1_);
				}
			});
			final ListenableFuture var9 = field_177322_i;
			return var9;
		}
		finally {
			field_177321_h.unlock();
		}
	}

	public ListenableFuture func_177319_a(final File p_177319_1_) {
		field_148532_f = new FileResourcePack(p_177319_1_);
		return Minecraft.getMinecraft().func_175603_A();
	}

	/**
	 * Getter for the IResourcePack instance associated with this
	 * ResourcePackRepository
	 */
	public IResourcePack getResourcePackInstance() {
		return field_148532_f;
	}

	public void func_148529_f() {
		field_177321_h.lock();

		try {
			if (field_177322_i != null) {
				field_177322_i.cancel(true);
			}

			field_177322_i = null;
			field_148532_f = null;
		}
		finally {
			field_177321_h.unlock();
		}
	}

	public class Entry {
		private final File resourcePackFile;
		private IResourcePack reResourcePack;
		private PackMetadataSection rePackMetadataSection;
		private BufferedImage texturePackIcon;
		private ResourceLocation locationTexturePackIcon;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001090";

		private Entry(final File p_i1295_2_) {
			resourcePackFile = p_i1295_2_;
		}

		public void updateResourcePack() throws IOException {
			reResourcePack = resourcePackFile.isDirectory() ? new FolderResourcePack(resourcePackFile)
					: new FileResourcePack(resourcePackFile);
			rePackMetadataSection = (PackMetadataSection) reResourcePack.getPackMetadata(rprMetadataSerializer, "pack");

			try {
				texturePackIcon = reResourcePack.getPackImage();
			} catch (final IOException var2) {
			}

			if (texturePackIcon == null) {
				texturePackIcon = rprDefaultResourcePack.getPackImage();
			}

			closeResourcePack();
		}

		public void bindTexturePackIcon(final TextureManager p_110518_1_) {
			if (locationTexturePackIcon == null) {
				locationTexturePackIcon = p_110518_1_.getDynamicTextureLocation("texturepackicon",
						new DynamicTexture(texturePackIcon));
			}

			p_110518_1_.bindTexture(locationTexturePackIcon);
		}

		public void closeResourcePack() {
			if (reResourcePack instanceof Closeable) {
				IOUtils.closeQuietly((Closeable) reResourcePack);
			}
		}

		public IResourcePack getResourcePack() {
			return reResourcePack;
		}

		public String getResourcePackName() {
			return reResourcePack.getPackName();
		}

		public String getTexturePackDescription() {
			return rePackMetadataSection == null
					? EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing \'pack\' section)"
					: rePackMetadataSection.func_152805_a().getFormattedText();
		}

		@Override
		public boolean equals(final Object p_equals_1_) {
			return this == p_equals_1_ ? true
					: p_equals_1_ instanceof ResourcePackRepository.Entry ? toString().equals(p_equals_1_.toString())
							: false;
		}

		@Override
		public int hashCode() {
			return toString().hashCode();
		}

		@Override
		public String toString() {
			return String.format("%s:%s:%d", new Object[] { resourcePackFile.getName(),
					resourcePackFile.isDirectory() ? "folder" : "zip", resourcePackFile.lastModified() });
		}

		Entry(final File p_i1296_2_, final Object p_i1296_3_) {
			this(p_i1296_2_);
		}
	}
}
