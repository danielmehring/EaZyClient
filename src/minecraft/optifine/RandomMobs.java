package optifine;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class RandomMobs {

public static final int EaZy = 1950;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static Map locationProperties = new HashMap();
	private static RenderGlobal renderGlobal = null;
	private static boolean initialized = false;
	private static Random random = new Random();
	private static boolean working = false;
	public static final String SUFFIX_PNG = ".png";
	public static final String SUFFIX_PROPERTIES = ".properties";
	public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
	public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
	private static final String[] DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting",
			"_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };

	public static void entityLoaded(final Entity entity, final World world) {
		if (entity instanceof EntityLiving) {
			if (world != null) {
				final EntityLiving el = (EntityLiving) entity;
				el.spawnPosition = el.getPosition();
				el.spawnBiome = world.getBiomeGenForCoords(el.spawnPosition);
				final WorldServer ws = Config.getWorldServer();

				if (ws != null) {
					final Entity es = ws.getEntityByID(entity.getEntityId());

					if (es instanceof EntityLiving) {
						final EntityLiving els = (EntityLiving) es;
						final UUID uuid = els.getUniqueID();
						final long uuidLow = uuid.getLeastSignificantBits();
						final int id = (int) (uuidLow & 2147483647L);
						el.randomMobsId = id;
					}
				}
			}
		}
	}

	public static void worldChanged(final World oldWorld, final World newWorld) {
		if (newWorld != null) {
			final List entityList = newWorld.getLoadedEntityList();

			for (int e = 0; e < entityList.size(); ++e) {
				final Entity entity = (Entity) entityList.get(e);
				entityLoaded(entity, newWorld);
			}
		}
	}

	public static ResourceLocation getTextureLocation(final ResourceLocation loc) {
		if (working) {
			return loc;
		} else {
			ResourceLocation var5;

			try {
				working = true;

				if (!initialized) {
					initialize();
				}

				if (renderGlobal == null) {
					final ResourceLocation entity1 = loc;
					return entity1;
				}

				final Entity entity = renderGlobal.renderedEntity;

				if (!(entity instanceof EntityLiving)) {
					final ResourceLocation entityLiving1 = loc;
					return entityLiving1;
				}

				final EntityLiving entityLiving = (EntityLiving) entity;
				final String name = loc.getResourcePath();

				if (!name.startsWith("textures/entity/")) {
					final ResourceLocation props1 = loc;
					return props1;
				}

				final RandomMobsProperties props = getProperties(loc);

				if (props != null) {
					var5 = props.getTextureLocation(loc, entityLiving);
					return var5;
				}

				var5 = loc;
			}
			finally {
				working = false;
			}

			return var5;
		}
	}

	private static RandomMobsProperties getProperties(final ResourceLocation loc) {
		final String name = loc.getResourcePath();
		RandomMobsProperties props = (RandomMobsProperties) locationProperties.get(name);

		if (props == null) {
			props = makeProperties(loc);
			locationProperties.put(name, props);
		}

		return props;
	}

	private static RandomMobsProperties makeProperties(final ResourceLocation loc) {
		final String path = loc.getResourcePath();
		final ResourceLocation propLoc = getPropertyLocation(loc);

		if (propLoc != null) {
			final RandomMobsProperties variants = parseProperties(propLoc, loc);

			if (variants != null) {
				return variants;
			}
		}

		final ResourceLocation[] variants1 = getTextureVariants(loc);
		return new RandomMobsProperties(path, variants1);
	}

	private static RandomMobsProperties parseProperties(final ResourceLocation propLoc, final ResourceLocation resLoc) {
		try {
			final String e = propLoc.getResourcePath();
			Config.dbg("RandomMobs: " + resLoc.getResourcePath() + ", variants: " + e);
			final InputStream in = Config.getResourceStream(propLoc);

			if (in == null) {
				Config.warn("RandomMobs properties not found: " + e);
				return null;
			} else {
				final Properties props = new Properties();
				props.load(in);
				in.close();
				final RandomMobsProperties rmp = new RandomMobsProperties(props, e, resLoc);
				return !rmp.isValid(e) ? null : rmp;
			}
		} catch (final FileNotFoundException var6) {
			Config.warn("RandomMobs file not found: " + resLoc.getResourcePath());
			return null;
		} catch (final IOException var7) {
			var7.printStackTrace();
			return null;
		}
	}

	private static ResourceLocation getPropertyLocation(final ResourceLocation loc) {
		final ResourceLocation locMcp = getMcpatcherLocation(loc);

		if (locMcp == null) {
			return null;
		} else {
			final String domain = locMcp.getResourceDomain();
			final String path = locMcp.getResourcePath();
			String pathBase = path;

			if (path.endsWith(".png")) {
				pathBase = path.substring(0, path.length() - ".png".length());
			}

			final String pathProps = pathBase + ".properties";
			final ResourceLocation locProps = new ResourceLocation(domain, pathProps);

			if (Config.hasResource(locProps)) {
				return locProps;
			} else {
				final String pathParent = getParentPath(pathBase);

				if (pathParent == null) {
					return null;
				} else {
					final ResourceLocation locParentProps = new ResourceLocation(domain, pathParent + ".properties");
					return Config.hasResource(locParentProps) ? locParentProps : null;
				}
			}
		}
	}

	public static ResourceLocation getMcpatcherLocation(final ResourceLocation loc) {
		final String path = loc.getResourcePath();

		if (!path.startsWith("textures/entity/")) {
			return null;
		} else {
			final String pathMcp = "mcpatcher/mob/" + path.substring("textures/entity/".length());
			return new ResourceLocation(loc.getResourceDomain(), pathMcp);
		}
	}

	public static ResourceLocation getLocationIndexed(final ResourceLocation loc, final int index) {
		if (loc == null) {
			return null;
		} else {
			final String path = loc.getResourcePath();
			final int pos = path.lastIndexOf(46);

			if (pos < 0) {
				return null;
			} else {
				final String prefix = path.substring(0, pos);
				final String suffix = path.substring(pos);
				final String pathNew = prefix + index + suffix;
				final ResourceLocation locNew = new ResourceLocation(loc.getResourceDomain(), pathNew);
				return locNew;
			}
		}
	}

	private static String getParentPath(final String path) {
		for (final String suffix : DEPENDANT_SUFFIXES) {
			if (path.endsWith(suffix)) {
				final String pathParent = path.substring(0, path.length() - suffix.length());
				return pathParent;
			}
		}

		return null;
	}

	private static ResourceLocation[] getTextureVariants(final ResourceLocation loc) {
		final ArrayList list = new ArrayList();
		list.add(loc);
		final ResourceLocation locMcp = getMcpatcherLocation(loc);

		if (locMcp == null) {
			return null;
		} else {
			for (int locs = 1; locs < list.size() + 10; ++locs) {
				final int index = locs + 1;
				final ResourceLocation locIndex = getLocationIndexed(locMcp, index);

				if (Config.hasResource(locIndex)) {
					list.add(locIndex);
				}
			}

			if (list.size() <= 1) {
				return null;
			} else {
				final ResourceLocation[] var6 = (ResourceLocation[]) list.toArray(new ResourceLocation[list.size()]);
				Config.dbg("RandomMobs: " + loc.getResourcePath() + ", variants: " + var6.length);
				return var6;
			}
		}
	}

	public static void resetTextures() {
		locationProperties.clear();

		if (Config.isRandomMobs()) {
			initialize();
		}
	}

	private static void initialize() {
		renderGlobal = Config.getRenderGlobal();

		if (renderGlobal != null) {
			initialized = true;
			final ArrayList list = new ArrayList();
			list.add("bat");
			list.add("blaze");
			list.add("cat/black");
			list.add("cat/ocelot");
			list.add("cat/red");
			list.add("cat/siamese");
			list.add("chicken");
			list.add("cow/cow");
			list.add("cow/mooshroom");
			list.add("creeper/creeper");
			list.add("enderman/enderman");
			list.add("enderman/enderman_eyes");
			list.add("ghast/ghast");
			list.add("ghast/ghast_shooting");
			list.add("iron_golem");
			list.add("pig/pig");
			list.add("sheep/sheep");
			list.add("sheep/sheep_fur");
			list.add("silverfish");
			list.add("skeleton/skeleton");
			list.add("skeleton/wither_skeleton");
			list.add("slime/slime");
			list.add("slime/magmacube");
			list.add("snowman");
			list.add("spider/cave_spider");
			list.add("spider/spider");
			list.add("spider_eyes");
			list.add("squid");
			list.add("villager/villager");
			list.add("villager/butcher");
			list.add("villager/farmer");
			list.add("villager/librarian");
			list.add("villager/priest");
			list.add("villager/smith");
			list.add("wither/wither");
			list.add("wither/wither_armor");
			list.add("wither/wither_invulnerable");
			list.add("wolf/wolf");
			list.add("wolf/wolf_angry");
			list.add("wolf/wolf_collar");
			list.add("wolf/wolf_tame");
			list.add("zombie_pigman");
			list.add("zombie/zombie");
			list.add("zombie/zombie_villager");

			for (int i = 0; i < list.size(); ++i) {
				final String name = (String) list.get(i);
				final String tex = "textures/entity/" + name + ".png";
				final ResourceLocation texLoc = new ResourceLocation(tex);

				if (!Config.hasResource(texLoc)) {
					Config.warn("Not found: " + texLoc);
				}

				getProperties(texLoc);
			}
		}
	}
}
