package net.minecraft.entity.player;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import me.EaZy.client.events.EventJump;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.NoKnockBack;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

import com.darkmagician6.eventapi.EventManager;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

public abstract class EntityPlayer extends EntityLivingBase {

	public static final int EaZy = 1191;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	/**
	 * Inventory of the player
	 */
	public InventoryPlayer inventory = new InventoryPlayer(this);
	private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();

	/**
	 * The Container for the player's inventory (which opens when they press E)
	 */
	public Container inventoryContainer;

	/**
	 * The Container the player has open.
	 */
	public Container openContainer;

	/**
	 * The food object of the player, the general hunger logic.
	 */
	protected FoodStats foodStats = new FoodStats();

	/**
	 * Used to tell if the player pressed jump twice. If this is at 0 and it's
	 * pressed (And they are allowed to fly, as defined in the player's
	 * movementInput) it sets this to 7. If it's pressed and it's greater than 0
	 * enable fly.
	 */
	protected int flyToggleTimer;
	public float prevCameraYaw;
	public float cameraYaw;

	/**
	 * Used by EntityPlayer to prevent too many xp orbs from getting absorbed at
	 * once.
	 */
	public int xpCooldown;
	public double field_71091_bM;
	public double field_71096_bN;
	public double field_71097_bO;
	public double field_71094_bP;
	public double field_71095_bQ;
	public double field_71085_bR;

	/**
	 * Boolean value indicating weather a player is sleeping or not
	 */
	protected boolean sleeping;

	/**
	 * the current location of the player
	 */
	public BlockPos playerLocation;
	private int sleepTimer;
	public float field_71079_bU;
	public float field_71082_cx;
	public float field_71089_bV;

	/**
	 * holds the spawn chunk of the player
	 */
	private BlockPos spawnChunk;

	/**
	 * Whether this player's spawn point is forced, preventing execution of bed
	 * checks.
	 */
	private boolean spawnForced;

	/**
	 * Holds the coordinate of the player when enter a minecraft to ride.
	 */
	private BlockPos startMinecartRidingCoordinate;

	/**
	 * The player's capabilities. (See class PlayerCapabilities)
	 */
	public PlayerCapabilities capabilities = new PlayerCapabilities();

	/**
	 * The current experience level the player is on.
	 */
	public int experienceLevel;

	/**
	 * The total amount of experience the player has. This also includes the
	 * amount of experience within their Experience Bar.
	 */
	public int experienceTotal;

	/**
	 * The current amount of experience the player has within their Experience
	 * Bar.
	 */
	public float experience;
	private int field_175152_f;

	/**
	 * This is the item that is in use when the player is holding down the
	 * useItemButton (e.g., bow, food, sword)
	 */
	private ItemStack itemInUse;

	/**
	 * This field starts off equal to getMaxItemUseDuration and is decremented
	 * on each tick
	 */
	private int itemInUseCount;
	protected float speedOnGround = 0.1F;
	public float speedInAir = 0.02F;
	private int field_82249_h;

	/**
	 * The player's unique game profile
	 */
	public GameProfile gameProfile;
	private boolean field_175153_bG = false;

	public boolean tooFastDown = false;
	public boolean wallDmgd = false;

	/**
	 * An instance of a fishing rod's hook. If this isn't null, the icon image
	 * of the fishing rod is slightly different
	 */
	public EntityFishHook fishEntity;

