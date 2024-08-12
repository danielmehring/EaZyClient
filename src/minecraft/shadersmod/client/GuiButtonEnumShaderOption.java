package shadersmod.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiButtonEnumShaderOption extends GuiButton {

public static final int EaZy = 1990;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private EnumShaderOption enumShaderOption = null;

	public GuiButtonEnumShaderOption(final EnumShaderOption enumShaderOption, final int x, final int y,
			final int widthIn, final int heightIn) {
		super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
		this.enumShaderOption = enumShaderOption;
	}

	public EnumShaderOption getEnumShaderOption() {
		return enumShaderOption;
	}

	private static String getButtonText(final EnumShaderOption eso) {
		final String nameText = I18n.format(eso.getResourceKey(), new Object[0]) + ": ";

		switch (GuiButtonEnumShaderOption.NamelessClass895471824.$SwitchMap$shadersmod$client$EnumShaderOption[eso
				.ordinal()]) {
			case 1:
				return nameText + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);

			case 2:
				return nameText + GuiShaders.toStringOnOff(Shaders.configNormalMap);

			case 3:
				return nameText + GuiShaders.toStringOnOff(Shaders.configSpecularMap);

			case 4:
				return nameText + GuiShaders.toStringQuality(Shaders.configRenderResMul);

			case 5:
				return nameText + GuiShaders.toStringQuality(Shaders.configShadowResMul);

			case 6:
				return nameText + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);

			case 7:
				return nameText + GuiShaders.toStringOnOff(Shaders.configCloudShadow);

			case 8:
				return nameText + Shaders.configOldLighting.getUserValue();

			case 9:
				return nameText + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);

			case 10:
				return nameText + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);

			default:
				return nameText + Shaders.getEnumShaderOption(eso);
		}
	}

	public void updateButtonText() {
		displayString = getButtonText(enumShaderOption);
	}

	static class NamelessClass895471824 {
		static final int[] $SwitchMap$shadersmod$client$EnumShaderOption = new int[EnumShaderOption.values().length];

		static {
			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.ANTIALIASING.ordinal()] = 1;
			} catch (final NoSuchFieldError var10) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.NORMAL_MAP.ordinal()] = 2;
			} catch (final NoSuchFieldError var9) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SPECULAR_MAP.ordinal()] = 3;
			} catch (final NoSuchFieldError var8) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.RENDER_RES_MUL.ordinal()] = 4;
			} catch (final NoSuchFieldError var7) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADOW_RES_MUL.ordinal()] = 5;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.HAND_DEPTH_MUL.ordinal()] = 6;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.CLOUD_SHADOW.ordinal()] = 7;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.OLD_LIGHTING.ordinal()] = 8;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADOW_CLIP_FRUSTRUM.ordinal()] = 9;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TWEAK_BLOCK_DAMAGE.ordinal()] = 10;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
