package net.minecraft.client.multiplayer;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.FastBreak;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class PlayerControllerMP {

    public static final int EaZy = 621;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    /**
     * The Minecraft instance.
     */
    private final Minecraft mc;
    private final NetHandlerPlayClient netClientHandler;
    private BlockPos field_178895_c = new BlockPos(-1, -1, -1);

    /**
     * The Item currently being used to destroy a block
     */
    private ItemStack currentItemHittingBlock;

    /**
     * Current block damage (MP)
     */
    private float curBlockDamageMP;

    /**
     * Tick counter, when it hits 4 it resets back to 0 and plays the step sound
     */
    private float stepSoundTickCounter;

    /**
     * Delays the first damage on the block after the first click on the block
     */
    private int blockHitDelay;

    /**
     * Tells if the player is hitting a block
     */
    private boolean isHittingBlock;

    /**
     * Current game type for the player
     */
    private WorldSettings.GameType currentGameType;

    /**
     * Index of the current item held by the player in the inventory hotbar
     */
    public int currentPlayerItem;

    public PlayerControllerMP(final Minecraft mcIn, final NetHandlerPlayClient p_i45062_2_) {
        currentGameType = WorldSettings.GameType.SURVIVAL;
        mc = mcIn;
        netClientHandler = p_i45062_2_;
    }

    public static void func_178891_a(final Minecraft mcIn, final PlayerControllerMP p_178891_1_,
            final BlockPos p_178891_2_, final EnumFacing p_178891_3_) {
        if (!Minecraft.theWorld.func_175719_a(Minecraft.thePlayer, p_178891_2_, p_178891_3_)) {
            p_178891_1_.func_178888_a(p_178891_2_, p_178891_3_);
        }
    }

    /**
     * Sets player capabilities depending on current gametype. params: player
     */
    public void setPlayerCapabilities(final EntityPlayer p_78748_1_) {
        currentGameType.configurePlayerCapabilities(p_78748_1_.capabilities);
    }

    /**
     * If modified to return true, the player spins around slowly around (0,
     * 68.5, 0). The GUI is disabled, the view is set to first person, and both
     * chat and menu are disabled. Unless the server is modified to ignore
     * illegal stances, attempting to enter a world at all will result in an
     * immediate kick due to an illegal stance. Appears to be left-over debug,
     * or demo code.
     */
    public boolean enableEverythingIsScrewedUpMode() {
        return currentGameType == WorldSettings.GameType.SPECTATOR;
    }

    /**
     * Sets the game type for the player.
     */
    public void setGameType(final WorldSettings.GameType p_78746_1_) {
        currentGameType = p_78746_1_;
        currentGameType.configurePlayerCapabilities(Minecraft.thePlayer.capabilities);
    }

    /**
     * Flips the player around.
     */
    public void flipPlayer(final EntityPlayer playerIn) {
        playerIn.rotationYaw = -180.0F;
    }

    public boolean shouldDrawHUD() {
        return currentGameType.isSurvivalOrAdventure();
    }

    public boolean func_178888_a(final BlockPos p_178888_1_, final EnumFacing p_178888_2_) {
        if (currentGameType.isAdventure()) {
            if (currentGameType == WorldSettings.GameType.SPECTATOR) {
                return false;
            }

            if (!Minecraft.thePlayer.func_175142_cm()) {
                final Block var3 = Minecraft.theWorld.getBlockState(p_178888_1_).getBlock();
                final ItemStack var4 = Minecraft.thePlayer.getCurrentEquippedItem();

                if (var4 == null) {
                    return false;
                }

                if (!var4.canDestroy(var3)) {
                    return false;
                }
            }
        }

        if (currentGameType.isCreative() && Minecraft.thePlayer.getHeldItem() != null
                && Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            return false;
        } else {
            final WorldClient var8 = Minecraft.theWorld;
            final IBlockState var9 = var8.getBlockState(p_178888_1_);
            final Block var5 = var9.getBlock();

            if (var5.getMaterial() == Material.air) {
                return false;
            } else {
                var8.playAuxSFX(2001, p_178888_1_, Block.getStateId(var9));
                final boolean var6 = var8.setBlockToAir(p_178888_1_);

                if (var6) {
                    var5.onBlockDestroyedByPlayer(var8, p_178888_1_, var9);
                }

                field_178895_c = new BlockPos(field_178895_c.getX(), -1, field_178895_c.getZ());

                if (!currentGameType.isCreative()) {
                    final ItemStack var7 = Minecraft.thePlayer.getCurrentEquippedItem();

                    if (var7 != null) {
                        var7.onBlockDestroyed(var8, var5, p_178888_1_, Minecraft.thePlayer);

                        if (var7.stackSize == 0) {
                            Minecraft.thePlayer.destroyCurrentEquippedItem();
                        }
                    }
                }

                return var6;
            }
        }
    }

    public boolean breakBlock(final BlockPos blockPos, final EnumFacing facing) {
        Block var3;

        if (currentGameType.isAdventure()) {
            if (currentGameType == WorldSettings.GameType.SPECTATOR) {
                return false;
            }

            if (!Minecraft.thePlayer.func_175142_cm()) {
                var3 = Minecraft.theWorld.getBlockState(blockPos).getBlock();
                final ItemStack var4 = Minecraft.thePlayer.getCurrentEquippedItem();

                if (var4 == null) {
                    return false;
                }

                if (!var4.canDestroy(var3)) {
                    return false;
                }
            }
        }
        
        if (!Minecraft.theWorld.getWorldBorder().contains(blockPos)) {
            return false;
        } else {
            if (currentGameType.isCreative()) {
                netClientHandler.addToSendQueue(new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, facing));
                func_178891_a(mc, this, blockPos, facing);
                blockHitDelay = 5;
            } else if (!isHittingBlock || !func_178893_a(blockPos)) {
                if (isHittingBlock) {
                    netClientHandler.addToSendQueue(new C07PacketPlayerDigging(
                            C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, field_178895_c, facing));
                }
                netClientHandler.addToSendQueue(new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, facing));
                var3 = Minecraft.theWorld.getBlockState(blockPos).getBlock();
                final boolean var5 = var3.getMaterial() != Material.air;

                if (var5 && curBlockDamageMP == 0.0F) {
                    var3.onBlockClicked(Minecraft.theWorld, blockPos, Minecraft.thePlayer);
                }

                if (var5 && var3.getPlayerRelativeBlockHardness(Minecraft.thePlayer, Minecraft.thePlayer.worldObj,
                        blockPos) >= 1.0F) {
                    func_178888_a(blockPos, facing);
                } else {
                    isHittingBlock = true;
                    field_178895_c = blockPos;
                    currentItemHittingBlock = Minecraft.thePlayer.getHeldItem();
                    curBlockDamageMP = 0.0F;
                    stepSoundTickCounter = 0.0F;
                    Minecraft.theWorld.sendBlockBreakProgress(Minecraft.thePlayer.getEntityId(), field_178895_c,
                            (int) (curBlockDamageMP * 10.0F) - 1);
                }
            }

            return true;
        }
    }

    /**
     * Resets current block damage and isHittingBlock
     */
    public void resetBlockRemoving() {
        if (isHittingBlock) {
            netClientHandler.addToSendQueue(new C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, field_178895_c, EnumFacing.DOWN));
            isHittingBlock = false;
            curBlockDamageMP = 0.0F;
            Minecraft.theWorld.sendBlockBreakProgress(Minecraft.thePlayer.getEntityId(), field_178895_c, -1);
        }
    }

    public boolean onPlayerDamageBlock(final BlockPos p_180512_1_, final EnumFacing p_180512_2_) {
        final boolean fastBreak = FastBreak.mod.isToggled();
        syncCurrentPlayItem();

        if (blockHitDelay > 0) {
            --blockHitDelay;
            return true;
        } else if (currentGameType.isCreative() && Minecraft.theWorld.getWorldBorder().contains(p_180512_1_)) {
            blockHitDelay = fastBreak ? 0 : 5;
            netClientHandler.addToSendQueue(new C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p_180512_1_, p_180512_2_));
            func_178891_a(mc, this, p_180512_1_, p_180512_2_);
            return true;
        } else if (func_178893_a(p_180512_1_)) {
            final Block var3 = Minecraft.theWorld.getBlockState(p_180512_1_).getBlock();

            if (var3.getMaterial() == Material.air) {
                isHittingBlock = false;
                return false;
            } else {
                curBlockDamageMP += var3.getPlayerRelativeBlockHardness(Minecraft.thePlayer,
                        Minecraft.thePlayer.worldObj, p_180512_1_);

                if (stepSoundTickCounter % 4.0F == 0.0F) {
                    mc.getSoundHandler()
                            .playSound(new PositionedSoundRecord(new ResourceLocation(var3.stepSound.getStepSound()),
                                    (var3.stepSound.getVolume() + 1.0F) / 8.0F, var3.stepSound.getFrequency() * 0.5F,
                                    p_180512_1_.getX() + 0.5F, p_180512_1_.getY() + 0.5F, p_180512_1_.getZ() + 0.5F));
                }

                ++stepSoundTickCounter;

                if (curBlockDamageMP >= (fastBreak
                        ? Client.setmgr.getSettingByName(FastBreak.mod, "Speed").getValFloat()
                        : 1.0f)) {
                    isHittingBlock = false;
                    netClientHandler.addToSendQueue(new C07PacketPlayerDigging(
                            C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p_180512_1_, p_180512_2_));
                    func_178888_a(p_180512_1_, p_180512_2_);
                    curBlockDamageMP = 0.0F;
                    stepSoundTickCounter = 0.0F;
                    blockHitDelay = fastBreak ? 0 : 5;
                }

                Minecraft.theWorld.sendBlockBreakProgress(Minecraft.thePlayer.getEntityId(), field_178895_c,
                        (int) (curBlockDamageMP * 10.0F) - 1);
                return true;
            }
        } else {
            return breakBlock(p_180512_1_, p_180512_2_);
        }
    }

    /**
     * player reach distance = 4F
     */
    public float getBlockReachDistance() {
        return currentGameType.isCreative() ? 5.0F : 4.5F;
    }

    public void updateController() {
        syncCurrentPlayItem();

        if (netClientHandler.getNetworkManager().isChannelOpen()) {
            netClientHandler.getNetworkManager().processReceivedPackets();
        } else {
            netClientHandler.getNetworkManager().checkDisconnected();
        }
    }

    private boolean func_178893_a(final BlockPos p_178893_1_) {
        final ItemStack var2 = Minecraft.thePlayer.getHeldItem();
        boolean var3 = currentItemHittingBlock == null && var2 == null;

        if (currentItemHittingBlock != null && var2 != null) {
            var3 = var2.getItem() == currentItemHittingBlock.getItem()
                    && ItemStack.areItemStackTagsEqual(var2, currentItemHittingBlock)
                    && (var2.isItemStackDamageable() || var2.getMetadata() == currentItemHittingBlock.getMetadata());
        }

        return p_178893_1_.equals(field_178895_c) && var3;
    }

    /**
     * Syncs the current player item with the server
     */
    public void syncCurrentPlayItem() {
        try {
            final int var1 = Minecraft.thePlayer.inventory.currentItem;

            if (var1 != currentPlayerItem) {
                currentPlayerItem = var1;
                netClientHandler.addToSendQueue(new C09PacketHeldItemChange(currentPlayerItem));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public boolean placeBlock(final EntityPlayerSP p_178890_1_, final WorldClient p_178890_2_,
            final ItemStack p_178890_3_, final BlockPos p_178890_4_, final EnumFacing p_178890_5_,
            final Vec3 p_178890_6_) {
        syncCurrentPlayItem();
        final float var7 = (float) (p_178890_6_.xCoord - p_178890_4_.getX());
        final float var8 = (float) (p_178890_6_.yCoord - p_178890_4_.getY());
        final float var9 = (float) (p_178890_6_.zCoord - p_178890_4_.getZ());
        boolean var10 = false;

        if (!Minecraft.theWorld.getWorldBorder().contains(p_178890_4_)) {
            return false;
        } else {
            if (currentGameType != WorldSettings.GameType.SPECTATOR) {
                final IBlockState var11 = p_178890_2_.getBlockState(p_178890_4_);

                if ((!p_178890_1_.isSneaking() || p_178890_1_.getHeldItem() == null)
                        && var11.getBlock().onBlockActivated(p_178890_2_, p_178890_4_, var11, p_178890_1_, p_178890_5_,
                                var7, var8, var9)) {
                    var10 = true;
                }

                if (!var10 && p_178890_3_ != null && p_178890_3_.getItem() instanceof ItemBlock) {
                    final ItemBlock var12 = (ItemBlock) p_178890_3_.getItem();

                    if (!var12.canPlaceBlockOnSide(p_178890_2_, p_178890_4_, p_178890_5_, p_178890_1_, p_178890_3_)) {
                        return false;
                    }
                }
            }

            netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(p_178890_4_, p_178890_5_.getIndex(),
                    p_178890_1_.inventory.getCurrentItem(), var7, var8, var9));

            if (!var10 && currentGameType != WorldSettings.GameType.SPECTATOR) {
                if (p_178890_3_ == null) {
                    return false;
                } else if (currentGameType.isCreative()) {
                    final int var14 = p_178890_3_.getMetadata();
                    final int var15 = p_178890_3_.stackSize;
                    final boolean var13 = p_178890_3_.onItemUse(p_178890_1_, p_178890_2_, p_178890_4_, p_178890_5_,
                            var7, var8, var9);
                    p_178890_3_.setItemDamage(var14);
                    p_178890_3_.stackSize = var15;
                    return var13;
                } else {
                    return p_178890_3_.onItemUse(p_178890_1_, p_178890_2_, p_178890_4_, p_178890_5_, var7, var8, var9);
                }
            } else {
                return true;
            }
        }
    }

    /**
     * Notifies the server of things like consuming food, etc...
     */
    public boolean sendUseItem(final EntityPlayer playerIn, final World worldIn, final ItemStack itemStackIn) {
        if (currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
        } else {
            syncCurrentPlayItem();
            netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(playerIn.inventory.getCurrentItem()));
            final int var4 = itemStackIn.stackSize;
            final ItemStack var5 = itemStackIn.useItemRightClick(worldIn, playerIn);

            if (var5 == itemStackIn && (var5 == null || var5.stackSize == var4)) {
                return false;
            } else {
                playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = var5;

                if (var5.stackSize == 0) {
                    playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = null;
                }

                return true;
            }
        }
    }

    public EntityPlayerSP func_178892_a(final World worldIn, final StatFileWriter p_178892_2_) {
        return new EntityPlayerSP(mc, worldIn, netClientHandler, p_178892_2_);
    }

    /**
     * Attacks an entity
     */
    public void attackEntity(final EntityPlayer playerIn, final Entity targetEntity) {
        syncCurrentPlayItem();
        netClientHandler.addToSendQueue(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.ATTACK));

        if (currentGameType != WorldSettings.GameType.SPECTATOR) {
            playerIn.attackTargetEntityWithCurrentItem(targetEntity);
        }
    }

    /**
     * Send packet to server - player is interacting with another entity (left
     * click)
     */
    public boolean interactWithEntitySendPacket(final EntityPlayer playerIn, final Entity targetEntity) {
        syncCurrentPlayItem();
        netClientHandler.addToSendQueue(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.INTERACT));
        return currentGameType != WorldSettings.GameType.SPECTATOR && playerIn.interactWith(targetEntity);
    }

    public boolean func_178894_a(final EntityPlayer p_178894_1_, final Entity p_178894_2_,
            final MovingObjectPosition p_178894_3_) {
        syncCurrentPlayItem();
        final Vec3 var4 = new Vec3(p_178894_3_.hitVec.xCoord - p_178894_2_.posX,
                p_178894_3_.hitVec.yCoord - p_178894_2_.posY, p_178894_3_.hitVec.zCoord - p_178894_2_.posZ);
        netClientHandler.addToSendQueue(new C02PacketUseEntity(p_178894_2_, var4));
        return currentGameType != WorldSettings.GameType.SPECTATOR && p_178894_2_.func_174825_a(p_178894_1_, var4);
    }

    /**
     * Handles slot clicks sends a packet to the server.
     */
    public ItemStack windowClick(final int windowId, final int slotId, final int p_78753_3_, final int p_78753_4_,
            final EntityPlayer playerIn) {
        final short var6 = playerIn.openContainer.getNextTransactionID(playerIn.inventory);
        final ItemStack var7 = playerIn.openContainer.slotClick(slotId, p_78753_3_, p_78753_4_, playerIn);
        netClientHandler.addToSendQueue(new C0EPacketClickWindow(windowId, slotId, p_78753_3_, p_78753_4_, var7, var6));
        return var7;
    }

    /**
     * GuiEnchantment uses this during multiplayer to tell PlayerControllerMP to
     * send a packet indicating the enchantment action the player has taken.
     */
    public void sendEnchantPacket(final int p_78756_1_, final int p_78756_2_) {
        netClientHandler.addToSendQueue(new C11PacketEnchantItem(p_78756_1_, p_78756_2_));
    }

    /**
     * Used in PlayerControllerMP to update the server with an ItemStack in a
     * slot.
     */
    public void sendSlotPacket(final ItemStack itemStackIn, final int slotId) {
        if (currentGameType.isCreative()) {
            netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(slotId, itemStackIn));
        }
    }

    /**
     * Sends a Packet107 to the server to drop the item on the ground
     */
    public void sendPacketDropItem(final ItemStack itemStackIn) {
        if (currentGameType.isCreative() && itemStackIn != null) {
            netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, itemStackIn));
        }
    }

    public void onStoppedUsingItem(final EntityPlayer playerIn) {
        syncCurrentPlayItem();
        netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                BlockPos.ORIGIN, EnumFacing.DOWN));
        playerIn.stopUsingItem();
    }

    public boolean gameIsSurvivalOrAdventure() {
        return currentGameType.isSurvivalOrAdventure();
    }

    /**
     * Checks if the player is not creative, used for checking if it should
     * break a block instantly
     */
    public boolean isNotCreative() {
        return !currentGameType.isCreative();
    }

    /**
     * returns true if player is in creative mode
     */
    public boolean isInCreativeMode() {
        return currentGameType.isCreative();
    }

    /**
     * true for hitting entities far away.
     */
    public boolean extendedReach() {
        return currentGameType.isCreative();
    }

    /**
     * Checks if the player is riding a horse, used to chose the GUI to open
     */
    public boolean isRidingHorse() {
        return Minecraft.thePlayer.isRiding() && Minecraft.thePlayer.ridingEntity instanceof EntityHorse;
    }

    public boolean isSpectatorMode() {
        return currentGameType == WorldSettings.GameType.SPECTATOR;
    }

    public WorldSettings.GameType func_178889_l() {
        return currentGameType;
    }
}
