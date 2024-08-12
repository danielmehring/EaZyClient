package net.minecraft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity implements IUpdatePlayerListBox {

public static final int EaZy = 1585;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final MobSpawnerBaseLogic field_145882_a = new MobSpawnerBaseLogic() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000361";
		@Override
		public void func_98267_a(final int p_98267_1_) {
			TileEntityMobSpawner.this.worldObj.addBlockEvent(TileEntityMobSpawner.this.pos, Blocks.mob_spawner,
					p_98267_1_, 0);
		}

		@Override
		public World getSpawnerWorld() {
			return TileEntityMobSpawner.this.worldObj;
		}

		@Override
		public BlockPos func_177221_b() {
			return TileEntityMobSpawner.this.pos;
		}

		@Override
		public void setRandomEntity(final MobSpawnerBaseLogic.WeightedRandomMinecart p_98277_1_) {
			super.setRandomEntity(p_98277_1_);

			if (getSpawnerWorld() != null) {
				getSpawnerWorld().markBlockForUpdate(TileEntityMobSpawner.this.pos);
			}
		}
	};
	// private static final String __OBFID = "http://https://fuckuskid00000360";

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		field_145882_a.readFromNBT(compound);
	}

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);
		field_145882_a.writeToNBT(compound);
	}

	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update() {
		field_145882_a.updateSpawner();
	}

	/**
	 * Overriden in a sign to provide the text.
	 */
	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound var1 = new NBTTagCompound();
		writeToNBT(var1);
		var1.removeTag("SpawnPotentials");
		return new S35PacketUpdateTileEntity(pos, 1, var1);
	}

	@Override
	public boolean receiveClientEvent(final int id, final int type) {
		return field_145882_a.setDelayToMin(id) ? true : super.receiveClientEvent(id, type);
	}

	public MobSpawnerBaseLogic getSpawnerBaseLogic() {
		return field_145882_a;
	}
}
