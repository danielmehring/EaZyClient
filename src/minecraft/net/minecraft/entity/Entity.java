package net.minecraft.entity;

import me.EaZy.client.events.EventOpaqueBlock;
import me.EaZy.client.events.EventSafewalk;
import me.EaZy.client.events.EventStep;
import me.EaZy.client.main.Client;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

import com.darkmagician6.eventapi.EventManager;

public abstract class Entity implements ICommandSender {

	public static final int EaZy = 1108;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final AxisAlignedBB field_174836_a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	private static int nextEntityID;
	private int entityId;
	public double renderDistanceWeight;

	/**
	 * Blocks entities from spawning when they do their AABB check to make sure
	 * the spot is clear of entities that can prevent spawning.
	 */
	public boolean preventEntitySpawning;

	/**
	 * The entity that is riding this entity
	 */
	public Entity riddenByEntity;

	/**
	 * The entity we are currently riding
	 */
	public Entity ridingEntity;
	public boolean forceSpawn;

	/**
	 * Reference to the World object.
	 */
	public World worldObj;
	public double prevPosX;
	public double prevPosY;
	public double prevPosZ;

	/**
	 * Entity position X
	 */
	public double posX;

	/**
	 * Entity position Y
	 */
	public double posY;

	/**
	 * Entity position Z
	 */
	public double posZ;

	/**
	 * Entity motion X
	 */
	public double motionX;

	/**
	 * Entity motion Y
	 */
	public double motionY;

	/**
	 * Entity motion Z
	 */
	public double motionZ;

	/**
	 * Entity rotation Yaw
	 */
	public float rotationYaw;

	/**
	 * Entity rotation Pitch
	 */
	public float rotationPitch;
	public float prevRotationYaw;
	public float prevRotationPitch;

	/**
	 * Axis aligned bounding box.
	 */
	public AxisAlignedBB boundingBox;
	public boolean onGround;

	/**
	 * True if after a move this entity has collided with something on X- or
	 * Z-axis
	 */
	public boolean isCollidedHorizontally;

	/**
	 * True if after a move this entity has collided with something on Y-axis
	 */
	public boolean isCollidedVertically;

	/**
	 * True if after a move this entity has collided with something either
	 * vertically or horizontally
	 */
	public boolean isCollided;
	public boolean velocityChanged;
	public boolean isInWeb;
	private boolean isOutsideBorder;

	/**
	 * gets set by setEntityDead, so this must be the flag whether an Entity is
	 * dead (inactive may be better term)
	 */
	public boolean isDead;

	/**
	 * How wide this entity is considered to be
	 */
	public float width;

	/**
	 * How high this entity is considered to be
	 */
	public float height;

	/**
	 * The previous ticks distance walked multiplied by 0.6
	 */
	public float prevDistanceWalkedModified;

	/**
	 * The distance walked multiplied by 0.6
	 */
	public float distanceWalkedModified;
	public float distanceWalkedOnStepModified;
	public float fallDistance;

	/**
	 * The distance that has to be exceeded in order to triger a new step sound
	 * and an onEntityWalking event on a block
	 */
	private int nextStepDistance;

	/**
	 * The entity's X coordinate at the previous tick, used to calculate
	 * position during rendering routines
	 */
	public double lastTickPosX;

	/**
	 * The entity's Y coordinate at the previous tick, used to calculate
	 * position during rendering routines
	 */
	public double lastTickPosY;

	/**
	 * The entity's Z coordinate at the previous tick, used to calculate
	 * position during rendering routines
	 */
	public double lastTickPosZ;

	/**
	 * How high this entity can step up when running into a block to try to get
	 * over it (currently make note the entity will always step up this amount
	 * and not just the amount needed)
	 */
	public float stepHeight;

	/**
	 * Whether this entity won't clip with collision or not (make note it won't
	 * disable gravity)
	 */
	public boolean noClip;

	/**
	 * Reduces the velocity applied by entity collisions by the specified
	 * percent.
	 */
	public float entityCollisionReduction;
	protected Random rand;

	/**
	 * How many ticks has this entity had ran since being alive
	 */
	public int ticksExisted;

	/**
	 * The amount of ticks you have to stand inside of fire before be set on
	 * fire
	 */
	public int fireResistance;
	private int fire;

	/**
	 * Whether this entity is currently inside of water (if it handles water
	 * movement that is)
	 */
	protected boolean inWater;

	/**
	 * Remaining time an entity will be "immune" to further damage after being
	 * hurt.
	 */
	public int hurtResistantTime;
	protected boolean firstUpdate;
	protected boolean isImmuneToFire;
	protected DataWatcher dataWatcher;
	private double entityRiderPitchDelta;
	private double entityRiderYawDelta;

	/**
	 * Has this entity been added to the chunk its within
	 */
	public boolean addedToChunk;
	public int chunkCoordX;
	public int chunkCoordY;
	public int chunkCoordZ;
	public int serverPosX;
	public int serverPosY;
	public int serverPosZ;

	/**
	 * Render entity even if it is outside the camera frustum. Only true in
	 * EntityFish for now. Used in RenderGlobal: render if ignoreFrustumCheck or
	 * in frustum.
	 */
	public boolean ignoreFrustumCheck;
	public boolean isAirBorne;
	public int timeUntilPortal;

	/**
	 * Whether the entity is inside a Portal
	 */
	protected boolean inPortal;
	protected int portalCounter;

	/**
	 * Which dimension the player is in (-1 = the Nether, 0 = normal world)
	 */
	public int dimension;
	protected int teleportDirection;
	private boolean invulnerable;
	protected UUID entityUniqueID;
	private final CommandResultStats field_174837_as;

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(final int id) {
		entityId = id;
	}

	public void func_174812_G() {
		setDead();
	}

	public Entity(final World worldIn) {
		entityId = nextEntityID++;
		renderDistanceWeight = 1.0D;
		boundingBox = field_174836_a;
		width = 0.6F;
		height = 1.8F;
		nextStepDistance = 1;
		rand = new Random();
		fireResistance = 1;
		firstUpdate = true;
		entityUniqueID = MathHelper.func_180182_a(rand);
		field_174837_as = new CommandResultStats();
		worldObj = worldIn;
		setPosition(0.0D, 0.0D, 0.0D);

		if (worldIn != null) {
			dimension = worldIn.provider.getDimensionId();
		}

		dataWatcher = new DataWatcher(this);
		dataWatcher.addObject(0, (byte) 0);
		dataWatcher.addObject(1, (short) 300);
		dataWatcher.addObject(3, (byte) 0);
		dataWatcher.addObject(2, "");
		dataWatcher.addObject(4, (byte) 0);
		entityInit();
	}

	protected abstract void entityInit();

	public DataWatcher getDataWatcher() {
		return dataWatcher;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		return p_equals_1_ instanceof Entity ? ((Entity) p_equals_1_).entityId == entityId : false;
	}

	@Override
	public int hashCode() {
		return entityId;
	}

	/**
	 * Keeps moving the entity up so it isn't colliding with blocks and other
	 * requirements for this entity to be spawned (only actually used on players
	 * though its also on Entity)
	 */
	protected void preparePlayerToSpawn() {
		if (worldObj != null) {
			while (posY > 0.0D && posY < 256.0D) {
				setPosition(posX, posY, posZ);

				if (worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) {
					break;
				}

				++posY;
			}

			motionX = motionY = motionZ = 0.0D;
			rotationPitch = 0.0F;

		}
	}

	/**
	 * Will get destroyed next tick.
	 */
	public void setDead() {
		isDead = true;
	}

	/**
	 * Sets the width and height of the entity. Args: width, height
	 */
	protected void setSize(final float width, final float height) {
		if (width != this.width || height != this.height) {
			final float var3 = this.width;
			this.width = width;
			this.height = height;
			func_174826_a(new AxisAlignedBB(getEntityBoundingBox().minX, getEntityBoundingBox().minY,
					getEntityBoundingBox().minZ, getEntityBoundingBox().minX + this.width,
					getEntityBoundingBox().minY + this.height, getEntityBoundingBox().minZ + this.width));

			if (this.width > var3 && !firstUpdate && !worldObj.isRemote) {
				moveEntity(var3 - this.width, 0.0D, var3 - this.width);
			}
		}
	}

	/**
	 * Sets the rotation of the entity. Args: yaw, pitch (both in degrees)
	 */
	protected void setRotation(final float yaw, final float pitch) {
		rotationYaw = yaw % 360.0F;
		rotationPitch = pitch % 360.0F;
	}

	public EnumFacing getFacingDirection() {
		return EnumFacing.getHorizontal(MathHelper.floor_double(rotationYaw * 4.0F / 360.0F + 0.5D) & 3);
	}

