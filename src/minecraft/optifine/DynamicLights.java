package optifine;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DynamicLights {

public static final int EaZy = 1898;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static Map<Integer, DynamicLight> mapDynamicLights = new HashMap();
	private static long timeUpdateMs = 0L;

	public static void entityAdded(final Entity entityIn, final RenderGlobal renderGlobal) {}

	public static void entityRemoved(final Entity entityIn, final RenderGlobal renderGlobal) {
		synchronized (mapDynamicLights) {
			final DynamicLight dynamicLight = mapDynamicLights.remove(IntegerCache.valueOf(entityIn.getEntityId()));

			if (dynamicLight != null) {
				dynamicLight.updateLitChunks(renderGlobal);
			}
		}
	}

	public static void update(final RenderGlobal renderGlobal) {
		final long timeNowMs = System.currentTimeMillis();

		if (timeNowMs >= timeUpdateMs + 50L) {
			timeUpdateMs = timeNowMs;
			synchronized (mapDynamicLights) {
				updateMapDynamicLights(renderGlobal);

				if (mapDynamicLights.size() > 0) {
					final Collection dynamicLights = mapDynamicLights.values();
					final Iterator it = dynamicLights.iterator();

					while (it.hasNext()) {
						final DynamicLight dynamicLight = (DynamicLight) it.next();
						dynamicLight.update(renderGlobal);
					}
				}
			}
		}
	}

	private static void updateMapDynamicLights(final RenderGlobal renderGlobal) {
		final WorldClient world = renderGlobal.getWorld();

		if (world != null) {
			final List entities = world.getLoadedEntityList();
			final Iterator it = entities.iterator();

			while (it.hasNext()) {
				final Entity entity = (Entity) it.next();
				final int lightLevel = getLightLevel(entity);
				Integer key;
				DynamicLight dynamicLight;

				if (lightLevel > 0) {
					key = IntegerCache.valueOf(entity.getEntityId());
					dynamicLight = mapDynamicLights.get(key);

					if (dynamicLight == null) {
						dynamicLight = new DynamicLight(entity);
						mapDynamicLights.put(key, dynamicLight);
					}
				} else {
					key = IntegerCache.valueOf(entity.getEntityId());
					dynamicLight = mapDynamicLights.remove(key);

					if (dynamicLight != null) {
						dynamicLight.updateLitChunks(renderGlobal);
					}
				}
			}
		}
	}

	public static int getCombinedLight(final BlockPos pos, int combinedLight) {
		final double lightPlayer = getLightLevel(pos);
		combinedLight = getCombinedLight(lightPlayer, combinedLight);
		return combinedLight;
	}

	public static int getCombinedLight(final Entity entity, int combinedLight) {
		final double lightPlayer = getLightLevel(entity);
		combinedLight = getCombinedLight(lightPlayer, combinedLight);
		return combinedLight;
	}

	public static int getCombinedLight(final double lightPlayer, int combinedLight) {
		if (lightPlayer > 0.0D) {
			final int lightPlayerFF = (int) (lightPlayer * 16.0D);
			final int lightBlockFF = combinedLight & 255;

			if (lightPlayerFF > lightBlockFF) {
				combinedLight &= -256;
				combinedLight |= lightPlayerFF;
			}
		}

		return combinedLight;
	}

	public static double getLightLevel(final BlockPos pos) {
		double lightLevelMax = 0.0D;
		synchronized (mapDynamicLights) {
			final Collection dynamicLights = mapDynamicLights.values();
			final Iterator it = dynamicLights.iterator();

			while (true) {
				if (!it.hasNext()) {
					break;
				}

				final DynamicLight dynamicLight = (DynamicLight) it.next();
				int dynamicLightLevel = dynamicLight.getLastLightLevel();

				if (dynamicLightLevel > 0) {
					final double px = dynamicLight.getLastPosX();
					final double py = dynamicLight.getLastPosY();
					final double pz = dynamicLight.getLastPosZ();
					final double dx = pos.getX() - px;
					final double dy = pos.getY() - py;
					final double dz = pos.getZ() - pz;
					double distSq = dx * dx + dy * dy + dz * dz;

					if (dynamicLight.isUnderwater() && !Config.isClearWater()) {
						dynamicLightLevel = Config.limit(dynamicLightLevel - 2, 0, 15);
						distSq *= 2.0D;
					}

					if (distSq <= 56.25D) {
						final double dist = Math.sqrt(distSq);
						final double light = 1.0D - dist / 7.5D;
						final double lightLevel = light * dynamicLightLevel;

						if (lightLevel > lightLevelMax) {
							lightLevelMax = lightLevel;
						}
					}
				}
			}
		}

		final double lightPlayer1 = Config.limit(lightLevelMax, 0.0D, 15.0D);
		return lightPlayer1;
	}

	public static int getLightLevel(final ItemStack itemStack) {
		if (itemStack == null) {
			return 0;
		} else {
			final Item item = itemStack.getItem();

			if (item instanceof ItemBlock) {
				final ItemBlock itemBlock = (ItemBlock) item;
				final Block block = itemBlock.getBlock();

				if (block != null) {
					return block.getLightValue();
				}
			}

			return item == Items.lava_bucket ? Blocks.lava.getLightValue()
					: item != Items.blaze_rod && item != Items.blaze_powder
							? item == Items.glowstone_dust ? 8
									: item == Items.prismarine_crystals ? 8
											: item == Items.magma_cream ? 8
													: item == Items.nether_star ? Blocks.beacon.getLightValue() / 2 : 0
							: 10;
		}
	}

	public static int getLightLevel(final Entity entity) {
		if (entity == Config.getMinecraft().func_175606_aa() && !Config.isDynamicHandLight()) {
			return 0;
		} else {
			if (entity instanceof EntityPlayer) {
				final EntityPlayer entityItem = (EntityPlayer) entity;

				if (entityItem.isSpectatorMode()) {
					return 0;
				}
			}

			if (entity.isBurning()) {
				return 15;
			} else if (entity instanceof EntityFireball) {
				return 15;
			} else if (entity instanceof EntityTNTPrimed) {
				return 15;
			} else if (entity instanceof EntityBlaze) {
				final EntityBlaze entityItem5 = (EntityBlaze) entity;
				return entityItem5.func_70845_n() ? 15 : 10;
			} else if (entity instanceof EntityMagmaCube) {
				final EntityMagmaCube entityItem4 = (EntityMagmaCube) entity;
				return entityItem4.squishFactor > 0.6D ? 13 : 8;
			} else {
				if (entity instanceof EntityCreeper) {
					final EntityCreeper entityItem1 = (EntityCreeper) entity;

					if (entityItem1.getCreeperFlashIntensity(0.0F) > 0.001D) {
						return 15;
					}
				}

				ItemStack itemStack;

				if (entity instanceof EntityLivingBase) {
					final EntityLivingBase entityItem3 = (EntityLivingBase) entity;
					itemStack = entityItem3.getHeldItem();
					final int levelMain = getLightLevel(itemStack);
					final ItemStack stackHead = entityItem3.getEquipmentInSlot(4);
					final int levelHead = getLightLevel(stackHead);
					return Math.max(levelMain, levelHead);
				} else if (entity instanceof EntityItem) {
					final EntityItem entityItem2 = (EntityItem) entity;
					itemStack = getItemStack(entityItem2);
					return getLightLevel(itemStack);
				} else {
					return 0;
				}
			}
		}
	}

	public static void removeLights(final RenderGlobal renderGlobal) {
		synchronized (mapDynamicLights) {
			final Collection lights = mapDynamicLights.values();
			final Iterator it = lights.iterator();

			while (it.hasNext()) {
				final DynamicLight dynamicLight = (DynamicLight) it.next();
				it.remove();
				dynamicLight.updateLitChunks(renderGlobal);
			}
		}
	}

	public static void clear() {
		synchronized (mapDynamicLights) {
			mapDynamicLights.clear();
		}
	}

	public static int getCount() {
		synchronized (mapDynamicLights) {
			return mapDynamicLights.size();
		}
	}

	public static ItemStack getItemStack(final EntityItem entityItem) {
		final ItemStack itemstack = entityItem.getDataWatcher().getWatchableObjectItemStack(10);
		return itemstack;
	}
}
