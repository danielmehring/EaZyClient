package net.minecraft.entity.passive;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

import com.google.common.base.Predicate;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant {

public static final int EaZy = 1187;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int randomTickDivider;
	private boolean isMating;
	private boolean isPlaying;
	Village villageObj;

	/** This villager's current customer. */
	private EntityPlayer buyingPlayer;

	/** Initialises the MerchantRecipeList.java */
	private MerchantRecipeList buyingList;
	private int timeUntilReset;

	/** addDefaultEquipmentAndRecipies is called if this is true */
	private boolean needsInitilization;
	private boolean field_175565_bs;
	private int wealth;

	/** Last player to trade with this villager, used for aggressivity. */
	private String lastBuyingPlayer;
	private int field_175563_bv;
	private int field_175562_bw;
	private boolean isLookingForHome;
	private boolean field_175564_by;
	private InventoryBasic field_175560_bz;
	private static final EntityVillager.ITradeList[][][][] field_175561_bA = new EntityVillager.ITradeList[][][][] {
			{ { { new EntityVillager.EmeraldForItems(Items.wheat,
					new EntityVillager.PriceInfo(18, 22)),
					new EntityVillager.EmeraldForItems(Items.potato, new EntityVillager.PriceInfo(15, 19)),
					new EntityVillager.EmeraldForItems(Items.carrot, new EntityVillager.PriceInfo(15, 19)),
					new EntityVillager.ListItemForEmeralds(Items.bread, new EntityVillager.PriceInfo(-4, -2)) },
					{
							new EntityVillager.EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin),
									new EntityVillager.PriceInfo(8, 13)),
							new EntityVillager.ListItemForEmeralds(Items.pumpkin_pie,
									new EntityVillager.PriceInfo(-3, -2)) },
					{ new EntityVillager.EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block),
							new EntityVillager.PriceInfo(7, 12)),
							new EntityVillager.ListItemForEmeralds(Items.apple,
									new EntityVillager.PriceInfo(-5, -7)) },
					{ new EntityVillager.ListItemForEmeralds(Items.cookie, new EntityVillager.PriceInfo(-6, -10)),
							new EntityVillager.ListItemForEmeralds(Items.cake, new EntityVillager.PriceInfo(1, 1)) } },
					{ { new EntityVillager.EmeraldForItems(Items.string, new EntityVillager.PriceInfo(15, 20)),
							new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)),
							new EntityVillager.ItemAndEmeraldToItem(Items.fish, new EntityVillager.PriceInfo(6, 6),
									Items.cooked_fish, new EntityVillager.PriceInfo(6, 6)) },
							{ new EntityVillager.ListEnchantedItemForEmeralds(Items.fishing_rod,
									new EntityVillager.PriceInfo(7, 8)) } },
					{ { new EntityVillager.EmeraldForItems(Item.getItemFromBlock(Blocks.wool),
							new EntityVillager.PriceInfo(16, 22)),
							new EntityVillager.ListItemForEmeralds(Items.shears,
									new EntityVillager.PriceInfo(3, 4)) },
							{ new EntityVillager.ListItemForEmeralds(
									new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0),
									new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14),
											new EntityVillager.PriceInfo(1, 2)),
									new EntityVillager.ListItemForEmeralds(
											new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15),
											new EntityVillager.PriceInfo(1, 2)) } },
					{ { new EntityVillager.EmeraldForItems(Items.string, new EntityVillager.PriceInfo(15, 20)),
							new EntityVillager.ListItemForEmeralds(Items.arrow,
									new EntityVillager.PriceInfo(-12, -8)) },
							{ new EntityVillager.ListItemForEmeralds(Items.bow, new EntityVillager.PriceInfo(2, 3)),
									new EntityVillager.ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel),
											new EntityVillager.PriceInfo(10, 10), Items.flint,
											new EntityVillager.PriceInfo(6, 10)) } } },
			{ { { new EntityVillager.EmeraldForItems(Items.paper, new EntityVillager.PriceInfo(24, 36)),
					new EntityVillager.ListEnchantedBookForEmeralds() },
					{ new EntityVillager.EmeraldForItems(Items.book, new EntityVillager.PriceInfo(8, 10)),
							new EntityVillager.ListItemForEmeralds(Items.compass, new EntityVillager.PriceInfo(10, 12)),
							new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf),
									new EntityVillager.PriceInfo(3, 4)) },
					{ new EntityVillager.EmeraldForItems(Items.written_book, new EntityVillager.PriceInfo(2, 2)),
							new EntityVillager.ListItemForEmeralds(Items.clock, new EntityVillager.PriceInfo(10, 12)),
							new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass),
									new EntityVillager.PriceInfo(-5, -3)) },
					{ new EntityVillager.ListEnchantedBookForEmeralds() },
					{ new EntityVillager.ListEnchantedBookForEmeralds() },
					{ new EntityVillager.ListItemForEmeralds(Items.name_tag,
							new EntityVillager.PriceInfo(20, 22)) } } },
			{ { { new EntityVillager.EmeraldForItems(Items.rotten_flesh, new EntityVillager.PriceInfo(36, 40)),
					new EntityVillager.EmeraldForItems(Items.gold_ingot, new EntityVillager.PriceInfo(8, 10)) },
					{ new EntityVillager.ListItemForEmeralds(Items.redstone, new EntityVillager.PriceInfo(-4, -1)),
							new EntityVillager.ListItemForEmeralds(
									new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()),
									new EntityVillager.PriceInfo(-2, -1)) },
					{ new EntityVillager.ListItemForEmeralds(Items.ender_eye, new EntityVillager.PriceInfo(7, 11)),
							new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone),
									new EntityVillager.PriceInfo(-3, -1)) },
					{ new EntityVillager.ListItemForEmeralds(Items.experience_bottle,
							new EntityVillager.PriceInfo(3, 11)) } } },
			{ { { new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)),
					new EntityVillager.ListItemForEmeralds(Items.iron_helmet, new EntityVillager.PriceInfo(4, 6)) },
					{ new EntityVillager.EmeraldForItems(Items.iron_ingot, new EntityVillager.PriceInfo(7, 9)),
							new EntityVillager.ListItemForEmeralds(Items.iron_chestplate,
									new EntityVillager.PriceInfo(10, 14)) },
					{ new EntityVillager.EmeraldForItems(Items.diamond, new EntityVillager.PriceInfo(3, 4)),
							new EntityVillager.ListEnchantedItemForEmeralds(Items.diamond_chestplate,
									new EntityVillager.PriceInfo(16, 19)) },
					{ new EntityVillager.ListItemForEmeralds(Items.chainmail_boots, new EntityVillager.PriceInfo(5, 7)),
							new EntityVillager.ListItemForEmeralds(Items.chainmail_leggings,
									new EntityVillager.PriceInfo(9, 11)),
							new EntityVillager.ListItemForEmeralds(Items.chainmail_helmet,
									new EntityVillager.PriceInfo(5, 7)),
							new EntityVillager.ListItemForEmeralds(Items.chainmail_chestplate,
									new EntityVillager.PriceInfo(11, 15)) } },
					{ { new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)),
							new EntityVillager.ListItemForEmeralds(Items.iron_axe,
									new EntityVillager.PriceInfo(6, 8)) },
							{ new EntityVillager.EmeraldForItems(Items.iron_ingot, new EntityVillager.PriceInfo(7, 9)),
									new EntityVillager.ListEnchantedItemForEmeralds(Items.iron_sword,
											new EntityVillager.PriceInfo(9, 10)) },
							{ new EntityVillager.EmeraldForItems(Items.diamond, new EntityVillager.PriceInfo(3, 4)),
									new EntityVillager.ListEnchantedItemForEmeralds(Items.diamond_sword,
											new EntityVillager.PriceInfo(12, 15)),
									new EntityVillager.ListEnchantedItemForEmeralds(Items.diamond_axe,
											new EntityVillager.PriceInfo(9, 12)) } },
					{ { new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)),
							new EntityVillager.ListEnchantedItemForEmeralds(Items.iron_shovel,
									new EntityVillager.PriceInfo(5, 7)) },
							{ new EntityVillager.EmeraldForItems(Items.iron_ingot, new EntityVillager.PriceInfo(7, 9)),
									new EntityVillager.ListEnchantedItemForEmeralds(Items.iron_pickaxe,
											new EntityVillager.PriceInfo(9, 11)) },
							{ new EntityVillager.EmeraldForItems(Items.diamond, new EntityVillager.PriceInfo(3, 4)),
									new EntityVillager.ListEnchantedItemForEmeralds(Items.diamond_pickaxe,
											new EntityVillager.PriceInfo(12, 15)) } } },
			{ { { new EntityVillager.EmeraldForItems(Items.porkchop, new EntityVillager.PriceInfo(14, 18)),
					new EntityVillager.EmeraldForItems(Items.chicken, new EntityVillager.PriceInfo(14, 18)) },
					{ new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)),
							new EntityVillager.ListItemForEmeralds(Items.cooked_porkchop,
									new EntityVillager.PriceInfo(-7, -5)),
							new EntityVillager.ListItemForEmeralds(Items.cooked_chicken,
									new EntityVillager.PriceInfo(-8, -6)) } },
					{ { new EntityVillager.EmeraldForItems(Items.leather, new EntityVillager.PriceInfo(9, 12)),
							new EntityVillager.ListItemForEmeralds(Items.leather_leggings,
									new EntityVillager.PriceInfo(2, 4)) },
							{ new EntityVillager.ListEnchantedItemForEmeralds(Items.leather_chestplate,
									new EntityVillager.PriceInfo(7, 12)) },
							{ new EntityVillager.ListItemForEmeralds(Items.saddle,
									new EntityVillager.PriceInfo(8, 10)) } } } };
	// private static final String __OBFID = "http://https://fuckuskid00001707";

	public EntityVillager(final World worldIn) {
		this(worldIn, 0);
	}

	public EntityVillager(final World worldIn, final int p_i1748_2_) {
		super(worldIn);
		field_175560_bz = new InventoryBasic("Items", false, 8);
		setProfession(p_i1748_2_);
		setSize(0.6F, 1.8F);
		((PathNavigateGround) getNavigator()).func_179688_b(true);
		((PathNavigateGround) getNavigator()).func_179690_a(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAvoidEntity(this, new Predicate() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002195";
			public boolean func_179530_a(final Entity p_179530_1_) {
				return p_179530_1_ instanceof EntityZombie;
			}

			@Override
			public boolean apply(final Object p_apply_1_) {
				return func_179530_a((Entity) p_apply_1_);
			}
		}, 8.0F, 0.6D, 0.6D));
		tasks.addTask(1, new EntityAITradePlayer(this));
		tasks.addTask(1, new EntityAILookAtTradePlayer(this));
		tasks.addTask(2, new EntityAIMoveIndoors(this));
		tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
		tasks.addTask(6, new EntityAIVillagerMate(this));
		tasks.addTask(7, new EntityAIFollowGolem(this));
		tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
		tasks.addTask(9, new EntityAIVillagerInteract(this));
		tasks.addTask(9, new EntityAIWander(this, 0.6D));
		tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
		setCanPickUpLoot(true);
	}

	private void func_175552_ct() {
		if (!field_175564_by) {
			field_175564_by = true;

			if (isChild()) {
				tasks.addTask(8, new EntityAIPlay(this, 0.32D));
			} else if (getProfession() == 0) {
				tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
			}
		}
	}

	@Override
	protected void func_175500_n() {
		if (getProfession() == 0) {
			tasks.addTask(8, new EntityAIHarvestFarmland(this, 0.6D));
		}

		super.func_175500_n();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
	}

	@Override
	protected void updateAITasks() {
		if (--randomTickDivider <= 0) {
			final BlockPos var1 = new BlockPos(this);
			worldObj.getVillageCollection().func_176060_a(var1);
			randomTickDivider = 70 + rand.nextInt(50);
			villageObj = worldObj.getVillageCollection().func_176056_a(var1, 32);

			if (villageObj == null) {
				detachHome();
			} else {
				final BlockPos var2 = villageObj.func_180608_a();
				func_175449_a(var2, (int) (villageObj.getVillageRadius() * 1.0F));

				if (isLookingForHome) {
					isLookingForHome = false;
					villageObj.setDefaultPlayerReputation(5);
				}
			}
		}

		if (!isTrading() && timeUntilReset > 0) {
			--timeUntilReset;

			if (timeUntilReset <= 0) {
				if (needsInitilization) {
					final Iterator var3 = buyingList.iterator();

					while (var3.hasNext()) {
						final MerchantRecipe var4 = (MerchantRecipe) var3.next();

						if (var4.isRecipeDisabled()) {
							var4.func_82783_a(rand.nextInt(6) + rand.nextInt(6) + 2);
						}
					}

					func_175554_cu();
					needsInitilization = false;

					if (villageObj != null && lastBuyingPlayer != null) {
						worldObj.setEntityState(this, (byte) 14);
						villageObj.setReputationForPlayer(lastBuyingPlayer, 1);
					}
				}

				addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
			}
		}

		super.updateAITasks();
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
		final boolean var3 = var2 != null && var2.getItem() == Items.spawn_egg;

		if (!var3 && isEntityAlive() && !isTrading() && !isChild()) {
			if (!worldObj.isRemote && (buyingList == null || buyingList.size() > 0)) {
				setCustomer(p_70085_1_);
				p_70085_1_.displayVillagerTradeGui(this);
			}

			p_70085_1_.triggerAchievement(StatList.timesTalkedToVillagerStat);
			return true;
		} else {
			return super.interact(p_70085_1_);
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, 0);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Profession", getProfession());
		tagCompound.setInteger("Riches", wealth);
		tagCompound.setInteger("Career", field_175563_bv);
		tagCompound.setInteger("CareerLevel", field_175562_bw);
		tagCompound.setBoolean("Willing", field_175565_bs);

		if (buyingList != null) {
			tagCompound.setTag("Offers", buyingList.getRecipiesAsTags());
		}

		final NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < field_175560_bz.getSizeInventory(); ++var3) {
			final ItemStack var4 = field_175560_bz.getStackInSlot(var3);

			if (var4 != null) {
				var2.appendTag(var4.writeToNBT(new NBTTagCompound()));
			}
		}

		tagCompound.setTag("Inventory", var2);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		setProfession(tagCompund.getInteger("Profession"));
		wealth = tagCompund.getInteger("Riches");
		field_175563_bv = tagCompund.getInteger("Career");
		field_175562_bw = tagCompund.getInteger("CareerLevel");
		field_175565_bs = tagCompund.getBoolean("Willing");

		if (tagCompund.hasKey("Offers", 10)) {
			final NBTTagCompound var2 = tagCompund.getCompoundTag("Offers");
			buyingList = new MerchantRecipeList(var2);
		}

		final NBTTagList var5 = tagCompund.getTagList("Inventory", 10);

		for (int var3 = 0; var3 < var5.tagCount(); ++var3) {
			final ItemStack var4 = ItemStack.loadItemStackFromNBT(var5.getCompoundTagAt(var3));

			if (var4 != null) {
				field_175560_bz.func_174894_a(var4);
			}
		}

		setCanPickUpLoot(true);
		func_175552_ct();
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return false;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.villager.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.villager.death";
	}

	public void setProfession(final int p_70938_1_) {
		dataWatcher.updateObject(16, p_70938_1_);
	}

	public int getProfession() {
		return Math.max(dataWatcher.getWatchableObjectInt(16) % 5, 0);
	}

	public boolean isMating() {
		return isMating;
	}

	public void setMating(final boolean p_70947_1_) {
		isMating = p_70947_1_;
	}

	public void setPlaying(final boolean p_70939_1_) {
		isPlaying = p_70939_1_;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	@Override
	public void setRevengeTarget(final EntityLivingBase p_70604_1_) {
		super.setRevengeTarget(p_70604_1_);

		if (villageObj != null && p_70604_1_ != null) {
			villageObj.addOrRenewAgressor(p_70604_1_);

			if (p_70604_1_ instanceof EntityPlayer) {
				byte var2 = -1;

				if (isChild()) {
					var2 = -3;
				}

				villageObj.setReputationForPlayer(p_70604_1_.getName(), var2);

				if (isEntityAlive()) {
					worldObj.setEntityState(this, (byte) 13);
				}
			}
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		if (villageObj != null) {
			final Entity var2 = cause.getEntity();

			if (var2 != null) {
				if (var2 instanceof EntityPlayer) {
					villageObj.setReputationForPlayer(var2.getName(), -2);
				} else if (var2 instanceof IMob) {
					villageObj.endMatingSeason();
				}
			} else {
				final EntityPlayer var3 = worldObj.getClosestPlayerToEntity(this, 16.0D);

				if (var3 != null) {
					villageObj.endMatingSeason();
				}
			}
		}

		super.onDeath(cause);
	}

	@Override
	public void setCustomer(final EntityPlayer p_70932_1_) {
		buyingPlayer = p_70932_1_;
	}

	@Override
	public EntityPlayer getCustomer() {
		return buyingPlayer;
	}

	public boolean isTrading() {
		return buyingPlayer != null;
	}

	public boolean func_175550_n(final boolean p_175550_1_) {
		if (!field_175565_bs && p_175550_1_ && func_175553_cp()) {
			boolean var2 = false;

			for (int var3 = 0; var3 < field_175560_bz.getSizeInventory(); ++var3) {
				final ItemStack var4 = field_175560_bz.getStackInSlot(var3);

				if (var4 != null) {
					if (var4.getItem() == Items.bread && var4.stackSize >= 3) {
						var2 = true;
						field_175560_bz.decrStackSize(var3, 3);
					} else if ((var4.getItem() == Items.potato || var4.getItem() == Items.carrot)
							&& var4.stackSize >= 12) {
						var2 = true;
						field_175560_bz.decrStackSize(var3, 12);
					}
				}

				if (var2) {
					worldObj.setEntityState(this, (byte) 18);
					field_175565_bs = true;
					break;
				}
			}
		}

		return field_175565_bs;
	}

	public void func_175549_o(final boolean p_175549_1_) {
		field_175565_bs = p_175549_1_;
	}

	@Override
	public void useRecipe(final MerchantRecipe p_70933_1_) {
		p_70933_1_.incrementToolUses();
		livingSoundTime = -getTalkInterval();
		playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
		int var2 = 3 + rand.nextInt(4);

		if (p_70933_1_.func_180321_e() == 1 || rand.nextInt(5) == 0) {
			timeUntilReset = 40;
			needsInitilization = true;
			field_175565_bs = true;

			if (buyingPlayer != null) {
				lastBuyingPlayer = buyingPlayer.getName();
			} else {
				lastBuyingPlayer = null;
			}

			var2 += 5;
		}

		if (p_70933_1_.getItemToBuy().getItem() == Items.emerald) {
			wealth += p_70933_1_.getItemToBuy().stackSize;
		}

		if (p_70933_1_.func_180322_j()) {
			worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY + 0.5D, posZ, var2));
		}
	}

	/**
	 * Notifies the merchant of a possible merchantrecipe being fulfilled or
	 * not. Usually, this is just a sound byte being played depending if the
	 * suggested itemstack is not null.
	 */
	@Override
	public void verifySellingItem(final ItemStack p_110297_1_) {
		if (!worldObj.isRemote && livingSoundTime > -getTalkInterval() + 20) {
			livingSoundTime = -getTalkInterval();

			if (p_110297_1_ != null) {
				playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
			} else {
				playSound("mob.villager.no", getSoundVolume(), getSoundPitch());
			}
		}
	}

	@Override
	public MerchantRecipeList getRecipes(final EntityPlayer p_70934_1_) {
		if (buyingList == null) {
			func_175554_cu();
		}

		return buyingList;
	}

	private void func_175554_cu() {
		final EntityVillager.ITradeList[][][] var1 = field_175561_bA[getProfession()];

		if (field_175563_bv != 0 && field_175562_bw != 0) {
			++field_175562_bw;
		} else {
			field_175563_bv = rand.nextInt(var1.length) + 1;
			field_175562_bw = 1;
		}

		if (buyingList == null) {
			buyingList = new MerchantRecipeList();
		}

		final int var2 = field_175563_bv - 1;
		final int var3 = field_175562_bw - 1;
		final EntityVillager.ITradeList[][] var4 = var1[var2];

		if (var3 < var4.length) {
			final EntityVillager.ITradeList[] var5 = var4[var3];
			final EntityVillager.ITradeList[] var6 = var5;
			final int var7 = var5.length;

			for (int var8 = 0; var8 < var7; ++var8) {
				final EntityVillager.ITradeList var9 = var6[var8];
				var9.func_179401_a(buyingList, rand);
			}
		}
	}

	@Override
	public void setRecipes(final MerchantRecipeList p_70930_1_) {}

	@Override
	public IChatComponent getDisplayName() {
		final String var1 = getCustomNameTag();

		if (var1 != null && var1.length() > 0) {
			return new ChatComponentText(var1);
		} else {
			if (buyingList == null) {
				func_175554_cu();
			}

			String var2 = null;

			switch (getProfession()) {
				case 0:
					if (field_175563_bv == 1) {
						var2 = "farmer";
					} else if (field_175563_bv == 2) {
						var2 = "fisherman";
					} else if (field_175563_bv == 3) {
						var2 = "shepherd";
					} else if (field_175563_bv == 4) {
						var2 = "fletcher";
					}

					break;

				case 1:
					var2 = "librarian";
					break;

				case 2:
					var2 = "cleric";
					break;

				case 3:
					if (field_175563_bv == 1) {
						var2 = "armor";
					} else if (field_175563_bv == 2) {
						var2 = "weapon";
					} else if (field_175563_bv == 3) {
						var2 = "tool";
					}

					break;

				case 4:
					if (field_175563_bv == 1) {
						var2 = "butcher";
					} else if (field_175563_bv == 2) {
						var2 = "leather";
					}
			}

			if (var2 != null) {
				final ChatComponentTranslation var3 = new ChatComponentTranslation("entity.Villager." + var2,
						new Object[0]);
				var3.getChatStyle().setChatHoverEvent(func_174823_aP());
				var3.getChatStyle().setInsertion(getUniqueID().toString());
				return var3;
			} else {
				return super.getDisplayName();
			}
		}
	}

	@Override
	public float getEyeHeight() {
		float var1 = 1.62F;

		if (isChild()) {
			var1 = (float) (var1 - 0.81D);
		}

		return var1;
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 12) {
			func_180489_a(EnumParticleTypes.HEART);
		} else if (p_70103_1_ == 13) {
			func_180489_a(EnumParticleTypes.VILLAGER_ANGRY);
		} else if (p_70103_1_ == 14) {
			func_180489_a(EnumParticleTypes.VILLAGER_HAPPY);
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	private void func_180489_a(final EnumParticleTypes p_180489_1_) {
		for (int var2 = 0; var2 < 5; ++var2) {
			final double var3 = rand.nextGaussian() * 0.02D;
			final double var5 = rand.nextGaussian() * 0.02D;
			final double var7 = rand.nextGaussian() * 0.02D;
			worldObj.spawnParticle(p_180489_1_, posX + rand.nextFloat() * width * 2.0F - width,
					posY + 1.0D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var3, var5,
					var7, new int[0]);
		}
	}

	@Override
	public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
		p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
		setProfession(worldObj.rand.nextInt(5));
		func_175552_ct();
		return p_180482_2_;
	}

	public void setLookingForHome() {
		isLookingForHome = true;
	}

	public EntityVillager func_180488_b(final EntityAgeable p_180488_1_) {
		final EntityVillager var2 = new EntityVillager(worldObj);
		var2.func_180482_a(worldObj.getDifficultyForLocation(new BlockPos(var2)), (IEntityLivingData) null);
		return var2;
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	@Override
	public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
		if (!worldObj.isRemote) {
			final EntityWitch var2 = new EntityWitch(worldObj);
			var2.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			var2.func_180482_a(worldObj.getDifficultyForLocation(new BlockPos(var2)), (IEntityLivingData) null);
			worldObj.spawnEntityInWorld(var2);
			setDead();
		}
	}

	public InventoryBasic func_175551_co() {
		return field_175560_bz;
	}

	@Override
	protected void func_175445_a(final EntityItem p_175445_1_) {
		final ItemStack var2 = p_175445_1_.getEntityItem();
		final Item var3 = var2.getItem();

		if (func_175558_a(var3)) {
			final ItemStack var4 = field_175560_bz.func_174894_a(var2);

			if (var4 == null) {
				p_175445_1_.setDead();
			} else {
				var2.stackSize = var4.stackSize;
			}
		}
	}

	private boolean func_175558_a(final Item p_175558_1_) {
		return p_175558_1_ == Items.bread || p_175558_1_ == Items.potato || p_175558_1_ == Items.carrot
				|| p_175558_1_ == Items.wheat || p_175558_1_ == Items.wheat_seeds;
	}

	public boolean func_175553_cp() {
		return func_175559_s(1);
	}

	public boolean func_175555_cq() {
		return func_175559_s(2);
	}

	public boolean func_175557_cr() {
		final boolean var1 = getProfession() == 0;
		return var1 ? !func_175559_s(5) : !func_175559_s(1);
	}

	private boolean func_175559_s(final int p_175559_1_) {
		final boolean var2 = getProfession() == 0;

		for (int var3 = 0; var3 < field_175560_bz.getSizeInventory(); ++var3) {
			final ItemStack var4 = field_175560_bz.getStackInSlot(var3);

			if (var4 != null) {
				if (var4.getItem() == Items.bread && var4.stackSize >= 3 * p_175559_1_
						|| var4.getItem() == Items.potato && var4.stackSize >= 12 * p_175559_1_
						|| var4.getItem() == Items.carrot && var4.stackSize >= 12 * p_175559_1_) {
					return true;
				}

				if (var2 && var4.getItem() == Items.wheat && var4.stackSize >= 9 * p_175559_1_) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean func_175556_cs() {
		for (int var1 = 0; var1 < field_175560_bz.getSizeInventory(); ++var1) {
			final ItemStack var2 = field_175560_bz.getStackInSlot(var1);

			if (var2 != null && (var2.getItem() == Items.wheat_seeds || var2.getItem() == Items.potato
					|| var2.getItem() == Items.carrot)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean func_174820_d(final int p_174820_1_, final ItemStack p_174820_2_) {
		if (super.func_174820_d(p_174820_1_, p_174820_2_)) {
			return true;
		} else {
			final int var3 = p_174820_1_ - 300;

			if (var3 >= 0 && var3 < field_175560_bz.getSizeInventory()) {
				field_175560_bz.setInventorySlotContents(var3, p_174820_2_);
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public EntityAgeable createChild(final EntityAgeable p_90011_1_) {
		return func_180488_b(p_90011_1_);
	}

	static class EmeraldForItems implements EntityVillager.ITradeList {
		public Item field_179405_a;
		public EntityVillager.PriceInfo field_179404_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002194";

		public EmeraldForItems(final Item p_i45815_1_, final EntityVillager.PriceInfo p_i45815_2_) {
			field_179405_a = p_i45815_1_;
			field_179404_b = p_i45815_2_;
		}

		@Override
		public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
			int var3 = 1;

			if (field_179404_b != null) {
				var3 = field_179404_b.func_179412_a(p_179401_2_);
			}

			p_179401_1_.add(new MerchantRecipe(new ItemStack(field_179405_a, var3, 0), Items.emerald));
		}
	}

	interface ITradeList {
		void func_179401_a(MerchantRecipeList var1, Random var2);
	}

	static class ItemAndEmeraldToItem implements EntityVillager.ITradeList {
		public ItemStack field_179411_a;
		public EntityVillager.PriceInfo field_179409_b;
		public ItemStack field_179410_c;
		public EntityVillager.PriceInfo field_179408_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002191";

		public ItemAndEmeraldToItem(final Item p_i45813_1_, final EntityVillager.PriceInfo p_i45813_2_,
				final Item p_i45813_3_, final EntityVillager.PriceInfo p_i45813_4_) {
			field_179411_a = new ItemStack(p_i45813_1_);
			field_179409_b = p_i45813_2_;
			field_179410_c = new ItemStack(p_i45813_3_);
			field_179408_d = p_i45813_4_;
		}

		@Override
		public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
			int var3 = 1;

			if (field_179409_b != null) {
				var3 = field_179409_b.func_179412_a(p_179401_2_);
			}

			int var4 = 1;

			if (field_179408_d != null) {
				var4 = field_179408_d.func_179412_a(p_179401_2_);
			}

			p_179401_1_
					.add(new MerchantRecipe(new ItemStack(field_179411_a.getItem(), var3, field_179411_a.getMetadata()),
							new ItemStack(Items.emerald),
							new ItemStack(field_179410_c.getItem(), var4, field_179410_c.getMetadata())));
		}
	}

	static class ListEnchantedBookForEmeralds implements EntityVillager.ITradeList {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002193";

		@Override
		public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
			final Enchantment var3 = Enchantment.enchantmentsList[p_179401_2_
					.nextInt(Enchantment.enchantmentsList.length)];
			final int var4 = MathHelper.getRandomIntegerInRange(p_179401_2_, var3.getMinLevel(), var3.getMaxLevel());
			final ItemStack var5 = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var3, var4));
			int var6 = 2 + p_179401_2_.nextInt(5 + var4 * 10) + 3 * var4;

			if (var6 > 64) {
				var6 = 64;
			}

			p_179401_1_.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, var6), var5));
		}
	}

	static class ListEnchantedItemForEmeralds implements EntityVillager.ITradeList {
		public ItemStack field_179407_a;
		public EntityVillager.PriceInfo field_179406_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002192";

		public ListEnchantedItemForEmeralds(final Item p_i45814_1_, final EntityVillager.PriceInfo p_i45814_2_) {
			field_179407_a = new ItemStack(p_i45814_1_);
			field_179406_b = p_i45814_2_;
		}

		@Override
		public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
			int var3 = 1;

			if (field_179406_b != null) {
				var3 = field_179406_b.func_179412_a(p_179401_2_);
			}

			final ItemStack var4 = new ItemStack(Items.emerald, var3, 0);
			ItemStack var5 = new ItemStack(field_179407_a.getItem(), 1, field_179407_a.getMetadata());
			var5 = EnchantmentHelper.addRandomEnchantment(p_179401_2_, var5, 5 + p_179401_2_.nextInt(15));
			p_179401_1_.add(new MerchantRecipe(var4, var5));
		}
	}

	static class ListItemForEmeralds implements EntityVillager.ITradeList {
		public ItemStack field_179403_a;
		public EntityVillager.PriceInfo field_179402_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002190";

		public ListItemForEmeralds(final Item p_i45811_1_, final EntityVillager.PriceInfo p_i45811_2_) {
			field_179403_a = new ItemStack(p_i45811_1_);
			field_179402_b = p_i45811_2_;
		}

		public ListItemForEmeralds(final ItemStack p_i45812_1_, final EntityVillager.PriceInfo p_i45812_2_) {
			field_179403_a = p_i45812_1_;
			field_179402_b = p_i45812_2_;
		}

		@Override
		public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
			int var3 = 1;

			if (field_179402_b != null) {
				var3 = field_179402_b.func_179412_a(p_179401_2_);
			}

			ItemStack var4;
			ItemStack var5;

			if (var3 < 0) {
				var4 = new ItemStack(Items.emerald, 1, 0);
				var5 = new ItemStack(field_179403_a.getItem(), -var3, field_179403_a.getMetadata());
			} else {
				var4 = new ItemStack(Items.emerald, var3, 0);
				var5 = new ItemStack(field_179403_a.getItem(), 1, field_179403_a.getMetadata());
			}

			p_179401_1_.add(new MerchantRecipe(var4, var5));
		}
	}

	static class PriceInfo extends Tuple {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002189";

		public PriceInfo(final int p_i45810_1_, final int p_i45810_2_) {
			super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
		}

		public int func_179412_a(final Random p_179412_1_) {
			return ((Integer) getFirst()) >= ((Integer) getSecond())
					? ((Integer) getFirst())
					: ((Integer) getFirst()) + p_179412_1_
							.nextInt(((Integer) getSecond()) - ((Integer) getFirst()) + 1);
		}
	}
}