	/**
	 * Sets the x,y,z of the entity from the given parameters. Also seems to set
	 * up a bounding box.
	 */
	public void setPosition(final double x, final double y, final double z) {
		posX = x;
		posY = y;
		posZ = z;
		final float var7 = width / 2.0F;
		final float var8 = height;
		func_174826_a(new AxisAlignedBB(x - var7, y, z - var7, x + var7, y + var8, z + var7));
	}

	/**
	 * Adds 15% to the entity's yaw and subtracts 15% from the pitch. Clamps
	 * pitch from -90 to 90. Both arguments in degrees.
	 */
	public void setAngles(final float yaw, final float pitch) {
		final float var3 = rotationPitch;
		final float var4 = rotationYaw;
		rotationYaw = (float) (rotationYaw + yaw * 0.15D);
		rotationPitch = (float) (rotationPitch - pitch * 0.15D);
		rotationPitch = MathHelper.clamp_float(rotationPitch, -90.0F, 90.0F);
		prevRotationPitch += rotationPitch - var3;
		prevRotationYaw += rotationYaw - var4;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		onEntityUpdate();
	}

	/**
	 * Gets called every tick from main Entity class
	 */
	public void onEntityUpdate() {
		worldObj.theProfiler.startSection("entityBaseTick");

		if (ridingEntity != null && ridingEntity.isDead) {
			ridingEntity = null;
		}

		prevDistanceWalkedModified = distanceWalkedModified;
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		prevRotationPitch = rotationPitch;
		prevRotationYaw = rotationYaw;

		if (!worldObj.isRemote && worldObj instanceof WorldServer) {
			worldObj.theProfiler.startSection("portal");
			final MinecraftServer var1 = ((WorldServer) worldObj).func_73046_m();
			final int var2 = getMaxInPortalTime();

			if (inPortal) {
				if (var1.getAllowNether()) {
					if (ridingEntity == null && portalCounter++ >= var2) {
						portalCounter = var2;
						timeUntilPortal = getPortalCooldown();
						byte var3;

						if (worldObj.provider.getDimensionId() == -1) {
							var3 = 0;
						} else {
							var3 = -1;
						}

						travelToDimension(var3);
					}

					inPortal = false;
				}
			} else {
				if (portalCounter > 0) {
					portalCounter -= 4;
				}

				if (portalCounter < 0) {
					portalCounter = 0;
				}
			}

			if (timeUntilPortal > 0) {
				--timeUntilPortal;
			}

			worldObj.theProfiler.endSection();
		}

		func_174830_Y();
		handleWaterMovement();

		if (worldObj.isRemote) {
			fire = 0;
		} else if (fire > 0) {
			if (isImmuneToFire) {
				fire -= 4;

				if (fire < 0) {
					fire = 0;
				}
			} else {
				if (fire % 20 == 0) {
					attackEntityFrom(DamageSource.onFire, 1.0F);
				}

				--fire;
			}
		}

		if (func_180799_ab()) {
			setOnFireFromLava();
			fallDistance *= 0.5F;
		}

		if (posY < -64.0D) {
			kill();
		}

		if (!worldObj.isRemote) {
			setFlag(0, fire > 0);
		}

		firstUpdate = false;
		worldObj.theProfiler.endSection();
	}

	/**
	 * Return the amount of time this entity should stay in a portal before
	 * being transported.
	 */
	public int getMaxInPortalTime() {
		return 0;
	}

	/**
	 * Called whenever the entity is walking inside of lava.
	 */
	protected void setOnFireFromLava() {
		if (!isImmuneToFire) {
			attackEntityFrom(DamageSource.lava, 4.0F);
			setFire(15);
		}
	}

	/**
	 * Sets entity to burn for x amount of seconds, cannot lower amount of
	 * existing fire.
	 */
	public void setFire(final int seconds) {
		int var2 = seconds * 20;
		var2 = EnchantmentProtection.getFireTimeForEntity(this, var2);

		if (fire < var2) {
			fire = var2;
		}
	}

	/**
	 * Removes fire from entity.
	 */
	public void extinguish() {
		fire = 0;
	}

	/**
	 * sets the dead flag. Used when you fall off the bottom of the world.
	 */
	protected void kill() {
		setDead();
	}

	/**
	 * Checks if the offset position from the entity's current position is
	 * inside of liquid. Args: x, y, z
	 */
	public boolean isOffsetPositionInLiquid(final double x, final double y, final double z) {
		final AxisAlignedBB var7 = getEntityBoundingBox().offset(x, y, z);
		return func_174809_b(var7);
	}

	private boolean func_174809_b(final AxisAlignedBB p_174809_1_) {
		return worldObj.getCollidingBoundingBoxes(this, p_174809_1_).isEmpty() && !worldObj.isAnyLiquid(p_174809_1_);
	}

	/**
	 * Tries to moves the entity by the passed in displacement. Args: x, y, z
	 */
	public void moveEntity(double x, double y, double z) {
		if (noClip) {
			func_174826_a(getEntityBoundingBox().offset(x, y, z));
			func_174829_m();
		} else {
			worldObj.theProfiler.startSection("move");
			final double _posX = posX;
			final double _posY = posY;
			final double _posZ = posZ;

			if (isInWeb) {
				isInWeb = false;
				x *= 0.25D;
				y *= 0.05000000074505806D;
				z *= 0.25D;
				motionX = 0.0D;
				motionY = 0.0D;
				motionZ = 0.0D;
			}

			double _x = x;
			final double _y = y;
			double _z = z;
			final boolean _onGround = onGround && isSneaking() && this instanceof EntityPlayer;
			
			final EventSafewalk event = new EventSafewalk(_onGround);
			EventManager.call(event);

			if (_onGround || event.getShouldWalkSafely() && onGround) {
				double var20;

				for (var20 = 0.05D; x != 0.0D
						&& worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, 0.0D))
								.isEmpty(); _x = x) {
					if (x < var20 && x >= -var20) {
						x = 0.0D;
					} else if (x > 0.0D) {
						x -= var20;
					} else {
						x += var20;
					}
				}

				for (; z != 0.0D
						&& worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(0.0D, -1.0D, z))
								.isEmpty(); _z = z) {
					if (z < var20 && z >= -var20) {
						z = 0.0D;
					} else if (z > 0.0D) {
						z -= var20;
					} else {
						z += var20;
					}
				}

