package net.minecraft.client.entity;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

import com.mojang.authlib.GameProfile;

import optifine.CapeUtils;
import optifine.Config;
import optifine.PlayerConfigurations;
import optifine.Reflector;

public abstract class AbstractClientPlayer extends EntityPlayer {

    public static final int EaZy = 451;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private NetworkPlayerInfo field_175157_a;
    private ResourceLocation locationOfCape = null;
    private String nameClear = null;
    // private static final String __OBFID = "http://https://fuckuskid00000935";

    public AbstractClientPlayer(final World worldIn, final GameProfile gameProfile) {
        super(worldIn, gameProfile);
        nameClear = gameProfile.getName();

        if (nameClear != null && !nameClear.isEmpty()) {
            nameClear = StringUtils.stripControlCodes(nameClear);
        }

        CapeUtils.downloadCape(this);
        PlayerConfigurations.getPlayerConfiguration(this);
    }

    @Override
    public boolean isSpectatorMode() {
        final NetworkPlayerInfo var1 = Minecraft.getNetHandler().func_175102_a(getGameProfile().getId());
        return var1 != null && var1.getGameType() == WorldSettings.GameType.SPECTATOR;
    }

    public boolean hasCape() {
        return func_175155_b() != null;
    }

    public NetworkPlayerInfo func_175155_b() {
        if (field_175157_a == null) {

            field_175157_a = Minecraft.getNetHandler().func_175102_a(getUniqueID());
        }

        return field_175157_a;
    }

    public boolean hasSkin() {
        final NetworkPlayerInfo var1 = func_175155_b();
        return var1 != null && var1.func_178856_e();
    }

    public ResourceLocation getLocationSkin() {
        final NetworkPlayerInfo var1 = func_175155_b();
        return var1 == null ? DefaultPlayerSkin.func_177334_a(getUniqueID()) : var1.func_178837_g();
    }

    public ResourceLocation getLocationCape() {
        if (!Config.isShowCapes()) {
            return null;
        } else if (locationOfCape != null) {
            return locationOfCape;
        } else {
            final NetworkPlayerInfo var1 = func_175155_b();
            return var1 == null ? null : var1.func_178861_h();
        }
    }

    public static ThreadDownloadImageData getDownloadImageSkin(final ResourceLocation resourceLocationIn,
            final String username) {

        final TextureManager var2 = Minecraft.getTextureManager();
        Object var3 = var2.getTexture(resourceLocationIn);

        if (var3 == null) {
            var3 = new ThreadDownloadImageData((File) null,
                    String.format("http://skins.minecraft.net/MinecraftSkins/%s.png",
                            new Object[]{StringUtils.stripControlCodes(username)}),
                    DefaultPlayerSkin.func_177334_a(getUUIDFromPlayerName(username)), new ImageBufferDownload());
            var2.loadTexture(resourceLocationIn, (ITextureObject) var3);
        }

        return (ThreadDownloadImageData) var3;
    }

    public static ResourceLocation getLocationSkin(final String username) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
    }

    public String func_175154_l() {
        final NetworkPlayerInfo var1 = func_175155_b();
        return var1 == null ? DefaultPlayerSkin.getModelNameFromUUID(getUniqueID()) : var1.func_178851_f();
    }

    public float func_175156_o() {
        float var1 = 1.0F;

        if (capabilities.isFlying) {
            var1 *= 1.1F;
        }

        final IAttributeInstance var2 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        var1 = (float) (var1 * ((var2.getAttributeValue() / capabilities.getWalkSpeed() + 1.0D) / 2.0D));

        if (capabilities.getWalkSpeed() == 0.0F || Float.isNaN(var1) || Float.isInfinite(var1)) {
            var1 = 1.0F;
        }

        if (isUsingItem() && getItemInUse().getItem() == Items.bow) {
            final int var3 = getItemInUseDuration();
            float var4 = var3 / 20.0F;

            if (var4 > 1.0F) {
                var4 = 1.0F;
            } else {
                var4 *= var4;
            }

            var1 *= 1.0F - var4 * 0.15F;
        }

        return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector
                .callFloat(Reflector.ForgeHooksClient_getOffsetFOV, new Object[]{this, var1}) : var1;
    }

    public String getNameClear() {
        return nameClear;
    }

    public ResourceLocation getLocationOfCape() {
        return locationOfCape;
    }

    public void setLocationOfCape(final ResourceLocation locationOfCape) {
        this.locationOfCape = locationOfCape;
    }
}
