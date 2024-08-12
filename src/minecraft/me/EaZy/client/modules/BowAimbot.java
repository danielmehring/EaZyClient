package me.EaZy.client.modules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.EntityUtils;
import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.Location;
import me.EaZy.client.utils.PredictUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;

public class BowAimbot extends Module {

public static BowAimbot mod;


    public static final int EaZy = 99;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public BowAimbot() {
        super("BowAimbot", 0, "ba", Category.COMBAT, "Aims the closest\nentity with your bow.");
        Client.setmgr.rSetting(new Setting("IDCheck", this, true));
        Client.setmgr.rSetting(new Setting("Invisibles", this, false));
        Client.setmgr.rSetting(new Setting("HurtCheck", this, false));
        Client.setmgr.rSetting(new Setting("TabCheck", this, false));
        Client.setmgr.rSetting(new Setting("SpeedCheck", this, false));
        Client.setmgr.rSetting(new Setting("OnlyIngame", this, false));
        Client.setmgr.rSetting(new Setting("WallDamage", this, false));
        Client.setmgr.rSetting(new Setting("PremiCheck", this, false));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "BogenZielBot";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onUpdate() {
        if (!isToggled()) {
            if (togglecmd) {
                setToggled(true);
                onEnable();
            }
            return;
        }
        if (isToggled() && !togglecmd) {
            setToggled(false);
            onDisable();
            return;
        }
        final EntityPlayer targetEntity = getClosestEntity();
        if (Minecraft.thePlayer.getCurrentEquippedItem() != null
                && Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && targetEntity != null
                && !targetEntity.isInvisible()) {
            final int bowCurrentCharge = Minecraft.thePlayer.getItemInUseDuration();
            if (bowCurrentCharge == 0) {
                EntityUtils.setLookChanged(false);
            }
            float bowVelocity = bowCurrentCharge / 20.0f;
            if ((bowVelocity = (bowVelocity * bowVelocity + bowVelocity * 2.0f) / 3.0f) < 0.1) {
                return;
            }
            if (bowVelocity > 1.0f) {
                bowVelocity = 1.0f;
            }
            final Location predicted = PredictUtil.predictEntityLocation(targetEntity,
                    Minecraft.getNetHandler().func_175102_a(Minecraft.thePlayer.getUniqueID()).responseTime
                    + Minecraft.getNetHandler().func_175102_a(targetEntity.getUniqueID()).responseTime);
            final double xDistance = predicted.x - Minecraft.thePlayer.posX;
            final double zDistance = predicted.z - Minecraft.thePlayer.posZ;
            final double eyeDistance = predicted.y + targetEntity.getEyeHeight()
                    - (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
            final double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
            Math.sqrt(trajectoryXZ * trajectoryXZ + eyeDistance * eyeDistance);
            final float trajectoryTheta90 = (float) (Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793)
                    - 90.0f;
            final float bowTrajectory = -getTrajectoryAngleSolutionLow((float) trajectoryXZ, (float) eyeDistance,
                    bowVelocity);
            EntityUtils.facePacket(trajectoryTheta90, Float.isNaN(bowTrajectory) ? 0 : bowTrajectory);
            // Minecraft.thePlayer.rotationYaw = trajectoryTheta90;
            // Minecraft.thePlayer.rotationPitch = Float.isNaN(bowTrajectory) ?
            // 0 : bowTrajectory;
        } else {
            EntityUtils.setLookChanged(false);
        }
    }

    public EntityPlayer getClosestEntity() {
        try {
            EntityPlayer closestEntity = null;
            for (final Object o : Minecraft.theWorld.loadedEntityList) {
                if (!(o instanceof EntityPlayer)) {
                    continue;
                }
                final EntityPlayer en = (EntityPlayer) o;
                if (isValid(en)) {
                    if (closestEntity == null) {
                        closestEntity = en;
                    } else if (closestEntity.getDistanceToEntity(Minecraft.thePlayer) > en
                            .getDistanceToEntity(Minecraft.thePlayer)) {
                        closestEntity = en;
                    }
                }
            }
            return closestEntity;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class PlayerComparator implements Comparator {

        private PlayerComparator() {
        }

        private int func_178952_a(final NetworkPlayerInfo p_178952_1_, final NetworkPlayerInfo p_178952_2_) {
            final ScorePlayerTeam var3 = p_178952_1_.func_178850_i();
            final ScorePlayerTeam var4 = p_178952_2_.func_178850_i();
            return ComparisonChain.start()
                    .compareTrueFirst(p_178952_1_.getGameType() != WorldSettings.GameType.SPECTATOR,
                            p_178952_2_.getGameType() != WorldSettings.GameType.SPECTATOR)
                    .compare(var3 != null ? var3.getRegisteredName() : "", var4 != null ? var4.getRegisteredName() : "")
                    .compare(p_178952_1_.func_178845_a().getName(), p_178952_2_.func_178845_a().getName()).result();
        }

        @Override
        public int compare(final Object p_compare_1_, final Object p_compare_2_) {
            return func_178952_a((NetworkPlayerInfo) p_compare_1_, (NetworkPlayerInfo) p_compare_2_);
        }

        private PlayerComparator(final Object p_i45528_1_) {
            this();
        }
    }

    private boolean isValid(final EntityPlayer ep) {
        if (Minecraft.thePlayer.isDead) {
            return false;
        }
        if (ep == null) {
            return false;
        }
        if (ep instanceof EntityPlayerSP) {
            return false;
        }
        if (ep.isDead || ep.getHealth() <= 0) {
            return false;
        }
        if (!ep.isEntityAlive()) {
            return false;
        }
        if (!ep.canEntityBeSeen(Minecraft.thePlayer)) {
            return false;
        }
        if (ep.getName().contains("§")) {
            return false;
        }
        if (!Client.setmgr.getSettingByName(this, "Invisibles").getValBoolean()) {
            if (ep.isInvisible()) {
                return false;
            }
        }
        if (Client.setmgr.getSettingByName(this, "PremiCheck").getValBoolean()) {
            if (ep.getGameProfile().getProperties().size() == 0) {
                return false;
            }
        }

        if (Client.setmgr.getSettingByName(this, "TabCheck").getValBoolean()) {
            final NetHandlerPlayClient var4 = Minecraft.thePlayer.sendQueue;
            final Ordering ordered = Ordering.from(new PlayerComparator(null));
            final List orderedList = ordered.sortedCopy(var4.getPlayerInfo());
            final Iterator var8 = orderedList.iterator();

            final ArrayList<String> names = new ArrayList<>();

            while (var8.hasNext()) {
                final NetworkPlayerInfo var9 = (NetworkPlayerInfo) var8.next();

                names.add(EnumChatFormatting.getTextWithoutFormattingCodes(var9.getPlayerNameForReal()));
            }

            if (!names.contains(
                    EnumChatFormatting.getTextWithoutFormattingCodes(ep.getDisplayName().getFormattedText()))) {
                return false;
            }

        }
        if (Client.setmgr.getSettingByName(this, "OnlyIngame").getValBoolean() && ep.getName().contains("§")
                && ep.getName().length() == 11) {
            return false;
        }
        if (Client.setmgr.getSettingByName(this, "SpeedCheck").getValBoolean() && ep.tooFastDown) {
            return false;
        }
        if (Client.setmgr.getSettingByName(this, "WallDamage").getValBoolean() && ep.wallDmgd) {
            return false;
        }
        if (Friends.contains(ep)) {
            return false;
        }
        if (ep.hurtTime > 0 && Client.setmgr.getSettingByName(this, "HurtCheck").getValBoolean()) {
            return false;
        }
        if (Client.setmgr.getSettingByName(this, "IDCheck").getValBoolean()) {
            if (ep.getEntityId() - 1073641823 > 0) {
                return false;
            }
        }
        return true;
    }

    private float getTrajectoryAngleSolutionLow(final float angleX, final float angleY, final float bowVelocity) {
        final float velocityIncrement = 0.006f;
        final float squareRootBow = bowVelocity * bowVelocity * bowVelocity * bowVelocity - velocityIncrement
                * (velocityIncrement * (angleX * angleX) + 2.0f * angleY * (bowVelocity * bowVelocity));
        return (float) Math.toDegrees(
                Math.atan((bowVelocity * bowVelocity - Math.sqrt(squareRootBow)) / (velocityIncrement * angleX)));
    }

    public float getDistanceBetweenAngles(final float angle1, final float angle2) {
        float angleToEntity = Math.abs(angle1 - angle2) % 360.0f;
        if (angleToEntity > 180.0f) {
            angleToEntity = 360.0f - angleToEntity;
        }
        return angleToEntity;
    }
}
