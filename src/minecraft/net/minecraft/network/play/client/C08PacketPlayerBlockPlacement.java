package net.minecraft.network.play.client;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class C08PacketPlayerBlockPlacement implements Packet {

public static final int EaZy = 1382;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
	private BlockPos field_179725_b;
	private int placedBlockDirection;
	private ItemStack stack;
	private float facingX;
	private float facingY;
	private float facingZ;
	// private static final String __OBFID = "http://https://fuckuskid00001371";

	public C08PacketPlayerBlockPlacement() {}

	public C08PacketPlayerBlockPlacement(final ItemStack p_i45930_1_) {
		this(field_179726_a, 255, p_i45930_1_, 0.0F, 0.0F, 0.0F);
	}

	public C08PacketPlayerBlockPlacement(final BlockPos p_i45931_1_, final int p_i45931_2_, final ItemStack p_i45931_3_,
			final float p_i45931_4_, final float p_i45931_5_, final float p_i45931_6_) {
		field_179725_b = p_i45931_1_;
		placedBlockDirection = p_i45931_2_;
		stack = p_i45931_3_ != null ? p_i45931_3_.copy() : null;
		facingX = p_i45931_4_;
		facingY = p_i45931_5_;
		facingZ = p_i45931_6_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179725_b = data.readBlockPos();
		placedBlockDirection = data.readUnsignedByte();
		stack = data.readItemStackFromBuffer();
		facingX = data.readUnsignedByte() / 16.0F;
		facingY = data.readUnsignedByte() / 16.0F;
		facingZ = data.readUnsignedByte() / 16.0F;
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeBlockPos(field_179725_b);
		data.writeByte(placedBlockDirection);
		data.writeItemStackToBuffer(stack);
		data.writeByte((int) (facingX * 16.0F));
		data.writeByte((int) (facingY * 16.0F));
		data.writeByte((int) (facingZ * 16.0F));
	}

	public void func_180769_a(final INetHandlerPlayServer p_180769_1_) {
		p_180769_1_.processPlayerBlockPlacement(this);
	}

	public BlockPos func_179724_a() {
		return field_179725_b;
	}

	public int getPlacedBlockDirection() {
		return placedBlockDirection;
	}

	public ItemStack getStack() {
		return stack;
	}

	/**
	 * Returns the offset from xPosition where the actual click took place.
	 */
	public float getPlacedBlockOffsetX() {
		return facingX;
	}

	/**
	 * Returns the offset from yPosition where the actual click took place.
	 */
	public float getPlacedBlockOffsetY() {
		return facingY;
	}

	/**
	 * Returns the offset from zPosition where the actual click took place.
	 */
	public float getPlacedBlockOffsetZ() {
		return facingZ;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180769_a((INetHandlerPlayServer) handler);
	}
}
