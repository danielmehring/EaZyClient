package net.minecraft.client.resources;

import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class FallbackResourceManager implements IResourceManager {

public static final int EaZy = 872;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_177246_b = LogManager.getLogger();
	protected final List resourcePacks = Lists.newArrayList();
	private final IMetadataSerializer frmMetadataSerializer;
	// private static final String __OBFID = "http://https://fuckuskid00001074";

	public FallbackResourceManager(final IMetadataSerializer p_i1289_1_) {
		frmMetadataSerializer = p_i1289_1_;
	}

	public void addResourcePack(final IResourcePack p_110538_1_) {
		resourcePacks.add(p_110538_1_);
	}

	@Override
	public Set getResourceDomains() {
		return null;
	}

	@Override
	public IResource getResource(final ResourceLocation p_110536_1_) throws IOException {
		IResourcePack var2 = null;
		final ResourceLocation var3 = getLocationMcmeta(p_110536_1_);

		for (int var4 = resourcePacks.size() - 1; var4 >= 0; --var4) {
			final IResourcePack var5 = (IResourcePack) resourcePacks.get(var4);

			if (var2 == null && var5.resourceExists(var3)) {
				var2 = var5;
			}

			if (var5.resourceExists(p_110536_1_)) {
				InputStream var6 = null;

				if (var2 != null) {
					var6 = func_177245_a(var3, var2);
				}

				return new SimpleResource(var5.getPackName(), p_110536_1_, func_177245_a(p_110536_1_, var5), var6,
						frmMetadataSerializer);
			}
		}

		throw new FileNotFoundException(p_110536_1_.toString());
	}

	protected InputStream func_177245_a(final ResourceLocation p_177245_1_, final IResourcePack p_177245_2_)
			throws IOException {
		final InputStream var3 = p_177245_2_.getInputStream(p_177245_1_);
		return field_177246_b.isDebugEnabled() ? new FallbackResourceManager.ImputStreamLeakedResourceLogger(var3,
				p_177245_1_, p_177245_2_.getPackName()) : var3;
	}

	@Override
	public List getAllResources(final ResourceLocation p_135056_1_) throws IOException {
		final ArrayList var2 = Lists.newArrayList();
		final ResourceLocation var3 = getLocationMcmeta(p_135056_1_);
		final Iterator var4 = resourcePacks.iterator();

		while (var4.hasNext()) {
			final IResourcePack var5 = (IResourcePack) var4.next();

			if (var5.resourceExists(p_135056_1_)) {
				final InputStream var6 = var5.resourceExists(var3) ? func_177245_a(var3, var5) : null;
				var2.add(new SimpleResource(var5.getPackName(), p_135056_1_, func_177245_a(p_135056_1_, var5), var6,
						frmMetadataSerializer));
			}
		}

		if (var2.isEmpty()) {
			throw new FileNotFoundException(p_135056_1_.toString());
		} else {
			return var2;
		}
	}

	static ResourceLocation getLocationMcmeta(final ResourceLocation p_110537_0_) {
		return new ResourceLocation(p_110537_0_.getResourceDomain(), p_110537_0_.getResourcePath() + ".mcmeta");
	}

	static class ImputStreamLeakedResourceLogger extends InputStream {
		private final InputStream field_177330_a;
		private final String field_177328_b;
		private boolean field_177329_c = false;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002395";

		public ImputStreamLeakedResourceLogger(final InputStream p_i46093_1_, final ResourceLocation p_i46093_2_,
				final String p_i46093_3_) {
			field_177330_a = p_i46093_1_;
			final ByteArrayOutputStream var4 = new ByteArrayOutputStream();
			new Exception().printStackTrace(new PrintStream(var4));
			field_177328_b = "Leaked resource: \'" + p_i46093_2_ + "\' loaded from pack: \'" + p_i46093_3_ + "\'\n"
					+ var4.toString();
		}

		@Override
		public void close() throws IOException {
			field_177330_a.close();
			field_177329_c = true;
		}

		@Override
		protected void finalize() throws Throwable {
			if (!field_177329_c) {
				FallbackResourceManager.field_177246_b.warn(field_177328_b);
			}

			super.finalize();
		}

		@Override
		public int read() throws IOException {
			return field_177330_a.read();
		}
	}
}
