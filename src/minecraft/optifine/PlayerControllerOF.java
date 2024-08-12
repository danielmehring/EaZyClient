package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PlayerControllerOF extends PlayerControllerMP {

public static final int EaZy = 1944;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private boolean acting = false;

	public PlayerControllerOF(final Minecraft mcIn, final NetHandlerPlayClient netHandler) {
		super(mcIn, netHandler);
	}

	@Override
	public boolean breakBlock(final BlockPos loc, final EnumFacing face) {
		acting = true;
		final boolean res = super.breakBlock(loc, face);
		acting = false;
		return res;
	}

	@Override
	public boolean onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing) {
		acting = true;
		final boolean res = super.onPlayerDamageBlock(posBlock, directionFacing);
		acting = false;
		return res;
	}

	/**
	 * Notifies the server of things like consuming food, etc...
	 */
	@Override
	public boolean sendUseItem(final EntityPlayer player, final World worldIn, final ItemStack stack) {
		acting = true;
		final boolean res = super.sendUseItem(player, worldIn, stack);
		acting = false;
		return res;
	}

	@Override
	public boolean placeBlock(final EntityPlayerSP player, final WorldClient worldIn, final ItemStack stack,
			final BlockPos pos, final EnumFacing facing, final Vec3 vec) {
		acting = true;
		final boolean res = super.placeBlock(player, worldIn, stack, pos, facing, vec);
		acting = false;
		return res;
	}

	public boolean isActing() {
		return acting;
	}
}