				for (; x != 0.0D && z != 0.0D
						&& worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, z))
								.isEmpty(); _z = z) {
					if (x < var20 && x >= -var20) {
						x = 0.0D;
					} else if (x > 0.0D) {
						x -= var20;
					} else {
						x += var20;
					}

					_x = x;

					if (z < var20 && z >= -var20) {
						z = 0.0D;
					} else if (z > 0.0D) {
						z -= var20;
					} else {
						z += var20;
					}
				}
			}

			final List var53 = worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(x, y, z));
			final AxisAlignedBB var21 = getEntityBoundingBox();
			AxisAlignedBB var23;

			for (final Iterator var22 = var53.iterator(); var22
					.hasNext(); y = var23.calculateYOffset(getEntityBoundingBox(), y)) {
				var23 = (AxisAlignedBB) var22.next();
			}

			func_174826_a(getEntityBoundingBox().offset(0.0D, y, 0.0D));
			final boolean var54 = onGround || _y != y && _y < 0.0D;
			AxisAlignedBB var24;
			Iterator var55;

			for (var55 = var53.iterator(); var55.hasNext(); x = var24.calculateXOffset(getEntityBoundingBox(), x)) {
				var24 = (AxisAlignedBB) var55.next();
			}

			func_174826_a(getEntityBoundingBox().offset(x, 0.0D, 0.0D));

			for (var55 = var53.iterator(); var55.hasNext(); z = var24.calculateZOffset(getEntityBoundingBox(), z)) {
				var24 = (AxisAlignedBB) var55.next();
			}

			func_174826_a(getEntityBoundingBox().offset(0.0D, 0.0D, z));

			if (stepHeight > 0.0F && var54 && (_x != x || _z != z)) {
				final double var56 = x;
				final double var25 = y;
				final double var27 = z;
				final AxisAlignedBB var29 = getEntityBoundingBox();
				func_174826_a(var21);
				y = stepHeight;
				final List var30 = worldObj.getCollidingBoundingBoxes(this,
						getEntityBoundingBox().addCoord(_x, y, _z));
				AxisAlignedBB var31 = getEntityBoundingBox();
				final AxisAlignedBB var32 = var31.addCoord(_x, 0.0D, _z);
				double var33 = y;
				AxisAlignedBB var36;

				for (final Iterator var35 = var30.iterator(); var35
						.hasNext(); var33 = var36.calculateYOffset(var32, var33)) {
					var36 = (AxisAlignedBB) var35.next();
				}

				var31 = var31.offset(0.0D, var33, 0.0D);
				double var67 = _x;
				AxisAlignedBB var38;

				for (final Iterator var37 = var30.iterator(); var37
						.hasNext(); var67 = var38.calculateXOffset(var31, var67)) {
					var38 = (AxisAlignedBB) var37.next();
				}

				var31 = var31.offset(var67, 0.0D, 0.0D);
				double var68 = _z;
				AxisAlignedBB var40;

				for (final Iterator var39 = var30.iterator(); var39
						.hasNext(); var68 = var40.calculateZOffset(var31, var68)) {
					var40 = (AxisAlignedBB) var39.next();
				}

				var31 = var31.offset(0.0D, 0.0D, var68);
				AxisAlignedBB var69 = getEntityBoundingBox();
				double var70 = y;
				AxisAlignedBB var43;

				for (final Iterator var42 = var30.iterator(); var42
						.hasNext(); var70 = var43.calculateYOffset(var69, var70)) {
					var43 = (AxisAlignedBB) var42.next();
				}

				var69 = var69.offset(0.0D, var70, 0.0D);
				double var71 = _x;
				AxisAlignedBB var45;

				for (final Iterator var44 = var30.iterator(); var44
						.hasNext(); var71 = var45.calculateXOffset(var69, var71)) {
					var45 = (AxisAlignedBB) var44.next();
				}

				var69 = var69.offset(var71, 0.0D, 0.0D);
				double var72 = _z;
				AxisAlignedBB var47;

				for (final Iterator var46 = var30.iterator(); var46
						.hasNext(); var72 = var47.calculateZOffset(var69, var72)) {
					var47 = (AxisAlignedBB) var46.next();
				}

				var69 = var69.offset(0.0D, 0.0D, var72);
				final double var73 = var67 * var67 + var68 * var68;
				final double var48 = var71 * var71 + var72 * var72;

				if (var73 > var48) {
					x = var67;
					z = var68;
					func_174826_a(var31);
				} else {
					x = var71;
					z = var72;
					func_174826_a(var69);
				}

				y = -stepHeight;
				AxisAlignedBB var51;

				for (final Iterator var50 = var30.iterator(); var50
						.hasNext(); y = var51.calculateYOffset(getEntityBoundingBox(), y)) {
					var51 = (AxisAlignedBB) var50.next();
				}

				func_174826_a(getEntityBoundingBox().offset(0.0D, y, 0.0D));

				if (var56 * var56 + var27 * var27 >= x * x + z * z) {
					x = var56;
					y = var25;
					z = var27;
					func_174826_a(var29);
				} else if (y >= -0.4 && y <= 0.0 && onGround) {
					final EventStep eventStep = new EventStep(stepHeight + y);
					EventManager.call(eventStep);
				}
			}

			worldObj.theProfiler.endSection();
			worldObj.theProfiler.startSection("rest");
			func_174829_m();
			isCollidedHorizontally = _x != x || _z != z;
			isCollidedVertically = _y != y;
			onGround = isCollidedVertically && _y < 0.0D;
			isCollided = isCollidedHorizontally || isCollidedVertically;
			final int var57 = MathHelper.floor_double(posX);
			final int var58 = MathHelper.floor_double(posY - 0.20000000298023224D);
			final int var59 = MathHelper.floor_double(posZ);
			BlockPos var26 = new BlockPos(var57, var58, var59);
			Block var60 = worldObj.getBlockState(var26).getBlock();

			if (var60.getMaterial() == Material.air) {
				final Block var28 = worldObj.getBlockState(var26.offsetDown()).getBlock();

				if (var28 instanceof BlockFence || var28 instanceof BlockWall || var28 instanceof BlockFenceGate) {
					var60 = var28;
					var26 = var26.offsetDown();
				}
			}

			func_180433_a(y, onGround, var60, var26);

			if (_x != x) {
				motionX = 0.0D;
			}

			if (_z != z) {
				motionZ = 0.0D;
			}

			if (_y != y) {
				var60.onLanded(worldObj, this);
			}

			if (canTriggerWalking() && !_onGround && ridingEntity == null) {
				final double var61 = posX - _posX;
				double var64 = posY - _posY;
				final double var66 = posZ - _posZ;

				if (var60 != Blocks.ladder) {
					var64 = 0.0D;
				}

				if (var60 != null && onGround) {
					var60.onEntityCollidedWithBlock(worldObj, var26, this);
				}

				distanceWalkedModified = (float) (distanceWalkedModified
						+ MathHelper.sqrt_double(var61 * var61 + var66 * var66) * 0.6D);
				distanceWalkedOnStepModified = (float) (distanceWalkedOnStepModified
						+ MathHelper.sqrt_double(var61 * var61 + var64 * var64 + var66 * var66) * 0.6D);

				if (distanceWalkedOnStepModified > nextStepDistance && var60.getMaterial() != Material.air) {
					nextStepDistance = (int) distanceWalkedOnStepModified + 1;

					if (isInWater()) {
						float var34 = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D
								+ motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.35F;

						if (var34 > 1.0F) {
							var34 = 1.0F;
						}

						playSound(getSwimSound(), var34, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
					}

					func_180429_a(var26, var60);
				}
			}

			try {
				doBlockCollisions();
			} catch (final Throwable var52) {
				final CrashReport var63 = CrashReport.makeCrashReport(var52, "Checking entity block collision");
				final CrashReportCategory var65 = var63.makeCategory("Entity being checked for collision");
				addEntityCrashInfo(var65);
				throw new ReportedException(var63);
			}

			final boolean var62 = isWet();

			if (worldObj.func_147470_e(getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
				dealFireDamage(1);

				if (!var62) {
					++fire;

					if (fire == 0) {
						setFire(8);
					}
				}
			} else if (fire <= 0) {
				fire = -fireResistance;
			}

			if (var62 && fire > 0) {
				playSound("random.fizz", 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
				fire = -fireResistance;
			}

			worldObj.theProfiler.endSection();
		}
	}

	private void func_174829_m() {
		posX = (getEntityBoundingBox().minX + getEntityBoundingBox().maxX) / 2.0D;
		posY = getEntityBoundingBox().minY;
		posZ = (getEntityBoundingBox().minZ + getEntityBoundingBox().maxZ) / 2.0D;
	}

	protected String getSwimSound() {
		return "game.neutral.swim";
	}

	protected void doBlockCollisions() {
		final BlockPos var1 = new BlockPos(getEntityBoundingBox().minX + 0.001D, getEntityBoundingBox().minY + 0.001D,
				getEntityBoundingBox().minZ + 0.001D);
		final BlockPos var2 = new BlockPos(getEntityBoundingBox().maxX - 0.001D, getEntityBoundingBox().maxY - 0.001D,
				getEntityBoundingBox().maxZ - 0.001D);

		if (worldObj.isAreaLoaded(var1, var2)) {
			for (int var3 = var1.getX(); var3 <= var2.getX(); ++var3) {
				for (int var4 = var1.getY(); var4 <= var2.getY(); ++var4) {
					for (int var5 = var1.getZ(); var5 <= var2.getZ(); ++var5) {
						final BlockPos var6 = new BlockPos(var3, var4, var5);
						final IBlockState var7 = worldObj.getBlockState(var6);

						try {
							var7.getBlock().onEntityCollidedWithBlock(worldObj, var6, var7, this);
						} catch (final Throwable var11) {
							final CrashReport var9 = CrashReport.makeCrashReport(var11, "Colliding entity with block");
							final CrashReportCategory var10 = var9.makeCategory("Block being collided with");
							CrashReportCategory.addBlockInfo(var10, var6, var7);
							throw new ReportedException(var9);
						}
					}
				}
			}
		}
	}

	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		Block.SoundType var3 = p_180429_2_.stepSound;

		if (worldObj.getBlockState(p_180429_1_.offsetUp()).getBlock() == Blocks.snow_layer) {
			var3 = Blocks.snow_layer.stepSound;
			playSound(var3.getStepSound(), var3.getVolume() * 0.15F, var3.getFrequency());
		} else if (!p_180429_2_.getMaterial().isLiquid()) {
			playSound(var3.getStepSound(), var3.getVolume() * 0.15F, var3.getFrequency());
		}
	}

	public void playSound(final String name, final float volume, final float pitch) {
		if (!isSlient()) {
			worldObj.playSoundAtEntity(this, name, volume, pitch);
		}
	}

	/**
	 * @return True if this entity will not play sounds
	 */
	public boolean isSlient() {
		return dataWatcher.getWatchableObjectByte(4) == 1;
	}

	public void func_174810_b(final boolean p_174810_1_) {
		dataWatcher.updateObject(4, (byte) (p_174810_1_ ? 1 : 0));
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return true;
	}

	protected void func_180433_a(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_,
			final BlockPos p_180433_5_) {
		if (p_180433_3_) {
			if (fallDistance > 0.0F) {
				if (p_180433_4_ != null) {
					p_180433_4_.onFallenUpon(worldObj, p_180433_5_, this, fallDistance);
				} else {
					fall(fallDistance, 1.0F);
				}

				fallDistance = 0.0F;
			}
		} else if (p_180433_1_ < 0.0D) {
			fallDistance = (float) (fallDistance - p_180433_1_);
		}
	}

	/**
	 * returns the bounding box for this entity
	 */
	public AxisAlignedBB getBoundingBox() {
		return null;
	}

	/**
	 * Will deal the specified amount of damage to the entity if the entity
	 * isn't immune to fire damage. Args: amountDamage
	 */
	protected void dealFireDamage(final int amount) {
		if (!isImmuneToFire) {
			attackEntityFrom(DamageSource.inFire, amount);
		}
	}

	public final boolean isImmuneToFire() {
		return isImmuneToFire;
	}

	public void fall(final float distance, final float damageMultiplier) {
		if (riddenByEntity != null) {
			riddenByEntity.fall(distance, damageMultiplier);
		}
	}

	/**
	 * Checks if this entity is either in water or on an open air block in rain
	 * (used in wolves).
	 */
	public boolean isWet() {
		return inWater || worldObj.func_175727_C(new BlockPos(posX, posY, posZ))
				|| worldObj.func_175727_C(new BlockPos(posX, posY + height, posZ));
	}

	/**
	 * Checks if this entity is inside water (if inWater field is true as a
	 * result of handleWaterMovement() returning true)
	 */
	public boolean isInWater() {
		return inWater;
	}

	/**
	 * Returns if this entity is in water and will end up adding the waters
	 * velocity to the entity
	 */
	public boolean handleWaterMovement() {
		if (worldObj.handleMaterialAcceleration(
				getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D),
				Material.water, this)) {
			if (!inWater && !firstUpdate) {
				resetHeight();
			}

			fallDistance = 0.0F;
			inWater = true;
			fire = 0;
		} else {
			inWater = false;
		}

		return inWater;
	}

	/**
	 * sets the players height back to normal after doing things like sleeping
	 * and dieing
	 */
	protected void resetHeight() {
		float var1 = MathHelper.sqrt_double(
				motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D)
				* 0.2F;

		if (var1 > 1.0F) {
			var1 = 1.0F;
		}

		playSound(getSplashSound(), var1, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
		final float var2 = MathHelper.floor_double(getEntityBoundingBox().minY);
		int var3;
		float var4;
		float var5;

		for (var3 = 0; var3 < 1.0F + width * 20.0F; ++var3) {
			var4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
			var5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
			worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX + var4, var2 + 1.0F, posZ + var5, motionX,
					motionY - rand.nextFloat() * 0.2F, motionZ, new int[0]);
		}

		for (var3 = 0; var3 < 1.0F + width * 20.0F; ++var3) {
			var4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
			var5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
			worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX + var4, var2 + 1.0F, posZ + var5, motionX,
					motionY, motionZ, new int[0]);
		}
	}

	public void func_174830_Y() {
		if (isSprinting() && !isInWater()) {
			func_174808_Z();
		}
	}

	protected void func_174808_Z() {
		final int var1 = MathHelper.floor_double(posX);
		final int var2 = MathHelper.floor_double(posY - 0.20000000298023224D);
		final int var3 = MathHelper.floor_double(posZ);
		final BlockPos var4 = new BlockPos(var1, var2, var3);
		final IBlockState var5 = worldObj.getBlockState(var4);
		final Block var6 = var5.getBlock();

		if (var6.getRenderType() != -1) {
			worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX + (rand.nextFloat() - 0.5D) * width,
					getEntityBoundingBox().minY + 0.1D, posZ + (rand.nextFloat() - 0.5D) * width, -motionX * 4.0D, 1.5D,
					-motionZ * 4.0D, new int[] { Block.getStateId(var5) });
		}
	}

	protected String getSplashSound() {
		return "game.neutral.swim.splash";
	}

	/**
	 * Checks if the current block the entity is within of the specified
	 * material type
	 */
	public boolean isInsideOfMaterial(final Material materialIn) {
		final double var2 = posY + getEyeHeight();
		final BlockPos var4 = new BlockPos(posX, var2, posZ);
		final IBlockState var5 = worldObj.getBlockState(var4);
		final Block var6 = var5.getBlock();

		if (var6.getMaterial() == materialIn) {
			final float var7 = BlockLiquid.getLiquidHeightPercent(var5.getBlock().getMetaFromState(var5)) - 0.11111111F;
			final float var8 = var4.getY() + 1 - var7;
			final boolean var9 = var2 < var8;
			return !var9 && this instanceof EntityPlayer ? false : var9;
		} else {
			return false;
		}
	}

	public boolean func_180799_ab() {
		return worldObj.isMaterialInBB(
				getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D),
				Material.lava);
	}

	/**
	 * Used in both water and by flying objects
	 */
	public void moveFlying(float strafe, float forward, final float friction) {
		float var4 = strafe * strafe + forward * forward;

		if (var4 >= 1.0E-4F) {
			var4 = MathHelper.sqrt_float(var4);

			if (var4 < 1.0F) {
				var4 = 1.0F;
			}

			var4 = friction / var4;
			strafe *= var4;
			forward *= var4;
			final float var5 = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F);
			final float var6 = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F);
			motionX += strafe * var6 - forward * var5;
			motionZ += forward * var6 + strafe * var5;
		}
	}

	public int getBrightnessForRender(final float p_70070_1_) {
		final BlockPos var2 = new BlockPos(posX, 0.0D, posZ);

		if (worldObj.isBlockLoaded(var2)) {
			final double var3 = (getEntityBoundingBox().maxY - getEntityBoundingBox().minY) * 0.66D;
			final int var5 = MathHelper.floor_double(posY + var3);
			return worldObj.getCombinedLight(var2.offsetUp(var5), 0);
		} else {
			return 0;
		}
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(final float p_70013_1_) {
		final BlockPos var2 = new BlockPos(posX, 0.0D, posZ);

		if (worldObj.isBlockLoaded(var2)) {
			final double var3 = (getEntityBoundingBox().maxY - getEntityBoundingBox().minY) * 0.66D;
			final int var5 = MathHelper.floor_double(posY + var3);
			return worldObj.getLightBrightness(var2.offsetUp(var5));
		} else {
			return 0.0F;
		}
	}

	/**
	 * Sets the reference to the World object.
	 */
	public void setWorld(final World worldIn) {
		worldObj = worldIn;
	}

	/**
	 * Sets the entity's position and rotation.
	 */
	public void setPositionAndRotation(final double x, final double y, final double z, final float yaw,
			final float pitch) {
		prevPosX = posX = x;
		prevPosY = posY = y;
		prevPosZ = posZ = z;
		prevRotationYaw = rotationYaw = yaw;
		prevRotationPitch = rotationPitch = pitch;
		final double var9 = prevRotationYaw - yaw;

		if (var9 < -180.0D) {
			prevRotationYaw += 360.0F;
		}

		if (var9 >= 180.0D) {
			prevRotationYaw -= 360.0F;
		}

		setPosition(posX, posY, posZ);
		setRotation(yaw, pitch);
	}

	public void func_174828_a(final BlockPos p_174828_1_, final float p_174828_2_, final float p_174828_3_) {
		setLocationAndAngles(p_174828_1_.getX() + 0.5D, p_174828_1_.getY(), p_174828_1_.getZ() + 0.5D, p_174828_2_,
				p_174828_3_);
	}

	/**
	 * Sets the location and Yaw/Pitch of an entity in the world
	 */
	public void setLocationAndAngles(final double x, final double y, final double z, final float yaw,
			final float pitch) {
		lastTickPosX = prevPosX = posX = x;
		lastTickPosY = prevPosY = posY = y;
		lastTickPosZ = prevPosZ = posZ = z;
		rotationYaw = yaw;
		rotationPitch = pitch;
		setPosition(posX, posY, posZ);
	}

	/**
	 * Returns the distance to the entity. Args: entity
	 */
	public float getDistanceToEntity(final Entity entityIn) {
		final float var2 = (float) (posX - entityIn.posX);
		final float var3 = (float) (posY - entityIn.posY);
		final float var4 = (float) (posZ - entityIn.posZ);
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}

	/**
	 * Gets the squared distance to the position. Args: x, y, z
	 */
	public double getDistanceSq(final double x, final double y, final double z) {
		final double var7 = posX - x;
		final double var9 = posY - y;
		final double var11 = posZ - z;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	public double getDistanceSq(final BlockPos p_174818_1_) {
		return p_174818_1_.distanceSq(posX, posY, posZ);
	}

	public double func_174831_c(final BlockPos p_174831_1_) {
		return p_174831_1_.distanceSqToCenter(posX, posY, posZ);
	}

	/**
	 * Gets the distance to the position. Args: x, y, z
	 */
	public double getDistance(final double x, final double y, final double z) {
		final double var7 = posX - x;
		final double var9 = posY - y;
		final double var11 = posZ - z;
		return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
	}

	/**
	 * Returns the squared distance to the entity. Args: entity
	 */
	public double getDistanceSqToEntity(final Entity entityIn) {
		final double var2 = posX - entityIn.posX;
		final double var4 = posY - entityIn.posY;
		final double var6 = posZ - entityIn.posZ;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	public void onCollideWithPlayer(final EntityPlayer entityIn) {}

	/**
	 * Applies a velocity to each of the entities pushing them away from each
	 * other. Args: entity
	 */
	public void applyEntityCollision(final Entity entityIn) {
		if (entityIn.riddenByEntity != this && entityIn.ridingEntity != this) {
			if (!entityIn.noClip && !noClip) {
				double var2 = entityIn.posX - posX;
				double var4 = entityIn.posZ - posZ;
				double var6 = MathHelper.abs_max(var2, var4);

				if (var6 >= 0.009999999776482582D) {
					var6 = MathHelper.sqrt_double(var6);
					var2 /= var6;
					var4 /= var6;
					double var8 = 1.0D / var6;

					if (var8 > 1.0D) {
						var8 = 1.0D;
					}

					var2 *= var8;
					var4 *= var8;
					var2 *= 0.05000000074505806D;
					var4 *= 0.05000000074505806D;
					var2 *= 1.0F - entityCollisionReduction;
					var4 *= 1.0F - entityCollisionReduction;

					if (riddenByEntity == null) {
						addVelocity(-var2, 0.0D, -var4);
					}

					if (entityIn.riddenByEntity == null) {
						entityIn.addVelocity(var2, 0.0D, var4);
					}
				}
			}
		}
	}

	/**
	 * Adds to the current velocity of the entity. Args: x, y, z
	 */
	public void addVelocity(final double x, final double y, final double z) {
		motionX += x;
		motionY += y;
		motionZ += z;
		isAirBorne = true;
	}

	/**
	 * Sets that this entity has been attacked.
	 */
	protected void setBeenAttacked() {
		velocityChanged = true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			setBeenAttacked();
			return false;
		}
	}

	/**
	 * interpolated look vector
	 */
	public Vec3 getLook(final float p_70676_1_) {
		if (p_70676_1_ == 1.0F) {
			return func_174806_f(rotationPitch, rotationYaw);
		} else {
			final float var2 = prevRotationPitch + (rotationPitch - prevRotationPitch) * p_70676_1_;
			final float var3 = prevRotationYaw + (rotationYaw - prevRotationYaw) * p_70676_1_;
			return func_174806_f(var2, var3);
		}
	}

	protected final Vec3 func_174806_f(final float p_174806_1_, final float p_174806_2_) {
		final float var3 = MathHelper.cos(-p_174806_2_ * 0.017453292F - (float) Math.PI);
		final float var4 = MathHelper.sin(-p_174806_2_ * 0.017453292F - (float) Math.PI);
		final float var5 = -MathHelper.cos(-p_174806_1_ * 0.017453292F);
		final float var6 = MathHelper.sin(-p_174806_1_ * 0.017453292F);
		return new Vec3(var4 * var5, var6, var3 * var5);
	}

	public Vec3 func_174824_e(final float p_174824_1_) {
		if (p_174824_1_ == 1.0F) {
			return new Vec3(posX, posY + getEyeHeight(), posZ);
		} else {
			final double var2 = prevPosX + (posX - prevPosX) * p_174824_1_;
			final double var4 = prevPosY + (posY - prevPosY) * p_174824_1_ + getEyeHeight();
			final double var6 = prevPosZ + (posZ - prevPosZ) * p_174824_1_;
			return new Vec3(var2, var4, var6);
		}
	}

	public MovingObjectPosition func_174822_a(final double p_174822_1_, final float p_174822_3_) {
		final Vec3 var4 = func_174824_e(p_174822_3_);
		final Vec3 var5 = getLook(p_174822_3_);
		final Vec3 var6 = var4.addVector(var5.xCoord * p_174822_1_, var5.yCoord * p_174822_1_,
				var5.zCoord * p_174822_1_);
		return worldObj.rayTraceBlocks(var4, var6, false, false, true);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	public boolean canBeCollidedWith() {
		return false;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	public boolean canBePushed() {
		return false;
	}

	/**
	 * Adds a value to the player score. Currently not actually used and the
	 * entity passed in does nothing. Args: entity, scoreToAdd
	 */
	public void addToPlayerScore(final Entity entityIn, final int amount) {}

	public boolean isInRangeToRender3d(final double x, final double y, final double z) {
		final double var7 = posX - x;
		final double var9 = posY - y;
		final double var11 = posZ - z;
		final double var13 = var7 * var7 + var9 * var9 + var11 * var11;
		return isInRangeToRenderDist(var13);
	}

	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge length * 64 * renderDistanceWeight
	 * Args: distance
	 */
	public boolean isInRangeToRenderDist(final double distance) {
		double var3 = getEntityBoundingBox().getAverageEdgeLength();
		var3 *= 64.0D * renderDistanceWeight;
		return distance < var3 * var3;
	}

	/**
	 * Like writeToNBTOptional but does not check if the entity is ridden. Used
	 * for saving ridden entities with their riders.
	 */
	public boolean writeMountToNBT(final NBTTagCompound tagCompund) {
		final String var2 = getEntityString();

		if (!isDead && var2 != null) {
			tagCompund.setString("id", var2);
			writeToNBT(tagCompund);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Either write this entity to the NBT tag given and return true, or return
	 * false without doing anything. If this returns false the entity is not
	 * saved on disk. Ridden entities return false here as they are saved with
	 * their rider.
	 */
	public boolean writeToNBTOptional(final NBTTagCompound tagCompund) {
		final String var2 = getEntityString();

		if (!isDead && var2 != null && riddenByEntity == null) {
			tagCompund.setString("id", var2);
			writeToNBT(tagCompund);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Save the entity to NBT (calls an abstract helper method to write extra
	 * data)
	 */
	public void writeToNBT(final NBTTagCompound tagCompund) {
		try {
			tagCompund.setTag("Pos", newDoubleNBTList(new double[] { posX, posY, posZ }));
			tagCompund.setTag("Motion", newDoubleNBTList(new double[] { motionX, motionY, motionZ }));
			tagCompund.setTag("Rotation", newFloatNBTList(new float[] { rotationYaw, rotationPitch }));
			tagCompund.setFloat("FallDistance", fallDistance);
			tagCompund.setShort("Fire", (short) fire);
			tagCompund.setShort("Air", (short) getAir());
			tagCompund.setBoolean("OnGround", onGround);
			tagCompund.setInteger("Dimension", dimension);
			tagCompund.setBoolean("Invulnerable", invulnerable);
			tagCompund.setInteger("PortalCooldown", timeUntilPortal);
			tagCompund.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
			tagCompund.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());

			if (getCustomNameTag() != null && getCustomNameTag().length() > 0) {
				tagCompund.setString("CustomName", getCustomNameTag());
				tagCompund.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
			}

			field_174837_as.func_179670_b(tagCompund);

			if (isSlient()) {
				tagCompund.setBoolean("Silent", isSlient());
			}

			writeEntityToNBT(tagCompund);

			if (ridingEntity != null) {
				final NBTTagCompound var2 = new NBTTagCompound();

				if (ridingEntity.writeMountToNBT(var2)) {
					tagCompund.setTag("Riding", var2);
				}
			}
		} catch (final Throwable var5) {
			final CrashReport var3 = CrashReport.makeCrashReport(var5, "Saving entity NBT");
			final CrashReportCategory var4 = var3.makeCategory("Entity being saved");
			addEntityCrashInfo(var4);
			throw new ReportedException(var3);
		}
	}

	/**
	 * Reads the entity from NBT (calls an abstract helper method to read
	 * specialized data)
	 */
	public void readFromNBT(final NBTTagCompound tagCompund) {
		try {
			final NBTTagList var2 = tagCompund.getTagList("Pos", 6);
			final NBTTagList var6 = tagCompund.getTagList("Motion", 6);
			final NBTTagList var7 = tagCompund.getTagList("Rotation", 5);
			motionX = var6.getDouble(0);
			motionY = var6.getDouble(1);
			motionZ = var6.getDouble(2);

			if (Math.abs(motionX) > 10.0D) {
				motionX = 0.0D;
			}

			if (Math.abs(motionY) > 10.0D) {
				motionY = 0.0D;
			}

			if (Math.abs(motionZ) > 10.0D) {
				motionZ = 0.0D;
			}

			prevPosX = lastTickPosX = posX = var2.getDouble(0);
			prevPosY = lastTickPosY = posY = var2.getDouble(1);
			prevPosZ = lastTickPosZ = posZ = var2.getDouble(2);
			prevRotationYaw = rotationYaw = var7.getFloat(0);
			prevRotationPitch = rotationPitch = var7.getFloat(1);
			fallDistance = tagCompund.getFloat("FallDistance");
			fire = tagCompund.getShort("Fire");
			setAir(tagCompund.getShort("Air"));
			onGround = tagCompund.getBoolean("OnGround");
			dimension = tagCompund.getInteger("Dimension");
			invulnerable = tagCompund.getBoolean("Invulnerable");
			timeUntilPortal = tagCompund.getInteger("PortalCooldown");

			if (tagCompund.hasKey("UUIDMost", 4) && tagCompund.hasKey("UUIDLeast", 4)) {
				entityUniqueID = new UUID(tagCompund.getLong("UUIDMost"), tagCompund.getLong("UUIDLeast"));
			} else if (tagCompund.hasKey("UUID", 8)) {
				entityUniqueID = UUID.fromString(tagCompund.getString("UUID"));
			}

			setPosition(posX, posY, posZ);
			setRotation(rotationYaw, rotationPitch);

			if (tagCompund.hasKey("CustomName", 8) && tagCompund.getString("CustomName").length() > 0) {
				setCustomNameTag(tagCompund.getString("CustomName"));
			}

			setAlwaysRenderNameTag(tagCompund.getBoolean("CustomNameVisible"));
			field_174837_as.func_179668_a(tagCompund);
			func_174810_b(tagCompund.getBoolean("Silent"));
			readEntityFromNBT(tagCompund);

			if (shouldSetPosAfterLoading()) {
				setPosition(posX, posY, posZ);
			}
		} catch (final Throwable var5) {
			final CrashReport var3 = CrashReport.makeCrashReport(var5, "Loading entity NBT");
			final CrashReportCategory var4 = var3.makeCategory("Entity being loaded");
			addEntityCrashInfo(var4);
			throw new ReportedException(var3);
		}
	}

	protected boolean shouldSetPosAfterLoading() {
		return true;
	}

	/**
	 * Returns the string that identifies this Entity's class
	 */
	protected final String getEntityString() {
		return EntityList.getEntityString(this);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	protected abstract void readEntityFromNBT(NBTTagCompound var1);

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	protected abstract void writeEntityToNBT(NBTTagCompound var1);

	public void onChunkLoad() {}

	/**
	 * creates a NBT list from the array of doubles passed to this function
	 */
	protected NBTTagList newDoubleNBTList(final double... numbers) {
		final NBTTagList var2 = new NBTTagList();
		final double[] var3 = numbers;
		final int var4 = numbers.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			final double var6 = var3[var5];
			var2.appendTag(new NBTTagDouble(var6));
		}

		return var2;
	}

	/**
	 * Returns a new NBTTagList filled with the specified floats
	 */
	protected NBTTagList newFloatNBTList(final float... numbers) {
		final NBTTagList var2 = new NBTTagList();
		final float[] var3 = numbers;
		final int var4 = numbers.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			final float var6 = var3[var5];
			var2.appendTag(new NBTTagFloat(var6));
		}

		return var2;
	}

	public EntityItem dropItem(final Item itemIn, final int size) {
		return dropItemWithOffset(itemIn, size, 0.0F);
	}

	public EntityItem dropItemWithOffset(final Item itemIn, final int size, final float p_145778_3_) {
		return entityDropItem(new ItemStack(itemIn, size, 0), p_145778_3_);
	}

	/**
	 * Drops an item at the position of the entity.
	 */
	public EntityItem entityDropItem(final ItemStack itemStackIn, final float offsetY) {
		if (itemStackIn.stackSize != 0 && itemStackIn.getItem() != null) {
			final EntityItem var3 = new EntityItem(worldObj, posX, posY + offsetY, posZ, itemStackIn);
			var3.setDefaultPickupDelay();
			worldObj.spawnEntityInWorld(var3);
			return var3;
		} else {
			return null;
		}
	}

	/**
	 * Checks whether target entity is alive.
	 */
	public boolean isEntityAlive() {
		return !isDead;
	}

	/**
	 * Checks if this entity is inside of an opaque block
	 */
	public boolean isEntityInsideOpaqueBlock() {
		final EventOpaqueBlock event = new EventOpaqueBlock();
		EventManager.call(event);
		if (event.isCancelled()) {
			return false;
		}
		if (noClip) {
			return false;
		} else {
			for (int var1 = 0; var1 < 8; ++var1) {
				final double var2 = posX + ((var1 >> 0) % 2 - 0.5F) * width * 0.8F;
				final double var4 = posY + ((var1 >> 1) % 2 - 0.5F) * 0.1F;
				final double var6 = posZ + ((var1 >> 2) % 2 - 0.5F) * width * 0.8F;

				if (worldObj.getBlockState(new BlockPos(var2, var4 + getEyeHeight(), var6)).getBlock()
						.isVisuallyOpaque()) {
					return true;
				}
			}

			return false;
		}
	}

	/**
	 * First layer of player interaction
	 */
	public boolean interactFirst(final EntityPlayer playerIn) {
		return false;
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like boats or
	 * minecarts.
	 */
	public AxisAlignedBB getCollisionBox(final Entity entityIn) {
		return null;
	}

	/**
	 * Handles updating while being ridden by an entity
	 */
	public void updateRidden() {
		if (ridingEntity.isDead) {
			ridingEntity = null;
		} else {
			motionX = 0.0D;
			motionY = 0.0D;
			motionZ = 0.0D;
			onUpdate();

			if (ridingEntity != null) {
				ridingEntity.updateRiderPosition();
				entityRiderYawDelta += ridingEntity.rotationYaw - ridingEntity.prevRotationYaw;

				for (entityRiderPitchDelta += ridingEntity.rotationPitch
						- ridingEntity.prevRotationPitch; entityRiderYawDelta >= 180.0D; entityRiderYawDelta -= 360.0D) {}

				while (entityRiderYawDelta < -180.0D) {
					entityRiderYawDelta += 360.0D;
				}

				while (entityRiderPitchDelta >= 180.0D) {
					entityRiderPitchDelta -= 360.0D;
				}

				while (entityRiderPitchDelta < -180.0D) {
					entityRiderPitchDelta += 360.0D;
				}

				double var1 = entityRiderYawDelta * 0.5D;
				double var3 = entityRiderPitchDelta * 0.5D;
				final float var5 = 10.0F;

				if (var1 > var5) {
					var1 = var5;
				}

				if (var1 < -var5) {
					var1 = -var5;
				}

				if (var3 > var5) {
					var3 = var5;
				}

				if (var3 < -var5) {
					var3 = -var5;
				}

				entityRiderYawDelta -= var1;
				entityRiderPitchDelta -= var3;
			}
		}
	}

	public void updateRiderPosition() {
		if (riddenByEntity != null) {
			riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
		}
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	public double getYOffset() {
		return 0.0D;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding
	 * this one.
	 */
	public double getMountedYOffset() {
		return height * 0.75D;
	}

	/**
	 * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
	 */
	public void mountEntity(final Entity entityIn) {
		entityRiderPitchDelta = 0.0D;
		entityRiderYawDelta = 0.0D;

		if (entityIn == null) {
			if (ridingEntity != null) {
				setLocationAndAngles(ridingEntity.posX, ridingEntity.getEntityBoundingBox().minY + ridingEntity.height,
						ridingEntity.posZ, rotationYaw, rotationPitch);
				ridingEntity.riddenByEntity = null;
			}

			ridingEntity = null;
		} else {
			if (ridingEntity != null) {
				ridingEntity.riddenByEntity = null;
			}

			if (entityIn != null) {
				for (Entity var2 = entityIn.ridingEntity; var2 != null; var2 = var2.ridingEntity) {
					if (var2 == this) {
						return;
					}
				}
			}

			ridingEntity = entityIn;
			entityIn.riddenByEntity = this;
		}
	}

	public void func_180426_a(final double p_180426_1_, double p_180426_3_, final double p_180426_5_,
			final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
		setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
		setRotation(p_180426_7_, p_180426_8_);
		final List var11 = worldObj.getCollidingBoundingBoxes(this,
				getEntityBoundingBox().contract(0.03125D, 0.0D, 0.03125D));

		if (!var11.isEmpty()) {
			double var12 = 0.0D;
			final Iterator var14 = var11.iterator();

			while (var14.hasNext()) {
				final AxisAlignedBB var15 = (AxisAlignedBB) var14.next();

				if (var15.maxY > var12) {
					var12 = var15.maxY;
				}
			}

			p_180426_3_ += var12 - getEntityBoundingBox().minY;
			setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
		}
	}

	public float getCollisionBorderSize() {
		return 0.1F;
	}

	/**
	 * returns a (normalized) vector of where this entity is looking
	 */
	public Vec3 getLookVec() {
		return null;
	}

	/**
	 * Called by portal blocks when an entity is within it.
	 */
	public void setInPortal() {
		if (timeUntilPortal > 0) {
			timeUntilPortal = getPortalCooldown();
		} else {
			final double var1 = prevPosX - posX;
			final double var3 = prevPosZ - posZ;

			if (!worldObj.isRemote && !inPortal) {
				int var5;

				if (MathHelper.abs((float) var1) > MathHelper.abs((float) var3)) {
					var5 = var1 > 0.0D ? EnumFacing.WEST.getHorizontalIndex() : EnumFacing.EAST.getHorizontalIndex();
				} else {
					var5 = var3 > 0.0D ? EnumFacing.NORTH.getHorizontalIndex() : EnumFacing.SOUTH.getHorizontalIndex();
				}

				teleportDirection = var5;
			}

			inPortal = true;
		}
	}

	/**
	 * Return the amount of cooldown before this entity can use a portal again.
	 */
	public int getPortalCooldown() {
		return 300;
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(final double x, final double y, final double z) {
		motionX = x;
		motionY = y;
		motionZ = z;
	}

	public void handleHealthUpdate(final byte p_70103_1_) {}

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in
	 * multiplayer.
	 */
	public void performHurtAnimation() {}

	/**
	 * returns the inventory of this entity (only used in EntityPlayerMP it
	 * seems)
	 */
	public ItemStack[] getInventory() {
		return null;
	}

	/**
	 * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is
	 * armor. Params: Item, slot
	 */
	public void setCurrentItemOrArmor(final int slotIn, final ItemStack itemStackIn) {}

	/**
	 * Returns true if the entity is on fire. Used by render to add the fire
	 * effect on rendering.
	 */
	public boolean isBurning() {
		final boolean var1 = worldObj != null && worldObj.isRemote;
		return !isImmuneToFire && (fire > 0 || var1 && getFlag(0));
	}

	/**
	 * Returns true if the entity is riding another entity, used by render to
	 * rotate the legs to be in 'sit' position for players.
	 */
	public boolean isRiding() {
		return ridingEntity != null;
	}

	/**
	 * Returns if this entity is sneaking.
	 */
	public boolean isSneaking() {
		return getFlag(1);
	}

	/**
	 * Sets the sneaking flag.
	 */
	public void setSneaking(final boolean sneaking) {
		setFlag(1, sneaking);
	}

	/**
	 * Get if the Entity is sprinting.
	 */
	public boolean isSprinting() {
		return getFlag(3);
	}

	/**
	 * Set sprinting switch for Entity.
	 */
	public void setSprinting(final boolean sprinting) {
		setFlag(3, sprinting);
	}

	public boolean isInvisible() {
		return getFlag(5);
	}

	/**
	 * Only used by renderer in EntityLivingBase subclasses. Determines if an
	 * entity is visible or not to a specfic player, if the entity is normally
	 * invisible. For EntityLivingBase subclasses, returning false when
	 * invisible will render the entity semitransparent.
	 */
	public boolean isInvisibleToPlayer(final EntityPlayer playerIn) {
		return playerIn.isSpectatorMode() ? false : isInvisible();
	}

	public void setInvisible(final boolean invisible) {
		setFlag(5, invisible);
	}

	public boolean isEating() {
		return getFlag(4);
	}

	public void setEating(final boolean eating) {
		setFlag(4, eating);
	}

	/**
	 * Returns true if the flag is active for the entity. Known flags: 0) is
	 * burning; 1) is sneaking; 2) is riding something; 3) is sprinting; 4) is
	 * eating
	 */
	protected boolean getFlag(final int flag) {
		return (dataWatcher.getWatchableObjectByte(0) & 1 << flag) != 0;
	}

	/**
	 * Enable or disable a entity flag, see getEntityFlag to read the know
	 * flags.
	 */
	protected void setFlag(final int flag, final boolean set) {
		final byte var3 = dataWatcher.getWatchableObjectByte(0);

		if (set) {
			dataWatcher.updateObject(0, (byte) (var3 | 1 << flag));
		} else {
			dataWatcher.updateObject(0, (byte) (var3 & ~(1 << flag)));
		}
	}

	public int getAir() {
		return dataWatcher.getWatchableObjectShort(1);
	}

	public void setAir(final int air) {
		dataWatcher.updateObject(1, (short) air);
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
		attackEntityFrom(DamageSource.field_180137_b, 5.0F);
		++fire;

		if (fire == 0) {
			setFire(8);
		}
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	public void onKillEntity(final EntityLivingBase entityLivingIn) {}

	protected boolean pushOutOfBlocks(final double x, final double y, final double z) {
		final BlockPos var7 = new BlockPos(x, y, z);
		final double var8 = x - var7.getX();
		final double var10 = y - var7.getY();
		final double var12 = z - var7.getZ();
		final List var14 = worldObj.func_147461_a(getEntityBoundingBox());

		if (var14.isEmpty() && !worldObj.func_175665_u(var7)) {
			return false;
		} else {
			byte var15 = 3;
			double var16 = 9999.0D;

			if (!worldObj.func_175665_u(var7.offsetWest()) && var8 < var16) {
				var16 = var8;
				var15 = 0;
			}

			if (!worldObj.func_175665_u(var7.offsetEast()) && 1.0D - var8 < var16) {
				var16 = 1.0D - var8;
				var15 = 1;
			}

			if (!worldObj.func_175665_u(var7.offsetUp()) && 1.0D - var10 < var16) {
				var16 = 1.0D - var10;
				var15 = 3;
			}

			if (!worldObj.func_175665_u(var7.offsetNorth()) && var12 < var16) {
				var16 = var12;
				var15 = 4;
			}

			if (!worldObj.func_175665_u(var7.offsetSouth()) && 1.0D - var12 < var16) {
				var16 = 1.0D - var12;
				var15 = 5;
			}

			final float var18 = rand.nextFloat() * 0.2F + 0.1F;

			if (var15 == 0) {
				motionX = -var18;
			}

			if (var15 == 1) {
				motionX = var18;
			}

			if (var15 == 3) {
				motionY = var18;
			}

			if (var15 == 4) {
				motionZ = -var18;
			}

			if (var15 == 5) {
				motionZ = var18;
			}

			return true;
		}
	}

	/**
	 * Sets the Entity inside a web block.
	 */
	public void setInWeb() {
		isInWeb = true;
		fallDistance = 0.0F;
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		if (hasCustomName()) {
			return getCustomNameTag();
		} else {
			String var1 = EntityList.getEntityString(this);

			if (var1 == null) {
				var1 = "generic";
			}

			return StatCollector.translateToLocal("entity." + var1 + ".name");
		}
	}

	/**
	 * Return the Entity parts making up this Entity (currently only for
	 * dragons)
	 */
	public Entity[] getParts() {
		return null;
	}

	/**
	 * Returns true if Entity argument is equal to this Entity
	 */
	public boolean isEntityEqual(final Entity entityIn) {
		return this == entityIn;
	}

	public float getRotationYawHead() {
		return 0.0F;
	}

	/**
	 * Sets the head's yaw rotation of the entity.
	 */
	public void setRotationYawHead(final float rotation) {}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	public boolean canAttackWithItem() {
		return true;
	}

	/**
	 * Called when a player attacks an entity. If this returns true the attack
	 * will not happen.
	 */
	public boolean hitByEntity(final Entity entityIn) {
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s[\'%s\'/%d, l=\'%s\', x=%.2f, y=%.2f, z=%.2f]",
				new Object[] { this.getClass().getSimpleName(), getName(), entityId,
						worldObj == null ? "~NULL~" : worldObj.getWorldInfo().getWorldName(), posX, posY, posZ });
	}

	public boolean func_180431_b(final DamageSource p_180431_1_) {
		return invulnerable && p_180431_1_ != DamageSource.outOfWorld && !p_180431_1_.func_180136_u();
	}

	/**
	 * Sets this entity's location and angles to the location and angles of the
	 * passed in entity.
	 */
	public void copyLocationAndAnglesFrom(final Entity entityIn) {
		setLocationAndAngles(entityIn.posX, entityIn.posY, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
	}

	public void func_180432_n(final Entity p_180432_1_) {
		final NBTTagCompound var2 = new NBTTagCompound();
		p_180432_1_.writeToNBT(var2);
		readFromNBT(var2);
		timeUntilPortal = p_180432_1_.timeUntilPortal;
		teleportDirection = p_180432_1_.teleportDirection;
	}

	/**
	 * Teleports the entity to another dimension. Params: Dimension number to
	 * teleport to
	 */
	public void travelToDimension(final int dimensionId) {
		if (!worldObj.isRemote && !isDead) {
			worldObj.theProfiler.startSection("changeDimension");
			final MinecraftServer var2 = MinecraftServer.getServer();
			final int var3 = dimension;
			final WorldServer var4 = var2.worldServerForDimension(var3);
			WorldServer var5 = var2.worldServerForDimension(dimensionId);
			dimension = dimensionId;

			if (var3 == 1 && dimensionId == 1) {
				var5 = var2.worldServerForDimension(0);
				dimension = 0;
			}

			worldObj.removeEntity(this);
			isDead = false;
			worldObj.theProfiler.startSection("reposition");
			var2.getConfigurationManager().transferEntityToWorld(this, var3, var4, var5);
			worldObj.theProfiler.endStartSection("reloading");
			final Entity var6 = EntityList.createEntityByName(EntityList.getEntityString(this), var5);

			if (var6 != null) {
				var6.func_180432_n(this);

				if (var3 == 1 && dimensionId == 1) {
					final BlockPos var7 = worldObj.func_175672_r(var5.getSpawnPoint());
					var6.func_174828_a(var7, var6.rotationYaw, var6.rotationPitch);
				}

				var5.spawnEntityInWorld(var6);
			}

			isDead = true;
			worldObj.theProfiler.endSection();
			var4.resetUpdateEntityTick();
			var5.resetUpdateEntityTick();
			worldObj.theProfiler.endSection();
		}
	}

	/**
	 * Explosion resistance of a block relative to this entity
	 */
	public float getExplosionResistance(final Explosion p_180428_1_, final World worldIn, final BlockPos p_180428_3_,
			final IBlockState p_180428_4_) {
		return p_180428_4_.getBlock().getExplosionResistance(this);
	}

	public boolean func_174816_a(final Explosion p_174816_1_, final World worldIn, final BlockPos p_174816_3_,
			final IBlockState p_174816_4_, final float p_174816_5_) {
		return true;
	}

	/**
	 * The maximum height from where the entity is alowed to jump (used in
	 * pathfinder)
	 */
	public int getMaxFallHeight() {
		return 3;
	}

	public int getTeleportDirection() {
		return teleportDirection;
	}

	/**
	 * Return whether this entity should NOT trigger a pressure plate or a
	 * tripwire.
	 */
	public boolean doesEntityNotTriggerPressurePlate() {
		return false;
	}

	public void addEntityCrashInfo(final CrashReportCategory category) {
		category.addCrashSectionCallable("Entity Type", new Callable() {
			@Override
			public String call() {
				return EntityList.getEntityString(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
			}
		});
		category.addCrashSection("Entity ID", entityId);
		category.addCrashSectionCallable("Entity Name", new Callable() {
			@Override
			public String call() {
				return Entity.this.getName();
			}
		});
		category.addCrashSection("Entity\'s Exact location",
				String.format("%.2f, %.2f, %.2f", new Object[] { posX, posY, posZ }));
		category.addCrashSection("Entity\'s Block location", CrashReportCategory.getCoordinateInfo(
				MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)));
		category.addCrashSection("Entity\'s Momentum",
				String.format("%.2f, %.2f, %.2f", new Object[] { motionX, motionY, motionZ }));
		category.addCrashSectionCallable("Entity\'s Rider", new Callable() {
			public String func_180118_a() {
				return riddenByEntity.toString();
			}

			@Override
			public Object call() {
				return func_180118_a();
			}
		});
		category.addCrashSectionCallable("Entity\'s Vehicle", new Callable() {
			public String func_180116_a() {
				return ridingEntity.toString();
			}

			@Override
			public Object call() {
				return func_180116_a();
			}
		});
	}

	/**
	 * Return whether this entity should be rendered as on fire.
	 */
	public boolean canRenderOnFire() {
		return isBurning();
	}

	public UUID getUniqueID() {
		return entityUniqueID;
	}

	public boolean isPushedByWater() {
		return true;
	}

	@Override
	public IChatComponent getDisplayName() {
		final ChatComponentText var1 = new ChatComponentText(getName());
		var1.getChatStyle().setChatHoverEvent(func_174823_aP());
		var1.getChatStyle().setInsertion(getUniqueID().toString());
		return var1;
	}

	/**
	 * Sets the custom name tag for this entity
	 */
	public void setCustomNameTag(final String p_96094_1_) {
		dataWatcher.updateObject(2, p_96094_1_);
	}

	public String getCustomNameTag() {
		return dataWatcher.getWatchableObjectString(2);
	}

	/**
	 * Returns true if this thing is named
	 */
	public boolean hasCustomName() {
		return dataWatcher.getWatchableObjectString(2).length() > 0;
	}

	public void setAlwaysRenderNameTag(final boolean p_174805_1_) {
		dataWatcher.updateObject(3, (byte) (p_174805_1_ ? 1 : 0));
	}

	public boolean getAlwaysRenderNameTag() {
		return dataWatcher.getWatchableObjectByte(3) == 1;
	}

	/**
	 * Sets the position of the entity and updates the 'last' variables
	 */
	public void setPositionAndUpdate(final double p_70634_1_, final double p_70634_3_, final double p_70634_5_) {
		setLocationAndAngles(p_70634_1_, p_70634_3_, p_70634_5_, rotationYaw, rotationPitch);
	}

	public boolean getAlwaysRenderNameTagForRender() {
		return getAlwaysRenderNameTag();
	}

	public void func_145781_i(final int p_145781_1_) {}

	public EnumFacing func_174811_aO() {
		return EnumFacing.getHorizontal(MathHelper.floor_double(rotationYaw * 4.0F / 360.0F + 0.5D) & 3);
	}

	protected HoverEvent func_174823_aP() {
		final NBTTagCompound var1 = new NBTTagCompound();
		final String var2 = EntityList.getEntityString(this);
		var1.setString("id", getUniqueID().toString());

		if (var2 != null) {
			var1.setString("type", var2);
		}

		var1.setString("name", getName());
		return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ChatComponentText(var1.toString()));
	}

	public boolean func_174827_a(final EntityPlayerMP p_174827_1_) {
		return true;
	}

	public AxisAlignedBB getEntityBoundingBox() {
		return boundingBox;
	}

	public void func_174826_a(final AxisAlignedBB p_174826_1_) {
		boundingBox = p_174826_1_;
	}

	public float getEyeHeight() {
		return height * 0.85F;
	}

	public boolean isOutsideBorder() {
		return isOutsideBorder;
	}

	public void setOutsideBorder(final boolean p_174821_1_) {
		isOutsideBorder = p_174821_1_;
	}

	public boolean func_174820_d(final int p_174820_1_, final ItemStack p_174820_2_) {
		return false;
	}

	/**
	 * Notifies this sender of some sort of information. This is for messages
	 * intended to display to the user. Used for typical output (like "you asked
	 * for whether or not this game rule is set, so here's your answer" ),
	 * warnings (like "I fetched this block for you by ID, but I'd like you to
	 * know that every time you do this, I die a little inside "), and errors
	 * (like "it's not called iron_pixacke, silly").
	 */
	@Override
	public void addChatMessage(final IChatComponent message) {}

	/**
	 * Returns true if the command sender is allowed to use the given command.
	 */
	@Override
	public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
		return true;
	}

	@Override
	public BlockPos getPosition() {
		return new BlockPos(posX, posY + 0.5D, posZ);
	}

	@Override
	public Vec3 getPositionVector() {
		return new Vec3(posX, posY, posZ);
	}

	@Override
	public World getEntityWorld() {
		return worldObj;
	}

	@Override
	public Entity getCommandSenderEntity() {
		return this;
	}

	@Override
	public boolean sendCommandFeedback() {
		return false;
	}

	@Override
	public void func_174794_a(final CommandResultStats.Type p_174794_1_, final int p_174794_2_) {
		field_174837_as.func_179672_a(this, p_174794_1_, p_174794_2_);
	}

	public CommandResultStats func_174807_aT() {
		return field_174837_as;
	}

	public void func_174817_o(final Entity p_174817_1_) {
		field_174837_as.func_179671_a(p_174817_1_.func_174807_aT());
	}

	public NBTTagCompound func_174819_aU() {
		return null;
	}

	public void func_174834_g(final NBTTagCompound p_174834_1_) {}

	public boolean func_174825_a(final EntityPlayer p_174825_1_, final Vec3 p_174825_2_) {
		return false;
	}

	public boolean func_180427_aV() {
		return false;
	}

	protected void func_174815_a(final EntityLivingBase p_174815_1_, final Entity p_174815_2_) {
		if (p_174815_2_ instanceof EntityLivingBase) {
			EnchantmentHelper.func_151384_a((EntityLivingBase) p_174815_2_, p_174815_1_);
		}

		EnchantmentHelper.func_151385_b(p_174815_1_, p_174815_2_);
	}
}
