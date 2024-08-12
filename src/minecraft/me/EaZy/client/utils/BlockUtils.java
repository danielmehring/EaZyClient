package me.EaZy.client.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockUtils {

    public static final int EaZy = 205;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isInsideBlock(final Entity entity) {
        int x = MathHelper.floor_double(entity.getEntityBoundingBox().expand(-0.1, -0.1, -0.1).minX);
        while (x < MathHelper.floor_double(entity.getEntityBoundingBox().expand(-0.1, -0.1, -0.1).maxX) + 1) {
            int y = MathHelper.floor_double(entity.getEntityBoundingBox().expand(-0.1, -0.1, -0.1).minY);
            while (y < MathHelper.floor_double(entity.getEntityBoundingBox().expand(-0.1, -0.1, -0.1).maxY) + 1) {
                int z = MathHelper.floor_double(entity.getEntityBoundingBox().expand(-0.1, -0.1, -0.1).minZ);
                while (z < MathHelper.floor_double(entity.getEntityBoundingBox().expand(-0.1, -0.1, -0.1).maxZ) + 1) {
                    final Block block = Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.theWorld,
                                new BlockPos(x, y, z), Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && entity.getEntityBoundingBox().intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        return false;
    }

    public static double getOffset(final Block block, final BlockPos pos) {
        final IBlockState state = Minecraft.theWorld.getBlockState(pos);
        double offset = 0.0;
        if (block instanceof BlockSlab && !((BlockSlab) block).isDouble()) {
            offset -= 0.5;
        } else if (block instanceof BlockEndPortalFrame) {
            offset -= 0.20000000298023224;
        } else if (block instanceof BlockBed) {
            offset -= 0.4399999976158142;
        } else if (block instanceof BlockCake) {
            offset -= 0.5;
        } else if (block instanceof BlockDaylightDetector) {
            offset -= 0.625;
        } else if (block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater) {
            offset -= 0.875;
        } else if (block instanceof BlockChest || block == Blocks.ender_chest) {
            offset -= 0.125;
        } else if (block instanceof BlockLilyPad) {
            offset -= 0.949999988079071;
        } else if (block == Blocks.snow_layer) {
            offset -= 0.875;
            offset += 0.125f * ((Integer) state.getValue(BlockSnow.LAYERS_PROP) - 1);
        } else if (BlockUtils.isValidBlock(block)) {
            offset -= 1.0;
        }
        return offset;
    }

    private static boolean isValidBlock(final Block block) {
        return block == Blocks.portal || block == Blocks.snow_layer || block instanceof BlockTripWireHook
                || block instanceof BlockTripWire || block instanceof BlockDaylightDetector
                || block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater
                || block instanceof BlockSign || block instanceof BlockAir || block instanceof BlockPressurePlate
                || block instanceof BlockTallGrass || block instanceof BlockFlower || block instanceof BlockMushroom
                || block instanceof BlockDoublePlant || block instanceof BlockReed || block instanceof BlockSapling
                || block == Blocks.carrots || block == Blocks.wheat || block == Blocks.nether_wart
                || block == Blocks.potatoes || block == Blocks.pumpkin_stem || block == Blocks.melon_stem
                || block == Blocks.heavy_weighted_pressure_plate || block == Blocks.light_weighted_pressure_plate
                || block == Blocks.redstone_wire || block instanceof BlockTorch || block instanceof BlockRedstoneTorch
                || block == Blocks.lever || block instanceof BlockButton;
    }

    public static Block getBlock(final int x, final int y, final int z) {
        return Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(final BlockPos pos) {
        return Minecraft.theWorld.getBlockState(pos).getBlock();
    }

    public static Block getBlock(final double x, final double y, final double z) {
        return Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing,
            final boolean real) {
        final EntitySnowball temp = new EntitySnowball(Minecraft.theWorld);
        if (real) {
            switch (facing.getName()) {
                case "south": {
                    temp.posX = x + 0.5;
                    temp.posY = y + 0.5;
                    temp.posZ = z + 1;
                    break;
                }

                case "north": {
                    temp.posX = x + 0.5;
                    temp.posY = y + 0.5;
                    temp.posZ = z;
                    break;
                }

                case "west": {
                    temp.posX = x;
                    temp.posY = y + 0.5;
                    temp.posZ = z + 0.5;
                    break;
                }

                case "east": {
                    temp.posX = x + 1;
                    temp.posY = y + 0.5;
                    temp.posZ = z + 0.5;
                    break;
                }

                case "up": {
                    temp.posX = x + 0.5;
                    temp.posY = y + 0.5;
                    temp.posZ = z + 0.5;
                    break;
                }
            }
            return EntityUtils.getAnglesToEntity(temp);
        } else {
            temp.posX = x + 0.5;
            temp.posY = y + 0.5;
            temp.posZ = z + 0.5;
            temp.posX += facing.getDirectionVec().getX() * 0.25;
            temp.posY += facing.getDirectionVec().getY() * 0.25;
            temp.posZ += facing.getDirectionVec().getZ() * 0.25;
            return EntityUtils.getAnglesToEntity(temp);
        }
    }

    public static void faceBlockPacket(final BlockPos blockPos) {
        final double diffX = blockPos.getX() + 0.5 - Minecraft.thePlayer.posX;
        final double diffY = blockPos.getY() + 0.5 - (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
        final double diffZ = blockPos.getZ() + 0.5 - Minecraft.thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        EntityUtils.prevPitch = EntityUtils.pitch;
        EntityUtils.prevYaw = EntityUtils.yaw;
        EntityUtils.pitch = Minecraft.thePlayer.rotationPitch
                + MathHelper.wrapAngleTo180_float(pitch - Minecraft.thePlayer.rotationPitch);
        EntityUtils.yaw = Minecraft.thePlayer.rotationYaw
                + MathHelper.wrapAngleTo180_float(yaw - Minecraft.thePlayer.rotationYaw);
        EntityUtils.setLookChanged(true);
    }
}