	public EntityPlayer(final World worldIn, final GameProfile p_i45324_2_) {
		super(worldIn);
		entityUniqueID = getUUID(p_i45324_2_);
		gameProfile = p_i45324_2_;
		inventoryContainer = new ContainerPlayer(inventory, !worldIn.isRemote, this);
		openContainer = inventoryContainer;
		final BlockPos var3 = worldIn.getSpawnPoint();
		setLocationAndAngles(var3.getX() + 0.5D, var3.getY() + 1, var3.getZ() + 0.5D, 0.0F, 0.0F);
		field_70741_aB = 180.0F;
		fireResistance = 20;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, 0.0F);
		dataWatcher.addObject(18, 0);
		dataWatcher.addObject(10, (byte) 0);
	}

	/**
	 * returns the ItemStack containing the itemInUse
	 */
	public ItemStack getItemInUse() {
		return itemInUse;
	}

	/**
	 * Returns the item in use count
	 */
	public int getItemInUseCount() {
		return itemInUseCount;
	}

	/**
	 * Checks if the entity is currently using an item (e.g., bow, food, sword)
	 * by holding down the useItemButton
	 */
	public boolean isUsingItem() {
		return itemInUse != null;
	}

	/**
	 * gets the duration for how long the current itemInUse has been in use
	 */
	public int getItemInUseDuration() {
		return isUsingItem() ? itemInUse.getMaxItemUseDuration() - itemInUseCount : 0;
	}

	public void stopUsingItem() {
		if (itemInUse != null) {
			itemInUse.onPlayerStoppedUsing(worldObj, this, itemInUseCount);
		}

		clearItemInUse();
	}

	public void clearItemInUse() {
		itemInUse = null;
		itemInUseCount = 0;

		if (!worldObj.isRemote) {
			setEating(false);
		}
	}

	public boolean isBlocking() {
		return isUsingItem() && itemInUse.getItem().getItemUseAction(itemInUse) == EnumAction.BLOCK;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		noClip = isSpectatorMode();

		if (isSpectatorMode()) {
			onGround = false;
		}

		if (itemInUse != null) {
			final ItemStack var1 = inventory.getCurrentItem();

			if (var1 == itemInUse) {
				if (itemInUseCount <= 25 && itemInUseCount % 4 == 0) {
					updateItemUse(var1, 5);
				}

				if (--itemInUseCount == 0 && !worldObj.isRemote) {
					onItemUseFinish();
				}
			} else {
				clearItemInUse();
			}
		}

		if (xpCooldown > 0) {
			--xpCooldown;
		}

		if (isPlayerSleeping()) {
			++sleepTimer;

			if (sleepTimer > 100) {
				sleepTimer = 100;
			}

			if (!worldObj.isRemote) {
				if (!func_175143_p()) {
					wakeUpPlayer(true, true, false);
				} else if (worldObj.isDaytime()) {
					wakeUpPlayer(false, true, true);
				}
			}
		} else if (sleepTimer > 0) {
			++sleepTimer;

			if (sleepTimer >= 110) {
				sleepTimer = 0;
			}
		}

		super.onUpdate();

		if (!worldObj.isRemote && openContainer != null && !openContainer.canInteractWith(this)) {
			closeScreen();
			openContainer = inventoryContainer;
		}

		if (isBurning() && capabilities.disableDamage) {
			extinguish();
		}

		field_71091_bM = field_71094_bP;
		field_71096_bN = field_71095_bQ;
		field_71097_bO = field_71085_bR;
		final double var14 = posX - field_71094_bP;
		final double var3 = posY - field_71095_bQ;
		final double var5 = posZ - field_71085_bR;
		final double var7 = 10.0D;

		if (var14 > var7) {
			field_71091_bM = field_71094_bP = posX;
		}

		if (var5 > var7) {
			field_71097_bO = field_71085_bR = posZ;
		}

		if (var3 > var7) {
			field_71096_bN = field_71095_bQ = posY;
		}

		if (var14 < -var7) {
			field_71091_bM = field_71094_bP = posX;
		}

		if (var5 < -var7) {
			field_71097_bO = field_71085_bR = posZ;
		}

		if (var3 < -var7) {
			field_71096_bN = field_71095_bQ = posY;
		}

		field_71094_bP += var14 * 0.25D;
		field_71085_bR += var5 * 0.25D;
		field_71095_bQ += var3 * 0.25D;

		if (ridingEntity == null) {
			startMinecartRidingCoordinate = null;
		}

		if (!worldObj.isRemote) {
			foodStats.onUpdate(this);
			triggerAchievement(StatList.minutesPlayedStat);

			if (isEntityAlive()) {
				triggerAchievement(StatList.timeSinceDeathStat);
			}
		}

		final double var10 = MathHelper.clamp_double(posX, -2.9999999E7D, 2.9999999E7D);
		final double var12 = MathHelper.clamp_double(posZ, -2.9999999E7D, 2.9999999E7D);

		if (var10 != posX || var12 != posZ) {
			setPosition(var10, posY, var12);
		}

		if (prevPosY - posY > 7 || prevPosX - posX > 3 || prevPosZ - posZ > 3) {
			tooFastDown = true;
		}
	}

	/**
	 * Return the amount of time this entity should stay in a portal before
	 * being transported.
	 */
	@Override
	public int getMaxInPortalTime() {
		return capabilities.disableDamage ? 0 : 80;
	}

	@Override
	protected String getSwimSound() {
		return "game.player.swim";
	}

	@Override
	protected String getSplashSound() {
		return "game.player.swim.splash";
	}

	/**
	 * Return the amount of cooldown before this entity can use a portal again.
	 */
	@Override
	public int getPortalCooldown() {
		return 10;
	}

	@Override
	public void playSound(final String name, final float volume, final float pitch) {
		worldObj.playSoundToNearExcept(this, name, volume, pitch);
	}

	/**
	 * Plays sounds and makes particles for item in use state
	 */
	protected void updateItemUse(final ItemStack itemStackIn, final int p_71010_2_) {
		if (itemStackIn.getItemUseAction() == EnumAction.DRINK) {
			playSound("random.drink", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (itemStackIn.getItemUseAction() == EnumAction.EAT) {
			for (int var3 = 0; var3 < p_71010_2_; ++var3) {
				Vec3 var4 = new Vec3((rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
				var4 = var4.rotatePitch(-rotationPitch * (float) Math.PI / 180.0F);
				var4 = var4.rotateYaw(-rotationYaw * (float) Math.PI / 180.0F);
				final double var5 = -rand.nextFloat() * 0.6D - 0.3D;
				Vec3 var7 = new Vec3((rand.nextFloat() - 0.5D) * 0.3D, var5, 0.6D);
				var7 = var7.rotatePitch(-rotationPitch * (float) Math.PI / 180.0F);
				var7 = var7.rotateYaw(-rotationYaw * (float) Math.PI / 180.0F);
				var7 = var7.addVector(posX, posY + getEyeHeight(), posZ);

				if (itemStackIn.getHasSubtypes()) {
					worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, var7.xCoord, var7.yCoord, var7.zCoord,
							var4.xCoord, var4.yCoord + 0.05D, var4.zCoord,
							new int[] { Item.getIdFromItem(itemStackIn.getItem()), itemStackIn.getMetadata() });
				} else {
					worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, var7.xCoord, var7.yCoord, var7.zCoord,
							var4.xCoord, var4.yCoord + 0.05D, var4.zCoord,
							new int[] { Item.getIdFromItem(itemStackIn.getItem()) });
				}
			}

			playSound("random.eat", 0.5F + 0.5F * rand.nextInt(2), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
		}
	}

	/**
	 * Used for when item use count runs out, ie: eating completed
	 */
	protected void onItemUseFinish() {
		if (itemInUse != null) {
			updateItemUse(itemInUse, 16);
			final int var1 = itemInUse.stackSize;
			final ItemStack var2 = itemInUse.onItemUseFinish(worldObj, this);

			if (var2 != itemInUse || var2 != null && var2.stackSize != var1) {
				inventory.mainInventory[inventory.currentItem] = var2;

				if (var2.stackSize == 0) {
					inventory.mainInventory[inventory.currentItem] = null;
				}
			}

			clearItemInUse();
		}
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		switch (p_70103_1_) {
		case 9:
			onItemUseFinish();
			break;
		case 23:
			field_175153_bG = false;
			break;
		case 22:
			field_175153_bG = true;
			break;
		default:
			super.handleHealthUpdate(p_70103_1_);
			break;
		}
	}

	/**
	 * Dead and sleeping entities cannot move
	 */
	@Override
	protected boolean isMovementBlocked() {
		return getHealth() <= 0.0F || isPlayerSleeping();
	}

	/**
	 * set current crafting inventory back to the 2x2 square
	 */
	protected void closeScreen() {
		openContainer = inventoryContainer;
	}

	/**
	 * Handles updating while being ridden by an entity
	 */
	@Override
	public void updateRidden() {
		if (!worldObj.isRemote && isSneaking()) {
			mountEntity((Entity) null);
			setSneaking(false);
		} else {
			final double var1 = posX;
			final double var3 = posY;
			final double var5 = posZ;
			final float var7 = rotationYaw;
			final float var8 = rotationPitch;
			super.updateRidden();
			prevCameraYaw = cameraYaw;
			cameraYaw = 0.0F;
			addMountedMovementStat(posX - var1, posY - var3, posZ - var5);

			if (ridingEntity instanceof EntityPig) {
				rotationPitch = var8;
				rotationYaw = var7;
				renderYawOffset = ((EntityPig) ridingEntity).renderYawOffset;
			}
		}
	}

	/**
	 * Keeps moving the entity up so it isn't colliding with blocks and other
	 * requirements for this entity to be spawned (only actually used on players
	 * though its also on Entity)
	 */
	@Override
	public void preparePlayerToSpawn() {
		setSize(0.6F, 1.8F);
		super.preparePlayerToSpawn();
		setHealth(getMaxHealth());
		deathTime = 0;
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();
		updateArmSwingProgress();
		rotationYawHead = rotationYaw;
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (flyToggleTimer > 0) {
			--flyToggleTimer;
		}

		if (worldObj.getDifficulty() == EnumDifficulty.PEACEFUL
				&& worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) {
			if (getHealth() < getMaxHealth() && ticksExisted % 20 == 0) {
				heal(1.0F);
			}

			if (foodStats.needFood() && ticksExisted % 10 == 0) {
				foodStats.setFoodLevel(foodStats.getFoodLevel() + 1);
			}
		}

		inventory.decrementAnimations();
		prevCameraYaw = cameraYaw;
		super.onLivingUpdate();
		final IAttributeInstance var1 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (!worldObj.isRemote) {
			var1.setBaseValue(capabilities.getWalkSpeed());
		}

		jumpMovementFactor = speedInAir;

		if (isSprinting()) {
			jumpMovementFactor = (float) (jumpMovementFactor + speedInAir * 0.3D);
		}

		setAIMoveSpeed((float) var1.getAttributeValue());
		float var2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		float var3 = (float) (Math.atan(-motionY * 0.20000000298023224D) * 15.0D);

		if (var2 > 0.1F) {
			var2 = 0.1F;
		}

		if (!onGround || getHealth() <= 0.0F) {
			var2 = 0.0F;
		}

		if (onGround || getHealth() <= 0.0F) {
			var3 = 0.0F;
		}

		cameraYaw += (var2 - cameraYaw) * 0.4F;
		cameraPitch += (var3 - cameraPitch) * 0.8F;

		if (getHealth() > 0.0F && !isSpectatorMode()) {
			AxisAlignedBB var4 = null;

			if (ridingEntity != null && !ridingEntity.isDead) {
				var4 = getEntityBoundingBox().union(ridingEntity.getEntityBoundingBox()).expand(1.0D, 0.0D, 1.0D);
			} else {
				var4 = getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D);
			}

			final List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, var4);

			for (int var6 = 0; var6 < var5.size(); ++var6) {
				final Entity var7 = (Entity) var5.get(var6);

				if (!var7.isDead) {
					collideWithPlayer(var7);
				}
			}
		}
	}

	private void collideWithPlayer(final Entity p_71044_1_) {
		p_71044_1_.onCollideWithPlayer(this);
	}

	public int getScore() {
		return dataWatcher.getWatchableObjectInt(18);
	}

	/**
	 * Set player's score
	 */
	public void setScore(final int p_85040_1_) {
		dataWatcher.updateObject(18, p_85040_1_);
	}

	/**
	 * Add to player's score
	 */
	public void addScore(final int p_85039_1_) {
		final int var2 = getScore();
		dataWatcher.updateObject(18, var2 + p_85039_1_);
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		System.out.println(cause);
		super.onDeath(cause);
		setSize(0.2F, 0.2F);
		setPosition(posX, posY, posZ);
		motionY = 0.10000000149011612D;

		if (getName().equals("Notch")) {
			func_146097_a(new ItemStack(Items.apple, 1), true, false);
		}

		if (!worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
			inventory.dropAllItems();
		}

		if (cause != null) {
			motionX = -MathHelper.cos((attackedAtYaw + rotationYaw) * (float) Math.PI / 180.0F) * 0.1F;
			motionZ = -MathHelper.sin((attackedAtYaw + rotationYaw) * (float) Math.PI / 180.0F) * 0.1F;
		} else {
			motionX = motionZ = 0.0D;
		}

		triggerAchievement(StatList.deathsStat);
		func_175145_a(StatList.timeSinceDeathStat);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "game.player.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "game.player.die";
	}

	/**
	 * Adds a value to the player score. Currently not actually used and the
	 * entity passed in does nothing. Args: entity, scoreToAdd
	 */
	@Override
	public void addToPlayerScore(final Entity entityIn, final int amount) {
		addScore(amount);
		final Collection var3 = getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.totalKillCount);

		if (entityIn instanceof EntityPlayer) {
			triggerAchievement(StatList.playerKillsStat);
			var3.addAll(getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.playerKillCount));
			var3.addAll(func_175137_e(entityIn));
		} else {
			triggerAchievement(StatList.mobKillsStat);
		}

		final Iterator var4 = var3.iterator();

		while (var4.hasNext()) {
			final ScoreObjective var5 = (ScoreObjective) var4.next();
			final Score var6 = getWorldScoreboard().getValueFromObjective(getName(), var5);
			var6.func_96648_a();
		}
	}

	private Collection func_175137_e(final Entity p_175137_1_) {
		final ScorePlayerTeam var2 = getWorldScoreboard().getPlayersTeam(getName());

		if (var2 != null) {
			final int var3 = var2.func_178775_l().func_175746_b();

			if (var3 >= 0 && var3 < IScoreObjectiveCriteria.field_178793_i.length) {
				final Iterator var4 = getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.field_178793_i[var3])
						.iterator();

				while (var4.hasNext()) {
					final ScoreObjective var5 = (ScoreObjective) var4.next();
					final Score var6 = getWorldScoreboard().getValueFromObjective(p_175137_1_.getName(), var5);
					var6.func_96648_a();
				}
			}
		}

		final ScorePlayerTeam var7 = getWorldScoreboard().getPlayersTeam(p_175137_1_.getName());

		if (var7 != null) {
			final int var8 = var7.func_178775_l().func_175746_b();

			if (var8 >= 0 && var8 < IScoreObjectiveCriteria.field_178792_h.length) {
				return getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.field_178792_h[var8]);
			}
		}

		return Lists.newArrayList();
	}

	/**
	 * Called when player presses the drop item key
	 */
	public EntityItem dropOneItem(final boolean p_71040_1_) {
		return func_146097_a(
				inventory.decrStackSize(inventory.currentItem,
						p_71040_1_ && inventory.getCurrentItem() != null ? inventory.getCurrentItem().stackSize : 1),
				false, true);
	}

	/**
	 * Args: itemstack, flag
	 */
	public EntityItem dropPlayerItemWithRandomChoice(final ItemStack itemStackIn, final boolean p_71019_2_) {
		return func_146097_a(itemStackIn, false, false);
	}

	public EntityItem func_146097_a(final ItemStack p_146097_1_, final boolean p_146097_2_, final boolean p_146097_3_) {
		if (p_146097_1_ == null) {
			return null;
		} else if (p_146097_1_.stackSize == 0) {
			return null;
		} else {
			final double var4 = posY - 0.30000001192092896D + getEyeHeight();
			final EntityItem var6 = new EntityItem(worldObj, posX, var4, posZ, p_146097_1_);
			var6.setPickupDelay(40);

			if (p_146097_3_) {
				var6.setThrower(getName());
			}

			float var7;
			float var8;

			if (p_146097_2_) {
				var7 = rand.nextFloat() * 0.5F;
				var8 = rand.nextFloat() * (float) Math.PI * 2.0F;
				var6.motionX = -MathHelper.sin(var8) * var7;
				var6.motionZ = MathHelper.cos(var8) * var7;
				var6.motionY = 0.20000000298023224D;
			} else {
				var7 = 0.3F;
				var6.motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
						* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var7;
				var6.motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
						* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var7;
				var6.motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI) * var7 + 0.1F;
				var8 = rand.nextFloat() * (float) Math.PI * 2.0F;
				var7 = 0.02F * rand.nextFloat();
				var6.motionX += Math.cos(var8) * var7;
				var6.motionY += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				var6.motionZ += Math.sin(var8) * var7;
			}

			joinEntityItemWithWorld(var6);

			if (p_146097_3_) {
				triggerAchievement(StatList.dropStat);
			}

			return var6;
		}
	}

	/**
	 * Joins the passed in entity item with the world. Args: entityItem
	 */
	protected void joinEntityItemWithWorld(final EntityItem p_71012_1_) {
		worldObj.spawnEntityInWorld(p_71012_1_);
	}

	public float func_180471_a(final Block p_180471_1_) {
		float var2 = inventory.getStrVsBlock(p_180471_1_);

		if (var2 > 1.0F) {
			final int var3 = EnchantmentHelper.getEfficiencyModifier(this);
			final ItemStack var4 = inventory.getCurrentItem();

			if (var3 > 0 && var4 != null) {
				var2 += var3 * var3 + 1;
			}
		}

		if (this.isPotionActive(Potion.digSpeed)) {
			var2 *= 1.0F + (getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
		}

		if (this.isPotionActive(Potion.digSlowdown)) {
			float var5 = 1.0F;

			switch (getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
			case 0:
				var5 = 0.3F;
				break;

			case 1:
				var5 = 0.09F;
				break;

			case 2:
				var5 = 0.0027F;
				break;

			case 3:
			default:
				var5 = 8.1E-4F;
			}

			var2 *= var5;
		}

		if (isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
			var2 /= 5.0F;
		}

		if (!onGround) {
			var2 /= 5.0F;
		}

		return var2;
	}

	/**
	 * Checks if the player has the ability to harvest a block (checks current
	 * inventory item for a tool if necessary)
	 */
	public boolean canHarvestBlock(final Block p_146099_1_) {
		return inventory.func_146025_b(p_146099_1_);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		entityUniqueID = getUUID(gameProfile);
		final NBTTagList var2 = tagCompund.getTagList("Inventory", 10);
		inventory.readFromNBT(var2);
		inventory.currentItem = tagCompund.getInteger("SelectedItemSlot");
		sleeping = tagCompund.getBoolean("Sleeping");
		sleepTimer = tagCompund.getShort("SleepTimer");
		experience = tagCompund.getFloat("XpP");
		experienceLevel = tagCompund.getInteger("XpLevel");
		experienceTotal = tagCompund.getInteger("XpTotal");
		field_175152_f = tagCompund.getInteger("XpSeed");

		if (field_175152_f == 0) {
			field_175152_f = rand.nextInt();
		}

		setScore(tagCompund.getInteger("Score"));

		if (sleeping) {
			playerLocation = new BlockPos(this);
			wakeUpPlayer(true, true, false);
		}

		if (tagCompund.hasKey("SpawnX", 99) && tagCompund.hasKey("SpawnY", 99) && tagCompund.hasKey("SpawnZ", 99)) {
			spawnChunk = new BlockPos(tagCompund.getInteger("SpawnX"), tagCompund.getInteger("SpawnY"),
					tagCompund.getInteger("SpawnZ"));
			spawnForced = tagCompund.getBoolean("SpawnForced");
		}

		foodStats.readNBT(tagCompund);
		capabilities.readCapabilitiesFromNBT(tagCompund);

		if (tagCompund.hasKey("EnderItems", 9)) {
			final NBTTagList var3 = tagCompund.getTagList("EnderItems", 10);
			theInventoryEnderChest.loadInventoryFromNBT(var3);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setTag("Inventory", inventory.writeToNBT(new NBTTagList()));
		tagCompound.setInteger("SelectedItemSlot", inventory.currentItem);
		tagCompound.setBoolean("Sleeping", sleeping);
		tagCompound.setShort("SleepTimer", (short) sleepTimer);
		tagCompound.setFloat("XpP", experience);
		tagCompound.setInteger("XpLevel", experienceLevel);
		tagCompound.setInteger("XpTotal", experienceTotal);
		tagCompound.setInteger("XpSeed", field_175152_f);
		tagCompound.setInteger("Score", getScore());

		if (spawnChunk != null) {
			tagCompound.setInteger("SpawnX", spawnChunk.getX());
			tagCompound.setInteger("SpawnY", spawnChunk.getY());
			tagCompound.setInteger("SpawnZ", spawnChunk.getZ());
			tagCompound.setBoolean("SpawnForced", spawnForced);
		}

		foodStats.writeNBT(tagCompound);
		capabilities.writeCapabilitiesToNBT(tagCompound);
		tagCompound.setTag("EnderItems", theInventoryEnderChest.saveInventoryToNBT());
		final ItemStack var2 = inventory.getCurrentItem();

		if (var2 != null && var2.getItem() != null) {
			tagCompound.setTag("SelectedItem", var2.writeToNBT(new NBTTagCompound()));
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, float amount) {
		if (source == DamageSource.inWall) {
			wallDmgd = true;
		}
		if (func_180431_b(source)) {
			return false;
		} else if (capabilities.disableDamage && !source.canHarmInCreative()) {
			return false;
		} else {
			entityAge = 0;

			if (getHealth() <= 0.0F) {
				return false;
			} else {
				if (isPlayerSleeping() && !worldObj.isRemote) {
					wakeUpPlayer(true, true, false);
				}

				if (source.isDifficultyScaled()) {
					if (worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
						amount = 0.0F;
					}

					if (worldObj.getDifficulty() == EnumDifficulty.EASY) {
						amount = amount / 2.0F + 1.0F;
					}

					if (worldObj.getDifficulty() == EnumDifficulty.HARD) {
						amount = amount * 3.0F / 2.0F;
					}
				}

				if (amount == 0.0F) {
					return false;
				} else {
					Entity var3 = source.getEntity();

					if (var3 instanceof EntityArrow && ((EntityArrow) var3).shootingEntity != null) {
						var3 = ((EntityArrow) var3).shootingEntity;
					}

					return super.attackEntityFrom(source, amount);
				}
			}
		}
	}

	public boolean canAttackPlayer(final EntityPlayer other) {
		final Team var2 = getTeam();
		final Team var3 = other.getTeam();
		return var2 == null ? true : !var2.isSameTeam(var3) ? true : var2.getAllowFriendlyFire();
	}

	@Override
	protected void damageArmor(final float p_70675_1_) {
		inventory.damageArmor(p_70675_1_);
	}

	/**
	 * Returns the current armor value as determined by a call to
	 * InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue() {
		return inventory.getTotalArmorValue();
	}

	/**
	 * When searching for vulnerable players, if a player is invisible, the
	 * return value of this is the chance of seeing them anyway.
	 */
	public float getArmorVisibility() {
		int var1 = 0;
		final ItemStack[] var2 = inventory.armorInventory;
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final ItemStack var5 = var2[var4];

			if (var5 != null) {
				++var1;
			}
		}

		return (float) var1 / (float) inventory.armorInventory.length;
	}

	/**
	 * Deals damage to the entity. If its a EntityPlayer then will take damage
	 * from the armor first and then health second with the reduced value. Args:
	 * damageAmount
	 */
	@Override
	protected void damageEntity(final DamageSource source, float p_70665_2_) {
		if (!func_180431_b(source)) {
			if (!source.isUnblockable() && isBlocking() && p_70665_2_ > 0.0F) {
				p_70665_2_ = (1.0F + p_70665_2_) * 0.5F;
			}

			p_70665_2_ = applyArmorCalculations(source, p_70665_2_);
			p_70665_2_ = applyPotionDamageCalculations(source, p_70665_2_);
			final float var3 = p_70665_2_;
			p_70665_2_ = Math.max(p_70665_2_ - getAbsorptionAmount(), 0.0F);
			setAbsorptionAmount(getAbsorptionAmount() - (var3 - p_70665_2_));

			if (p_70665_2_ != 0.0F) {
				addExhaustion(source.getHungerDamage());
				final float var4 = getHealth();
				setHealth(getHealth() - p_70665_2_);
				getCombatTracker().func_94547_a(source, var4, p_70665_2_);

				if (p_70665_2_ < 3.4028235E37F) {
					addStat(StatList.damageTakenStat, Math.round(p_70665_2_ * 10.0F));
				}
			}
		}
	}

	public void func_175141_a(final TileEntitySign p_175141_1_) {}

	public void displayCommandBlockGUI(final CommandBlockLogic p_146095_1_) {}

	public void displayVillagerTradeGui(final IMerchant villager) {}

	/**
	 * Displays the GUI for interacting with a chest inventory. Args:
	 * chestInventory
	 */
	public void displayGUIChest(final IInventory chestInventory) {}

	public void displayGUIHorse(final EntityHorse p_110298_1_, final IInventory p_110298_2_) {}

	public void displayGui(final IInteractionObject guiOwner) {}

	/**
	 * Displays the GUI for interacting with a book.
	 */
	public void displayGUIBook(final ItemStack bookStack) {}

	public boolean interactWith(final Entity p_70998_1_) {
		if (isSpectatorMode()) {
			if (p_70998_1_ instanceof IInventory) {
				displayGUIChest((IInventory) p_70998_1_);
			}

			return false;
		} else {
			ItemStack var2 = getCurrentEquippedItem();
			final ItemStack var3 = var2 != null ? var2.copy() : null;

			if (!p_70998_1_.interactFirst(this)) {
				if (var2 != null && p_70998_1_ instanceof EntityLivingBase) {
					if (capabilities.isCreativeMode) {
						var2 = var3;
					}

					if (var2.interactWithEntity(this, (EntityLivingBase) p_70998_1_)) {
						if (var2.stackSize <= 0 && !capabilities.isCreativeMode) {
							destroyCurrentEquippedItem();
						}

						return true;
					}
				}

				return false;
			} else {
				if (var2 != null && var2 == getCurrentEquippedItem()) {
					if (var2.stackSize <= 0 && !capabilities.isCreativeMode) {
						destroyCurrentEquippedItem();
					} else if (var2.stackSize < var3.stackSize && capabilities.isCreativeMode) {
						var2.stackSize = var3.stackSize;
					}
				}

				return true;
			}
		}
	}

	/**
	 * Returns the currently being used item by the player.
	 */
	public ItemStack getCurrentEquippedItem() {
		return inventory.getCurrentItem();
	}

	/**
	 * Destroys the currently equipped item from the player's inventory.
	 */
	public void destroyCurrentEquippedItem() {
		inventory.setInventorySlotContents(inventory.currentItem, (ItemStack) null);
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	@Override
	public double getYOffset() {
		return -0.35D;
	}

	/**
	 * Attacks for the player the targeted entity with the currently equipped
	 * item. The equipped item has hitEntity called on it. Args: targetEntity
	 */
	public void attackTargetEntityWithCurrentItem(final Entity targetEntity) {
		if (targetEntity.canAttackWithItem()) {
			if (!targetEntity.hitByEntity(this)) {
				float var2 = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
				float var4 = 0.0F;

				if (targetEntity instanceof EntityLivingBase) {
					var4 = EnchantmentHelper.func_152377_a(getHeldItem(),
							((EntityLivingBase) targetEntity).getCreatureAttribute());
				} else {
					var4 = EnchantmentHelper.func_152377_a(getHeldItem(), EnumCreatureAttribute.UNDEFINED);
				}

				int knockbackAmplifier = EnchantmentHelper.getRespiration(this);

				if (isSprinting()) {
					++knockbackAmplifier;
				}

				if (var2 > 0.0F || var4 > 0.0F) {
					final boolean var5 = fallDistance > 0.0F && !onGround && !isOnLadder() && !isInWater()
							&& !this.isPotionActive(Potion.blindness) && ridingEntity == null
							&& targetEntity instanceof EntityLivingBase;

					if (var5 && var2 > 0.0F) {
						var2 *= 1.5F;
					}

					var2 += var4;
					boolean var6 = false;
					final int var7 = EnchantmentHelper.getFireAspectModifier(this);

					if (targetEntity instanceof EntityLivingBase && var7 > 0 && !targetEntity.isBurning()) {
						var6 = true;
						targetEntity.setFire(1);
					}

					final double preMotX = targetEntity.motionX;
					final double preMotY = targetEntity.motionY;
					final double preMotZ = targetEntity.motionZ;
					final boolean gotDamaged = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(this),
							var2);

					if (gotDamaged) {
						if (knockbackAmplifier > 0) {
							targetEntity.addVelocity(
									-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * knockbackAmplifier * 0.5F,
									0.1D,
									MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * knockbackAmplifier * 0.5F);
						}

						if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
							((EntityPlayerMP) targetEntity).playerNetServerHandler
									.sendPacket(new S12PacketEntityVelocity(targetEntity));
							targetEntity.velocityChanged = false;
							targetEntity.motionX = preMotX;
							targetEntity.motionY = preMotY;
							targetEntity.motionZ = preMotZ;
						}

						if (var5) {
							onCriticalHit(targetEntity);
						}

						if (var4 > 0.0F) {
							onEnchantmentCritical(targetEntity);
						}

						if (var2 >= 18.0F) {
							triggerAchievement(AchievementList.overkill);
						}

						setLastAttacker(targetEntity);

						if (targetEntity instanceof EntityLivingBase) {
							EnchantmentHelper.func_151384_a((EntityLivingBase) targetEntity, this);
						}

						EnchantmentHelper.func_151385_b(this, targetEntity);
						final ItemStack var15 = getCurrentEquippedItem();
						Object var16 = targetEntity;

						if (targetEntity instanceof EntityDragonPart) {
							final IEntityMultiPart var17 = ((EntityDragonPart) targetEntity).entityDragonObj;

							if (var17 instanceof EntityLivingBase) {
								var16 = var17;
							}
						}

						if (var15 != null && var16 instanceof EntityLivingBase) {
							var15.hitEntity((EntityLivingBase) var16, this);

							if (var15.stackSize <= 0) {
								destroyCurrentEquippedItem();
							}
						}

						if (targetEntity instanceof EntityLivingBase) {
							addStat(StatList.damageDealtStat, Math.round(var2 * 10.0F));

							if (var7 > 0) {
								targetEntity.setFire(var7 * 4);
							}
						}

						addExhaustion(0.3F);
					} else if (var6) {
						targetEntity.extinguish();
					}
				}
			}
		}
	}

	/**
	 * Called when the player performs a critical hit on the Entity. Args:
	 * entity that was hit critically
	 */
	public void onCriticalHit(final Entity p_71009_1_) {}

	public void onEnchantmentCritical(final Entity p_71047_1_) {}

	public void respawnPlayer() {}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		super.setDead();
		inventoryContainer.onContainerClosed(this);

		if (openContainer != null) {
			openContainer.onContainerClosed(this);
		}
	}

	/**
	 * Checks if this entity is inside of an opaque block
	 */
	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return !sleeping && super.isEntityInsideOpaqueBlock();
	}

	public boolean func_175144_cb() {
		return false;
	}

	/**
	 * Returns the GameProfile for this player
	 */
	public GameProfile getGameProfile() {
		return gameProfile;
	}

	public EntityPlayer.EnumStatus func_180469_a(final BlockPos p_180469_1_) {
		if (!worldObj.isRemote) {
			if (isPlayerSleeping() || !isEntityAlive()) {
				return EntityPlayer.EnumStatus.OTHER_PROBLEM;
			}

			if (!worldObj.provider.isSurfaceWorld()) {
				return EntityPlayer.EnumStatus.NOT_POSSIBLE_HERE;
			}

			if (worldObj.isDaytime()) {
				return EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW;
			}

			if (Math.abs(posX - p_180469_1_.getX()) > 3.0D || Math.abs(posY - p_180469_1_.getY()) > 2.0D
					|| Math.abs(posZ - p_180469_1_.getZ()) > 3.0D) {
				return EntityPlayer.EnumStatus.TOO_FAR_AWAY;
			}

			final double var2 = 8.0D;
			final double var4 = 5.0D;
			final List var6 = worldObj.getEntitiesWithinAABB(EntityMob.class,
					new AxisAlignedBB(p_180469_1_.getX() - var2, p_180469_1_.getY() - var4, p_180469_1_.getZ() - var2,
							p_180469_1_.getX() + var2, p_180469_1_.getY() + var4, p_180469_1_.getZ() + var2));

			if (!var6.isEmpty()) {
				return EntityPlayer.EnumStatus.NOT_SAFE;
			}
		}

		if (isRiding()) {
			mountEntity((Entity) null);
		}

		setSize(0.2F, 0.2F);

		if (worldObj.isBlockLoaded(p_180469_1_)) {
			final EnumFacing var7 = (EnumFacing) worldObj.getBlockState(p_180469_1_).getValue(BlockDirectional.AGE);
			float var3 = 0.5F;
			float var8 = 0.5F;

			switch (EntityPlayer.SwitchEnumFacing.field_179420_a[var7.ordinal()]) {
			case 1:
				var8 = 0.9F;
				break;

			case 2:
				var8 = 0.1F;
				break;

			case 3:
				var3 = 0.1F;
				break;

			case 4:
				var3 = 0.9F;
			}

			func_175139_a(var7);
			setPosition(p_180469_1_.getX() + var3, p_180469_1_.getY() + 0.6875F, p_180469_1_.getZ() + var8);
		} else {
			setPosition(p_180469_1_.getX() + 0.5F, p_180469_1_.getY() + 0.6875F, p_180469_1_.getZ() + 0.5F);
		}

		sleeping = true;
		sleepTimer = 0;
		playerLocation = p_180469_1_;
		motionX = motionZ = motionY = 0.0D;

		if (!worldObj.isRemote) {
			worldObj.updateAllPlayersSleepingFlag();
		}

		return EntityPlayer.EnumStatus.OK;
	}

	private void func_175139_a(final EnumFacing p_175139_1_) {
		field_71079_bU = 0.0F;
		field_71089_bV = 0.0F;

		switch (EntityPlayer.SwitchEnumFacing.field_179420_a[p_175139_1_.ordinal()]) {
		case 1:
			field_71089_bV = -1.8F;
			break;

		case 2:
			field_71089_bV = 1.8F;
			break;

		case 3:
			field_71079_bU = 1.8F;
			break;

		case 4:
			field_71079_bU = -1.8F;
		}
	}

	/**
	 * Wake up the player if they're sleeping.
	 */
	public void wakeUpPlayer(final boolean p_70999_1_, final boolean updateWorldFlag, final boolean setSpawn) {
		setSize(0.6F, 1.8F);
		final IBlockState var4 = worldObj.getBlockState(playerLocation);

		if (playerLocation != null && var4.getBlock() == Blocks.bed) {
			worldObj.setBlockState(playerLocation, var4.withProperty(BlockBed.OCCUPIED_PROP, false), 4);
			BlockPos var5 = BlockBed.getSafeExitLocation(worldObj, playerLocation, 0);

			if (var5 == null) {
				var5 = playerLocation.offsetUp();
			}

			setPosition(var5.getX() + 0.5F, var5.getY() + 0.1F, var5.getZ() + 0.5F);
		}

		sleeping = false;

		if (!worldObj.isRemote && updateWorldFlag) {
			worldObj.updateAllPlayersSleepingFlag();
		}

		sleepTimer = p_70999_1_ ? 0 : 100;

		if (setSpawn) {
			func_180473_a(playerLocation, false);
		}
	}

	private boolean func_175143_p() {
		return worldObj.getBlockState(playerLocation).getBlock() == Blocks.bed;
	}

	public static BlockPos func_180467_a(final World worldIn, final BlockPos p_180467_1_, final boolean p_180467_2_) {
		if (worldIn.getBlockState(p_180467_1_).getBlock() != Blocks.bed) {
			if (!p_180467_2_) {
				return null;
			} else {
				final Material var3 = worldIn.getBlockState(p_180467_1_).getBlock().getMaterial();
				final Material var4 = worldIn.getBlockState(p_180467_1_.offsetUp()).getBlock().getMaterial();
				final boolean var5 = !var3.isSolid() && !var3.isLiquid();
				final boolean var6 = !var4.isSolid() && !var4.isLiquid();
				return var5 && var6 ? p_180467_1_ : null;
			}
		} else {
			return BlockBed.getSafeExitLocation(worldIn, p_180467_1_, 0);
		}
	}

	/**
	 * Returns the orientation of the bed in degrees.
	 */
	public float getBedOrientationInDegrees() {
		if (playerLocation != null) {
			final EnumFacing var1 = (EnumFacing) worldObj.getBlockState(playerLocation).getValue(BlockDirectional.AGE);

			switch (EntityPlayer.SwitchEnumFacing.field_179420_a[var1.ordinal()]) {
			case 1:
				return 90.0F;

			case 2:
				return 270.0F;

			case 3:
				return 0.0F;

			case 4:
				return 180.0F;
			}
		}

		return 0.0F;
	}

	/**
	 * Returns whether player is sleeping or not
	 */
	@Override
	public boolean isPlayerSleeping() {
		return sleeping;
	}

	/**
	 * Returns whether or not the player is asleep and the screen has fully
	 * faded.
	 */
	public boolean isPlayerFullyAsleep() {
		return sleeping && sleepTimer >= 100;
	}

	public int getSleepTimer() {
		return sleepTimer;
	}

	public void addChatComponentMessage(final IChatComponent p_146105_1_) {}

	public BlockPos func_180470_cg() {
		return spawnChunk;
	}

	public boolean isSpawnForced() {
		return spawnForced;
	}

	public void func_180473_a(final BlockPos p_180473_1_, final boolean p_180473_2_) {
		if (p_180473_1_ != null) {
			spawnChunk = p_180473_1_;
			spawnForced = p_180473_2_;
		} else {
			spawnChunk = null;
			spawnForced = false;
		}
	}

	/**
	 * Will trigger the specified trigger.
	 */
	public void triggerAchievement(final StatBase p_71029_1_) {
		addStat(p_71029_1_, 1);
	}

	/**
	 * Adds a value to a statistic field.
	 */
	public void addStat(final StatBase p_71064_1_, final int p_71064_2_) {}

	public void func_175145_a(final StatBase p_175145_1_) {}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	public void jump() {
		if (this == Minecraft.thePlayer) {
			final EventJump event = new EventJump();
			EventManager.call(event);
			if (event.isCancelled()) {
				return;
			}
		}
		super.jump();
		triggerAchievement(StatList.jumpStat);
		if (isSprinting()) {
			addExhaustion(0.8f);
		} else {
			addExhaustion(0.2f);
		}
	}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	@Override
	public void moveEntityWithHeading(final float p_70612_1_, final float p_70612_2_) {
		final double var3 = posX;
		final double var5 = posY;
		final double var7 = posZ;

		if (capabilities.isFlying && ridingEntity == null) {
			final double var9 = motionY;
			final float var11 = jumpMovementFactor;
			jumpMovementFactor = capabilities.getFlySpeed() * (isSprinting() ? 2 : 1);
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			motionY = var9 * 0.6D;
			jumpMovementFactor = var11;
		} else {
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}

		addMovementStat(posX - var3, posY - var5, posZ - var7);
	}

	/**
	 * the movespeed used for the new AI system
	 */
	@Override
	public float getAIMoveSpeed() {
		return (float) getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
	}

	/**
	 * Adds a value to a movement statistic field - like run, walk, swin or
	 * climb.
	 */
	public void addMovementStat(final double p_71000_1_, final double p_71000_3_, final double p_71000_5_) {
		if (ridingEntity == null) {
			int var7;

			if (isInsideOfMaterial(Material.water)) {
				var7 = Math.round(MathHelper.sqrt_double(
						p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);

				if (var7 > 0) {
					addStat(StatList.distanceDoveStat, var7);
					addExhaustion(0.015F * var7 * 0.01F);
				}
			} else if (isInWater()) {
				var7 = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

				if (var7 > 0) {
					addStat(StatList.distanceSwumStat, var7);
					addExhaustion(0.015F * var7 * 0.01F);
				}
			} else if (isOnLadder()) {
				if (p_71000_3_ > 0.0D) {
					addStat(StatList.distanceClimbedStat, (int) Math.round(p_71000_3_ * 100.0D));
				}
			} else if (onGround) {
				var7 = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

				if (var7 > 0) {
					addStat(StatList.distanceWalkedStat, var7);

					if (isSprinting()) {
						addStat(StatList.distanceSprintedStat, var7);
						addExhaustion(0.099999994F * var7 * 0.01F);
					} else {
						if (isSneaking()) {
							addStat(StatList.distanceCrouchedStat, var7);
						}

						addExhaustion(0.01F * var7 * 0.01F);
					}
				}
			} else {
				var7 = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

				if (var7 > 25) {
					addStat(StatList.distanceFlownStat, var7);
				}
			}
		}
	}

	/**
	 * Adds a value to a mounted movement statistic field - by minecart, boat,
	 * or pig.
	 */
	private void addMountedMovementStat(final double p_71015_1_, final double p_71015_3_, final double p_71015_5_) {
		if (ridingEntity != null) {
			final int var7 = Math.round(
					MathHelper.sqrt_double(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_)
							* 100.0F);

			if (var7 > 0) {
				if (ridingEntity instanceof EntityMinecart) {
					addStat(StatList.distanceByMinecartStat, var7);

					if (startMinecartRidingCoordinate == null) {
						startMinecartRidingCoordinate = new BlockPos(this);
					} else if (startMinecartRidingCoordinate.distanceSq(MathHelper.floor_double(posX),
							MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) >= 1000000.0D) {
						triggerAchievement(AchievementList.onARail);
					}
				} else if (ridingEntity instanceof EntityBoat) {
					addStat(StatList.distanceByBoatStat, var7);
				} else if (ridingEntity instanceof EntityPig) {
					addStat(StatList.distanceByPigStat, var7);
				} else if (ridingEntity instanceof EntityHorse) {
					addStat(StatList.distanceByHorseStat, var7);
				}
			}
		}
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {
		if (!capabilities.allowFlying) {
			if (distance >= 2.0F) {
				addStat(StatList.distanceFallenStat, (int) Math.round(distance * 100.0D));
			}

			super.fall(distance, damageMultiplier);
		}
	}

	/**
	 * sets the players height back to normal after doing things like sleeping
	 * and dieing
	 */
	@Override
	protected void resetHeight() {
		if (!isSpectatorMode()) {
			super.resetHeight();
		}
	}

	@Override
	protected String func_146067_o(final int p_146067_1_) {
		return p_146067_1_ > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	@Override
	public void onKillEntity(final EntityLivingBase entityLivingIn) {
		if (entityLivingIn instanceof IMob) {
			triggerAchievement(AchievementList.killEnemy);
		}

		final EntityList.EntityEggInfo var2 = (EntityList.EntityEggInfo) EntityList.entityEggs
				.get(EntityList.getEntityID(entityLivingIn));

		if (var2 != null) {
			triggerAchievement(var2.field_151512_d);
		}
	}

	/**
	 * Sets the Entity inside a web block.
	 */
	@Override
	public void setInWeb() {
		if (!capabilities.isFlying) {
			super.setInWeb();
		}
	}

	@Override
	public ItemStack getCurrentArmor(final int p_82169_1_) {
		return inventory.armorItemInSlot(p_82169_1_);
	}

	/**
	 * Add experience points to player.
	 */
	public void addExperience(int p_71023_1_) {
		addScore(p_71023_1_);
		final int var2 = Integer.MAX_VALUE - experienceTotal;

		if (p_71023_1_ > var2) {
			p_71023_1_ = var2;
		}

		experience += (float) p_71023_1_ / (float) xpBarCap();

		for (experienceTotal += p_71023_1_; experience >= 1.0F; experience /= xpBarCap()) {
			experience = (experience - 1.0F) * xpBarCap();
			addExperienceLevel(1);
		}
	}

	public int func_175138_ci() {
		return field_175152_f;
	}

	public void func_71013_b(final int p_71013_1_) {
		experienceLevel -= p_71013_1_;

		if (experienceLevel < 0) {
			experienceLevel = 0;
			experience = 0.0F;
			experienceTotal = 0;
		}

		field_175152_f = rand.nextInt();
	}

	/**
	 * Add experience levels to this player.
	 */
	public void addExperienceLevel(final int p_82242_1_) {
		experienceLevel += p_82242_1_;

		if (experienceLevel < 0) {
			experienceLevel = 0;
			experience = 0.0F;
			experienceTotal = 0;
		}

		if (p_82242_1_ > 0 && experienceLevel % 5 == 0 && field_82249_h < ticksExisted - 100.0F) {
			final float var2 = experienceLevel > 30 ? 1.0F : experienceLevel / 30.0F;
			worldObj.playSoundAtEntity(this, "random.levelup", var2 * 0.75F, 1.0F);
			field_82249_h = ticksExisted;
		}
	}

	/**
	 * This method returns the cap amount of experience that the experience bar
	 * can hold. With each level, the experience cap on the player's experience
	 * bar is raised by 10.
	 */
	public int xpBarCap() {
		return experienceLevel >= 30 ? 112 + (experienceLevel - 30) * 9
				: experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2;
	}

	/**
	 * increases exhaustion level by supplied amount
	 */
	public void addExhaustion(final float p_71020_1_) {
		if (!capabilities.disableDamage) {
			if (!worldObj.isRemote) {
				foodStats.addExhaustion(p_71020_1_);
			}
		}
	}

	/**
	 * Returns the player's FoodStats object.
	 */
	public FoodStats getFoodStats() {
		return foodStats;
	}

	public boolean canEat(final boolean p_71043_1_) {
		return (p_71043_1_ || foodStats.needFood()) && !capabilities.disableDamage;
	}

	/**
	 * Checks if the player's health is not full and not zero.
	 */
	public boolean shouldHeal() {
		return getHealth() > 0.0F && getHealth() < getMaxHealth();
	}

	/**
	 * sets the itemInUse when the use item button is clicked. Args: itemstack,
	 * int maxItemUseDuration
	 */
	public void setItemInUse(final ItemStack p_71008_1_, final int p_71008_2_) {
		if (p_71008_1_ != itemInUse) {
			itemInUse = p_71008_1_;
			itemInUseCount = p_71008_2_;

			if (!worldObj.isRemote) {
				setEating(true);
			}
		}
	}

	public boolean func_175142_cm() {
		return capabilities.allowEdit;
	}

	public boolean func_175151_a(final BlockPos p_175151_1_, final EnumFacing p_175151_2_,
			final ItemStack p_175151_3_) {
		if (capabilities.allowEdit) {
			return true;
		} else if (p_175151_3_ == null) {
			return false;
		} else {
			final BlockPos var4 = p_175151_1_.offset(p_175151_2_.getOpposite());
			final Block var5 = worldObj.getBlockState(var4).getBlock();
			return p_175151_3_.canPlaceOn(var5) || p_175151_3_.canEditBlocks();
		}
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	@Override
	protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
		if (worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
			return 0;
		} else {
			final int var2 = experienceLevel * 7;
			return var2 > 100 ? 100 : var2;
		}
	}

	/**
	 * Only use is to identify if class is an instance of player for experience
	 * dropping
	 */
	@Override
	protected boolean isPlayer() {
		return true;
	}

	@Override
	public boolean getAlwaysRenderNameTagForRender() {
		return true;
	}

	/**
	 * Copies the values from the given player into this player if boolean par2
	 * is true. Always clones Ender Chest Inventory.
	 */
	public void clonePlayer(final EntityPlayer p_71049_1_, final boolean p_71049_2_) {
		if (p_71049_2_) {
			inventory.copyInventory(p_71049_1_.inventory);
			setHealth(p_71049_1_.getHealth());
			foodStats = p_71049_1_.foodStats;
			experienceLevel = p_71049_1_.experienceLevel;
			experienceTotal = p_71049_1_.experienceTotal;
			experience = p_71049_1_.experience;
			setScore(p_71049_1_.getScore());
			teleportDirection = p_71049_1_.teleportDirection;
		} else if (worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
			inventory.copyInventory(p_71049_1_.inventory);
			experienceLevel = p_71049_1_.experienceLevel;
			experienceTotal = p_71049_1_.experienceTotal;
			experience = p_71049_1_.experience;
			setScore(p_71049_1_.getScore());
		}

		theInventoryEnderChest = p_71049_1_.theInventoryEnderChest;
		getDataWatcher().updateObject(10, p_71049_1_.getDataWatcher().getWatchableObjectByte(10));
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return !capabilities.isFlying;
	}

	/**
	 * Sends the player's abilities to the server (if there is one).
	 */
	public void sendPlayerAbilities() {}

	/**
	 * Sets the player's game mode and sends it to them.
	 */
	public void setGameType(final WorldSettings.GameType gameType) {}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return gameProfile.getName();
	}

	/**
	 * Returns the InventoryEnderChest of this player.
	 */
	public InventoryEnderChest getInventoryEnderChest() {
		return theInventoryEnderChest;
	}

	/**
	 * 0: Tool in Hand; 1-4: Armor
	 */
	@Override
	public ItemStack getEquipmentInSlot(final int p_71124_1_) {
		return p_71124_1_ == 0 ? inventory.getCurrentItem() : inventory.armorInventory[p_71124_1_ - 1];
	}

	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	@Override
	public ItemStack getHeldItem() {
		return inventory.getCurrentItem();
	}

	/**
	 * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is
	 * armor. Params: Item, slot
	 */
	@Override
	public void setCurrentItemOrArmor(final int slotIn, final ItemStack itemStackIn) {
		inventory.armorInventory[slotIn] = itemStackIn;
	}

	/**
	 * Only used by renderer in EntityLivingBase subclasses. Determines if an
	 * entity is visible or not to a specfic player, if the entity is normally
	 * invisible. For EntityLivingBase subclasses, returning false when
	 * invisible will render the entity semitransparent.
	 */
	@Override
	public boolean isInvisibleToPlayer(final EntityPlayer playerIn) {
		if (!isInvisible()) {
			return false;
		} else if (playerIn.isSpectatorMode()) {
			return false;
		} else {
			final Team var2 = getTeam();
			return var2 == null || playerIn == null || playerIn.getTeam() != var2 || !var2.func_98297_h();
		}
	}

	public abstract boolean isSpectatorMode();

	/**
	 * returns the inventory of this entity (only used in EntityPlayerMP it
	 * seems)
	 */
	@Override
	public ItemStack[] getInventory() {
		return inventory.armorInventory;
	}

	@Override
	public boolean isPushedByWater() {
		return !capabilities.isFlying && !NoKnockBack.mod.isToggled();
	}

	public Scoreboard getWorldScoreboard() {
		return worldObj.getScoreboard();
	}

	@Override
	public Team getTeam() {
		return getWorldScoreboard().getPlayersTeam(getName());
	}

	@Override
	public IChatComponent getDisplayName() {
		final ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.formatPlayerName(getTeam(), getName()));
		var1.getChatStyle()
				.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + getName() + " "));
		var1.getChatStyle().setChatHoverEvent(func_174823_aP());
		var1.getChatStyle().setInsertion(getName());
		return var1;
	}

	@Override
	public float getEyeHeight() {
		float var1 = 1.62F;

		if (isPlayerSleeping()) {
			var1 = 0.2F;
		}

		if (isSneaking()) {
			var1 -= 0.08F;
		}

		return var1;
	}

	@Override
	public void setAbsorptionAmount(float p_110149_1_) {
		if (p_110149_1_ < 0.0F) {
			p_110149_1_ = 0.0F;
		}

		getDataWatcher().updateObject(17, p_110149_1_);
	}

	@Override
	public float getAbsorptionAmount() {
		return getDataWatcher().getWatchableObjectFloat(17);
	}

	/**
	 * Gets a players UUID given their GameProfie
	 */
	public static UUID getUUID(final GameProfile p_146094_0_) {
		UUID var1 = p_146094_0_.getId();

		if (var1 == null) {
			var1 = getUUIDFromPlayerName(p_146094_0_.getName());
		}

		return var1;
	}

	public static UUID getUUIDFromPlayerName(final String p_175147_0_) {
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_175147_0_).getBytes(Charsets.UTF_8));
	}

	public boolean func_175146_a(final LockCode p_175146_1_) {
		if (p_175146_1_.isEmpty()) {
			return true;
		} else {
			final ItemStack var2 = getCurrentEquippedItem();
			return var2 != null && var2.hasDisplayName() ? var2.getDisplayName().equals(p_175146_1_.getLock()) : false;
		}
	}

	public boolean func_175148_a(final EnumPlayerModelParts p_175148_1_) {
		return (getDataWatcher().getWatchableObjectByte(10) & p_175148_1_.func_179327_a()) == p_175148_1_
				.func_179327_a();
	}

	@Override
	public boolean sendCommandFeedback() {
		return MinecraftServer.getServer().worldServers[0].getGameRules()
				.getGameRuleBooleanValue("sendCommandFeedback");
	}

	@Override
	public boolean func_174820_d(final int p_174820_1_, final ItemStack p_174820_2_) {
		if (p_174820_1_ >= 0 && p_174820_1_ < inventory.mainInventory.length) {
			inventory.setInventorySlotContents(p_174820_1_, p_174820_2_);
			return true;
		} else {
			final int var3 = p_174820_1_ - 100;
			int var4;

			if (var3 >= 0 && var3 < inventory.armorInventory.length) {
				var4 = var3 + 1;

				if (p_174820_2_ != null && p_174820_2_.getItem() != null) {
					if (p_174820_2_.getItem() instanceof ItemArmor) {
						if (EntityLiving.getArmorPosition(p_174820_2_) != var4) {
							return false;
						}
					} else if (var4 != 4
							|| p_174820_2_.getItem() != Items.skull && !(p_174820_2_.getItem() instanceof ItemBlock)) {
						return false;
					}
				}

				inventory.setInventorySlotContents(var3 + inventory.mainInventory.length, p_174820_2_);
				return true;
			} else {
				var4 = p_174820_1_ - 200;

				if (var4 >= 0 && var4 < theInventoryEnderChest.getSizeInventory()) {
					theInventoryEnderChest.setInventorySlotContents(var4, p_174820_2_);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public boolean func_175140_cp() {
		return field_175153_bG;
	}

	public void func_175150_k(final boolean p_175150_1_) {
		field_175153_bG = p_175150_1_;
	}

	public static enum EnumChatVisibility {
		FULL("FULL", 0, 0, "options.chat.visibility.full"), SYSTEM("SYSTEM", 1, 1,
				"options.chat.visibility.system"), HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
		private static final EntityPlayer.EnumChatVisibility[] field_151432_d = new EntityPlayer.EnumChatVisibility[values().length];
		private final int chatVisibility;
		private final String resourceKey;

		private EnumChatVisibility(final String p_i45323_1_, final int p_i45323_2_, final int p_i45323_3_,
				final String p_i45323_4_) {
			chatVisibility = p_i45323_3_;
			resourceKey = p_i45323_4_;
		}

		public int getChatVisibility() {
			return chatVisibility;
		}

		public static EntityPlayer.EnumChatVisibility getEnumChatVisibility(final int p_151426_0_) {
			return field_151432_d[p_151426_0_ % field_151432_d.length];
		}

		public String getResourceKey() {
			return resourceKey;
		}

		static {
			final EntityPlayer.EnumChatVisibility[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final EntityPlayer.EnumChatVisibility var3 = var0[var2];
				field_151432_d[var3.chatVisibility] = var3;
			}
		}
	}

	public static enum EnumStatus {
		OK("OK", 0), NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1), NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW",
				2), TOO_FAR_AWAY("TOO_FAR_AWAY", 3), OTHER_PROBLEM("OTHER_PROBLEM", 4), NOT_SAFE("NOT_SAFE", 5);

		private EnumStatus(final String p_i1751_1_, final int p_i1751_2_) {}
	}

	static final class SwitchEnumFacing {

		static final int[] field_179420_a = new int[EnumFacing.values().length];

		static {
			try {
				field_179420_a[EnumFacing.SOUTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {}

			try {
				field_179420_a[EnumFacing.NORTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {}

			try {
				field_179420_a[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {}

			try {
				field_179420_a[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {}
		}
	}
}
