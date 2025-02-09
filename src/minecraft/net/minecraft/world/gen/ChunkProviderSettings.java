package net.minecraft.world.gen;

import net.minecraft.util.JsonUtils;
import net.minecraft.world.biome.BiomeGenBase;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ChunkProviderSettings {

public static final int EaZy = 1731;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public final float field_177811_a;
	public final float field_177809_b;
	public final float field_177810_c;
	public final float field_177806_d;
	public final float field_177808_e;
	public final float field_177803_f;
	public final float field_177804_g;
	public final float field_177825_h;
	public final float field_177827_i;
	public final float field_177821_j;
	public final float field_177823_k;
	public final float field_177817_l;
	public final float field_177819_m;
	public final float field_177813_n;
	public final float field_177815_o;
	public final float field_177843_p;
	public final int field_177841_q;
	public final boolean field_177839_r;
	public final boolean field_177837_s;
	public final int field_177835_t;
	public final boolean field_177833_u;
	public final boolean field_177831_v;
	public final boolean field_177829_w;
	public final boolean field_177854_x;
	public final boolean field_177852_y;
	public final boolean field_177850_z;
	public final boolean field_177781_A;
	public final int field_177782_B;
	public final boolean field_177783_C;
	public final int field_177777_D;
	public final boolean field_177778_E;
	public final int field_177779_F;
	public final int field_177780_G;
	public final int field_177788_H;
	public final int field_177789_I;
	public final int field_177790_J;
	public final int field_177791_K;
	public final int field_177784_L;
	public final int field_177785_M;
	public final int field_177786_N;
	public final int field_177787_O;
	public final int field_177797_P;
	public final int field_177796_Q;
	public final int field_177799_R;
	public final int field_177798_S;
	public final int field_177793_T;
	public final int field_177792_U;
	public final int field_177795_V;
	public final int field_177794_W;
	public final int field_177801_X;
	public final int field_177800_Y;
	public final int field_177802_Z;
	public final int field_177846_aa;
	public final int field_177847_ab;
	public final int field_177844_ac;
	public final int field_177845_ad;
	public final int field_177851_ae;
	public final int field_177853_af;
	public final int field_177848_ag;
	public final int field_177849_ah;
	public final int field_177832_ai;
	public final int field_177834_aj;
	public final int field_177828_ak;
	public final int field_177830_al;
	public final int field_177840_am;
	public final int field_177842_an;
	public final int field_177836_ao;
	public final int field_177838_ap;
	public final int field_177818_aq;
	public final int field_177816_ar;
	public final int field_177814_as;
	public final int field_177812_at;
	public final int field_177826_au;
	public final int field_177824_av;
	public final int field_177822_aw;
	public final int field_177820_ax;
	public final int field_177807_ay;
	public final int field_177805_az;
	// private static final String __OBFID = "http://https://fuckuskid00002006";

	private ChunkProviderSettings(final ChunkProviderSettings.Factory p_i45639_1_) {
		field_177811_a = p_i45639_1_.field_177899_b;
		field_177809_b = p_i45639_1_.field_177900_c;
		field_177810_c = p_i45639_1_.field_177896_d;
		field_177806_d = p_i45639_1_.field_177898_e;
		field_177808_e = p_i45639_1_.field_177893_f;
		field_177803_f = p_i45639_1_.field_177894_g;
		field_177804_g = p_i45639_1_.field_177915_h;
		field_177825_h = p_i45639_1_.field_177917_i;
		field_177827_i = p_i45639_1_.field_177911_j;
		field_177821_j = p_i45639_1_.field_177913_k;
		field_177823_k = p_i45639_1_.field_177907_l;
		field_177817_l = p_i45639_1_.field_177909_m;
		field_177819_m = p_i45639_1_.field_177903_n;
		field_177813_n = p_i45639_1_.field_177905_o;
		field_177815_o = p_i45639_1_.field_177933_p;
		field_177843_p = p_i45639_1_.field_177931_q;
		field_177841_q = p_i45639_1_.field_177929_r;
		field_177839_r = p_i45639_1_.field_177927_s;
		field_177837_s = p_i45639_1_.field_177925_t;
		field_177835_t = p_i45639_1_.field_177923_u;
		field_177833_u = p_i45639_1_.field_177921_v;
		field_177831_v = p_i45639_1_.field_177919_w;
		field_177829_w = p_i45639_1_.field_177944_x;
		field_177854_x = p_i45639_1_.field_177942_y;
		field_177852_y = p_i45639_1_.field_177940_z;
		field_177850_z = p_i45639_1_.field_177870_A;
		field_177781_A = p_i45639_1_.field_177871_B;
		field_177782_B = p_i45639_1_.field_177872_C;
		field_177783_C = p_i45639_1_.field_177866_D;
		field_177777_D = p_i45639_1_.field_177867_E;
		field_177778_E = p_i45639_1_.field_177868_F;
		field_177779_F = p_i45639_1_.field_177869_G;
		field_177780_G = p_i45639_1_.field_177877_H;
		field_177788_H = p_i45639_1_.field_177878_I;
		field_177789_I = p_i45639_1_.field_177879_J;
		field_177790_J = p_i45639_1_.field_177880_K;
		field_177791_K = p_i45639_1_.field_177873_L;
		field_177784_L = p_i45639_1_.field_177874_M;
		field_177785_M = p_i45639_1_.field_177875_N;
		field_177786_N = p_i45639_1_.field_177876_O;
		field_177787_O = p_i45639_1_.field_177886_P;
		field_177797_P = p_i45639_1_.field_177885_Q;
		field_177796_Q = p_i45639_1_.field_177888_R;
		field_177799_R = p_i45639_1_.field_177887_S;
		field_177798_S = p_i45639_1_.field_177882_T;
		field_177793_T = p_i45639_1_.field_177881_U;
		field_177792_U = p_i45639_1_.field_177884_V;
		field_177795_V = p_i45639_1_.field_177883_W;
		field_177794_W = p_i45639_1_.field_177891_X;
		field_177801_X = p_i45639_1_.field_177890_Y;
		field_177800_Y = p_i45639_1_.field_177892_Z;
		field_177802_Z = p_i45639_1_.field_177936_aa;
		field_177846_aa = p_i45639_1_.field_177937_ab;
		field_177847_ab = p_i45639_1_.field_177934_ac;
		field_177844_ac = p_i45639_1_.field_177935_ad;
		field_177845_ad = p_i45639_1_.field_177941_ae;
		field_177851_ae = p_i45639_1_.field_177943_af;
		field_177853_af = p_i45639_1_.field_177938_ag;
		field_177848_ag = p_i45639_1_.field_177939_ah;
		field_177849_ah = p_i45639_1_.field_177922_ai;
		field_177832_ai = p_i45639_1_.field_177924_aj;
		field_177834_aj = p_i45639_1_.field_177918_ak;
		field_177828_ak = p_i45639_1_.field_177920_al;
		field_177830_al = p_i45639_1_.field_177930_am;
		field_177840_am = p_i45639_1_.field_177932_an;
		field_177842_an = p_i45639_1_.field_177926_ao;
		field_177836_ao = p_i45639_1_.field_177928_ap;
		field_177838_ap = p_i45639_1_.field_177908_aq;
		field_177818_aq = p_i45639_1_.field_177906_ar;
		field_177816_ar = p_i45639_1_.field_177904_as;
		field_177814_as = p_i45639_1_.field_177902_at;
		field_177812_at = p_i45639_1_.field_177916_au;
		field_177826_au = p_i45639_1_.field_177914_av;
		field_177824_av = p_i45639_1_.field_177912_aw;
		field_177822_aw = p_i45639_1_.field_177910_ax;
		field_177820_ax = p_i45639_1_.field_177897_ay;
		field_177807_ay = p_i45639_1_.field_177895_az;
		field_177805_az = p_i45639_1_.field_177889_aA;
	}

	ChunkProviderSettings(final ChunkProviderSettings.Factory p_i45640_1_, final Object p_i45640_2_) {
		this(p_i45640_1_);
	}

	public static class Factory {
		static final Gson field_177901_a = new GsonBuilder()
				.registerTypeAdapter(ChunkProviderSettings.Factory.class, new ChunkProviderSettings.Serializer())
				.create();
		public float field_177899_b = 684.412F;
		public float field_177900_c = 684.412F;
		public float field_177896_d = 512.0F;
		public float field_177898_e = 512.0F;
		public float field_177893_f = 200.0F;
		public float field_177894_g = 200.0F;
		public float field_177915_h = 0.5F;
		public float field_177917_i = 80.0F;
		public float field_177911_j = 160.0F;
		public float field_177913_k = 80.0F;
		public float field_177907_l = 8.5F;
		public float field_177909_m = 12.0F;
		public float field_177903_n = 1.0F;
		public float field_177905_o = 0.0F;
		public float field_177933_p = 1.0F;
		public float field_177931_q = 0.0F;
		public int field_177929_r = 63;
		public boolean field_177927_s = true;
		public boolean field_177925_t = true;
		public int field_177923_u = 8;
		public boolean field_177921_v = true;
		public boolean field_177919_w = true;
		public boolean field_177944_x = true;
		public boolean field_177942_y = true;
		public boolean field_177940_z = true;
		public boolean field_177870_A = true;
		public boolean field_177871_B = true;
		public int field_177872_C = 4;
		public boolean field_177866_D = true;
		public int field_177867_E = 80;
		public boolean field_177868_F = false;
		public int field_177869_G = -1;
		public int field_177877_H = 4;
		public int field_177878_I = 4;
		public int field_177879_J = 33;
		public int field_177880_K = 10;
		public int field_177873_L = 0;
		public int field_177874_M = 256;
		public int field_177875_N = 33;
		public int field_177876_O = 8;
		public int field_177886_P = 0;
		public int field_177885_Q = 256;
		public int field_177888_R = 33;
		public int field_177887_S = 10;
		public int field_177882_T = 0;
		public int field_177881_U = 80;
		public int field_177884_V = 33;
		public int field_177883_W = 10;
		public int field_177891_X = 0;
		public int field_177890_Y = 80;
		public int field_177892_Z = 33;
		public int field_177936_aa = 10;
		public int field_177937_ab = 0;
		public int field_177934_ac = 80;
		public int field_177935_ad = 17;
		public int field_177941_ae = 20;
		public int field_177943_af = 0;
		public int field_177938_ag = 128;
		public int field_177939_ah = 9;
		public int field_177922_ai = 20;
		public int field_177924_aj = 0;
		public int field_177918_ak = 64;
		public int field_177920_al = 9;
		public int field_177930_am = 2;
		public int field_177932_an = 0;
		public int field_177926_ao = 32;
		public int field_177928_ap = 8;
		public int field_177908_aq = 8;
		public int field_177906_ar = 0;
		public int field_177904_as = 16;
		public int field_177902_at = 8;
		public int field_177916_au = 1;
		public int field_177914_av = 0;
		public int field_177912_aw = 16;
		public int field_177910_ax = 7;
		public int field_177897_ay = 1;
		public int field_177895_az = 16;
		public int field_177889_aA = 16;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002004";

		public static ChunkProviderSettings.Factory func_177865_a(final String p_177865_0_) {
			if (p_177865_0_.length() == 0) {
				return new ChunkProviderSettings.Factory();
			} else {
				try {
					return field_177901_a.fromJson(p_177865_0_, ChunkProviderSettings.Factory.class);
				} catch (final Exception var2) {
					return new ChunkProviderSettings.Factory();
				}
			}
		}

		@Override
		public String toString() {
			return field_177901_a.toJson(this);
		}

		public Factory() {
			func_177863_a();
		}

		public void func_177863_a() {
			field_177899_b = 684.412F;
			field_177900_c = 684.412F;
			field_177896_d = 512.0F;
			field_177898_e = 512.0F;
			field_177893_f = 200.0F;
			field_177894_g = 200.0F;
			field_177915_h = 0.5F;
			field_177917_i = 80.0F;
			field_177911_j = 160.0F;
			field_177913_k = 80.0F;
			field_177907_l = 8.5F;
			field_177909_m = 12.0F;
			field_177903_n = 1.0F;
			field_177905_o = 0.0F;
			field_177933_p = 1.0F;
			field_177931_q = 0.0F;
			field_177929_r = 63;
			field_177927_s = true;
			field_177925_t = true;
			field_177923_u = 8;
			field_177921_v = true;
			field_177919_w = true;
			field_177944_x = true;
			field_177942_y = true;
			field_177940_z = true;
			field_177870_A = true;
			field_177871_B = true;
			field_177872_C = 4;
			field_177866_D = true;
			field_177867_E = 80;
			field_177868_F = false;
			field_177869_G = -1;
			field_177877_H = 4;
			field_177878_I = 4;
			field_177879_J = 33;
			field_177880_K = 10;
			field_177873_L = 0;
			field_177874_M = 256;
			field_177875_N = 33;
			field_177876_O = 8;
			field_177886_P = 0;
			field_177885_Q = 256;
			field_177888_R = 33;
			field_177887_S = 10;
			field_177882_T = 0;
			field_177881_U = 80;
			field_177884_V = 33;
			field_177883_W = 10;
			field_177891_X = 0;
			field_177890_Y = 80;
			field_177892_Z = 33;
			field_177936_aa = 10;
			field_177937_ab = 0;
			field_177934_ac = 80;
			field_177935_ad = 17;
			field_177941_ae = 20;
			field_177943_af = 0;
			field_177938_ag = 128;
			field_177939_ah = 9;
			field_177922_ai = 20;
			field_177924_aj = 0;
			field_177918_ak = 64;
			field_177920_al = 9;
			field_177930_am = 2;
			field_177932_an = 0;
			field_177926_ao = 32;
			field_177928_ap = 8;
			field_177908_aq = 8;
			field_177906_ar = 0;
			field_177904_as = 16;
			field_177902_at = 8;
			field_177916_au = 1;
			field_177914_av = 0;
			field_177912_aw = 16;
			field_177910_ax = 7;
			field_177897_ay = 1;
			field_177895_az = 16;
			field_177889_aA = 16;
		}

		@Override
		public boolean equals(final Object p_equals_1_) {
			if (this == p_equals_1_) {
				return true;
			} else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
				final ChunkProviderSettings.Factory var2 = (ChunkProviderSettings.Factory) p_equals_1_;
				return field_177936_aa != var2.field_177936_aa ? false
						: field_177934_ac != var2.field_177934_ac ? false
								: field_177937_ab != var2.field_177937_ab ? false
										: field_177892_Z != var2.field_177892_Z ? false
												: Float.compare(var2.field_177907_l, field_177907_l) != 0 ? false
														: Float.compare(var2.field_177905_o, field_177905_o) != 0
																? false
																: Float.compare(var2.field_177903_n,
																		field_177903_n) != 0 ? false
																				: Float.compare(var2.field_177931_q,
																						field_177931_q) != 0 ? false
																								: Float.compare(
																										var2.field_177933_p,
																										field_177933_p) != 0
																												? false
																												: field_177877_H != var2.field_177877_H
																														? false
																														: field_177941_ae != var2.field_177941_ae
																																? false
																																: field_177938_ag != var2.field_177938_ag
																																		? false
																																		: field_177943_af != var2.field_177943_af
																																				? false
																																				: field_177935_ad != var2.field_177935_ad
																																						? false
																																						: Float.compare(
																																								var2.field_177899_b,
																																								field_177899_b) != 0
																																										? false
																																										: Float.compare(
																																												var2.field_177915_h,
																																												field_177915_h) != 0
																																														? false
																																														: Float.compare(
																																																var2.field_177893_f,
																																																field_177893_f) != 0
																																																		? false
																																																		: Float.compare(
																																																				var2.field_177894_g,
																																																				field_177894_g) != 0
																																																						? false
																																																						: field_177916_au != var2.field_177916_au
																																																								? false
																																																								: field_177912_aw != var2.field_177912_aw
																																																										? false
																																																										: field_177914_av != var2.field_177914_av
																																																												? false
																																																												: field_177902_at != var2.field_177902_at
																																																														? false
																																																														: field_177883_W != var2.field_177883_W
																																																																? false
																																																																: field_177890_Y != var2.field_177890_Y
																																																																		? false
																																																																		: field_177891_X != var2.field_177891_X
																																																																				? false
																																																																				: field_177884_V != var2.field_177884_V
																																																																						? false
																																																																						: field_177880_K != var2.field_177880_K
																																																																								? false
																																																																								: field_177874_M != var2.field_177874_M
																																																																										? false
																																																																										: field_177873_L != var2.field_177873_L
																																																																												? false
																																																																												: field_177879_J != var2.field_177879_J
																																																																														? false
																																																																														: field_177923_u != var2.field_177923_u
																																																																																? false
																																																																																: field_177869_G != var2.field_177869_G
																																																																																		? false
																																																																																		: field_177930_am != var2.field_177930_am
																																																																																				? false
																																																																																				: field_177926_ao != var2.field_177926_ao
																																																																																						? false
																																																																																						: field_177932_an != var2.field_177932_an
																																																																																								? false
																																																																																								: field_177920_al != var2.field_177920_al
																																																																																										? false
																																																																																										: field_177887_S != var2.field_177887_S
																																																																																												? false
																																																																																												: field_177881_U != var2.field_177881_U
																																																																																														? false
																																																																																														: field_177882_T != var2.field_177882_T
																																																																																																? false
																																																																																																: field_177888_R != var2.field_177888_R
																																																																																																		? false
																																																																																																		: field_177876_O != var2.field_177876_O
																																																																																																				? false
																																																																																																				: field_177885_Q != var2.field_177885_Q
																																																																																																						? false
																																																																																																						: field_177886_P != var2.field_177886_P
																																																																																																								? false
																																																																																																								: field_177875_N != var2.field_177875_N
																																																																																																										? false
																																																																																																										: Float.compare(
																																																																																																												var2.field_177900_c,
																																																																																																												field_177900_c) != 0
																																																																																																														? false
																																																																																																														: field_177922_ai != var2.field_177922_ai
																																																																																																																? false
																																																																																																																: field_177918_ak != var2.field_177918_ak
																																																																																																																		? false
																																																																																																																		: field_177924_aj != var2.field_177924_aj
																																																																																																																				? false
																																																																																																																				: field_177939_ah != var2.field_177939_ah
																																																																																																																						? false
																																																																																																																						: field_177895_az != var2.field_177895_az
																																																																																																																								? false
																																																																																																																								: field_177897_ay != var2.field_177897_ay
																																																																																																																										? false
																																																																																																																										: field_177910_ax != var2.field_177910_ax
																																																																																																																												? false
																																																																																																																												: field_177889_aA != var2.field_177889_aA
																																																																																																																														? false
																																																																																																																														: field_177867_E != var2.field_177867_E
																																																																																																																																? false
																																																																																																																																: Float.compare(
																																																																																																																																		var2.field_177898_e,
																																																																																																																																		field_177898_e) != 0
																																																																																																																																				? false
																																																																																																																																				: Float.compare(
																																																																																																																																						var2.field_177917_i,
																																																																																																																																						field_177917_i) != 0
																																																																																																																																								? false
																																																																																																																																								: Float.compare(
																																																																																																																																										var2.field_177911_j,
																																																																																																																																										field_177911_j) != 0
																																																																																																																																												? false
																																																																																																																																												: Float.compare(
																																																																																																																																														var2.field_177913_k,
																																																																																																																																														field_177913_k) != 0
																																																																																																																																																? false
																																																																																																																																																: field_177908_aq != var2.field_177908_aq
																																																																																																																																																		? false
																																																																																																																																																		: field_177904_as != var2.field_177904_as
																																																																																																																																																				? false
																																																																																																																																																				: field_177906_ar != var2.field_177906_ar
																																																																																																																																																						? false
																																																																																																																																																						: field_177928_ap != var2.field_177928_ap
																																																																																																																																																								? false
																																																																																																																																																								: field_177878_I != var2.field_177878_I
																																																																																																																																																										? false
																																																																																																																																																										: field_177929_r != var2.field_177929_r
																																																																																																																																																												? false
																																																																																																																																																												: Float.compare(
																																																																																																																																																														var2.field_177909_m,
																																																																																																																																																														field_177909_m) != 0
																																																																																																																																																																? false
																																																																																																																																																																: Float.compare(
																																																																																																																																																																		var2.field_177896_d,
																																																																																																																																																																		field_177896_d) != 0
																																																																																																																																																																				? false
																																																																																																																																																																				: field_177927_s != var2.field_177927_s
																																																																																																																																																																						? false
																																																																																																																																																																						: field_177925_t != var2.field_177925_t
																																																																																																																																																																								? false
																																																																																																																																																																								: field_177866_D != var2.field_177866_D
																																																																																																																																																																										? false
																																																																																																																																																																										: field_177868_F != var2.field_177868_F
																																																																																																																																																																												? false
																																																																																																																																																																												: field_177944_x != var2.field_177944_x
																																																																																																																																																																														? false
																																																																																																																																																																														: field_177870_A != var2.field_177870_A
																																																																																																																																																																																? false
																																																																																																																																																																																: field_177921_v != var2.field_177921_v
																																																																																																																																																																																		? false
																																																																																																																																																																																		: field_177942_y != var2.field_177942_y
																																																																																																																																																																																				? false
																																																																																																																																																																																				: field_177940_z != var2.field_177940_z
																																																																																																																																																																																						? false
																																																																																																																																																																																						: field_177919_w != var2.field_177919_w
																																																																																																																																																																																								? false
																																																																																																																																																																																								: field_177871_B != var2.field_177871_B
																																																																																																																																																																																										? false
																																																																																																																																																																																										: field_177872_C == var2.field_177872_C;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			int var1 = field_177899_b != 0.0F ? Float.floatToIntBits(field_177899_b) : 0;
			var1 = 31 * var1 + (field_177900_c != 0.0F ? Float.floatToIntBits(field_177900_c) : 0);
			var1 = 31 * var1 + (field_177896_d != 0.0F ? Float.floatToIntBits(field_177896_d) : 0);
			var1 = 31 * var1 + (field_177898_e != 0.0F ? Float.floatToIntBits(field_177898_e) : 0);
			var1 = 31 * var1 + (field_177893_f != 0.0F ? Float.floatToIntBits(field_177893_f) : 0);
			var1 = 31 * var1 + (field_177894_g != 0.0F ? Float.floatToIntBits(field_177894_g) : 0);
			var1 = 31 * var1 + (field_177915_h != 0.0F ? Float.floatToIntBits(field_177915_h) : 0);
			var1 = 31 * var1 + (field_177917_i != 0.0F ? Float.floatToIntBits(field_177917_i) : 0);
			var1 = 31 * var1 + (field_177911_j != 0.0F ? Float.floatToIntBits(field_177911_j) : 0);
			var1 = 31 * var1 + (field_177913_k != 0.0F ? Float.floatToIntBits(field_177913_k) : 0);
			var1 = 31 * var1 + (field_177907_l != 0.0F ? Float.floatToIntBits(field_177907_l) : 0);
			var1 = 31 * var1 + (field_177909_m != 0.0F ? Float.floatToIntBits(field_177909_m) : 0);
			var1 = 31 * var1 + (field_177903_n != 0.0F ? Float.floatToIntBits(field_177903_n) : 0);
			var1 = 31 * var1 + (field_177905_o != 0.0F ? Float.floatToIntBits(field_177905_o) : 0);
			var1 = 31 * var1 + (field_177933_p != 0.0F ? Float.floatToIntBits(field_177933_p) : 0);
			var1 = 31 * var1 + (field_177931_q != 0.0F ? Float.floatToIntBits(field_177931_q) : 0);
			var1 = 31 * var1 + field_177929_r;
			var1 = 31 * var1 + (field_177927_s ? 1 : 0);
			var1 = 31 * var1 + (field_177925_t ? 1 : 0);
			var1 = 31 * var1 + field_177923_u;
			var1 = 31 * var1 + (field_177921_v ? 1 : 0);
			var1 = 31 * var1 + (field_177919_w ? 1 : 0);
			var1 = 31 * var1 + (field_177944_x ? 1 : 0);
			var1 = 31 * var1 + (field_177942_y ? 1 : 0);
			var1 = 31 * var1 + (field_177940_z ? 1 : 0);
			var1 = 31 * var1 + (field_177870_A ? 1 : 0);
			var1 = 31 * var1 + (field_177871_B ? 1 : 0);
			var1 = 31 * var1 + field_177872_C;
			var1 = 31 * var1 + (field_177866_D ? 1 : 0);
			var1 = 31 * var1 + field_177867_E;
			var1 = 31 * var1 + (field_177868_F ? 1 : 0);
			var1 = 31 * var1 + field_177869_G;
			var1 = 31 * var1 + field_177877_H;
			var1 = 31 * var1 + field_177878_I;
			var1 = 31 * var1 + field_177879_J;
			var1 = 31 * var1 + field_177880_K;
			var1 = 31 * var1 + field_177873_L;
			var1 = 31 * var1 + field_177874_M;
			var1 = 31 * var1 + field_177875_N;
			var1 = 31 * var1 + field_177876_O;
			var1 = 31 * var1 + field_177886_P;
			var1 = 31 * var1 + field_177885_Q;
			var1 = 31 * var1 + field_177888_R;
			var1 = 31 * var1 + field_177887_S;
			var1 = 31 * var1 + field_177882_T;
			var1 = 31 * var1 + field_177881_U;
			var1 = 31 * var1 + field_177884_V;
			var1 = 31 * var1 + field_177883_W;
			var1 = 31 * var1 + field_177891_X;
			var1 = 31 * var1 + field_177890_Y;
			var1 = 31 * var1 + field_177892_Z;
			var1 = 31 * var1 + field_177936_aa;
			var1 = 31 * var1 + field_177937_ab;
			var1 = 31 * var1 + field_177934_ac;
			var1 = 31 * var1 + field_177935_ad;
			var1 = 31 * var1 + field_177941_ae;
			var1 = 31 * var1 + field_177943_af;
			var1 = 31 * var1 + field_177938_ag;
			var1 = 31 * var1 + field_177939_ah;
			var1 = 31 * var1 + field_177922_ai;
			var1 = 31 * var1 + field_177924_aj;
			var1 = 31 * var1 + field_177918_ak;
			var1 = 31 * var1 + field_177920_al;
			var1 = 31 * var1 + field_177930_am;
			var1 = 31 * var1 + field_177932_an;
			var1 = 31 * var1 + field_177926_ao;
			var1 = 31 * var1 + field_177928_ap;
			var1 = 31 * var1 + field_177908_aq;
			var1 = 31 * var1 + field_177906_ar;
			var1 = 31 * var1 + field_177904_as;
			var1 = 31 * var1 + field_177902_at;
			var1 = 31 * var1 + field_177916_au;
			var1 = 31 * var1 + field_177914_av;
			var1 = 31 * var1 + field_177912_aw;
			var1 = 31 * var1 + field_177910_ax;
			var1 = 31 * var1 + field_177897_ay;
			var1 = 31 * var1 + field_177895_az;
			var1 = 31 * var1 + field_177889_aA;
			return var1;
		}

		public ChunkProviderSettings func_177864_b() {
			return new ChunkProviderSettings(this, null);
		}
	}

	public static class Serializer implements JsonDeserializer, JsonSerializer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002003";

		public ChunkProviderSettings.Factory func_177861_a(final JsonElement p_177861_1_, final Type p_177861_2_,
				final JsonDeserializationContext p_177861_3_) {
			final JsonObject var4 = p_177861_1_.getAsJsonObject();
			final ChunkProviderSettings.Factory var5 = new ChunkProviderSettings.Factory();

			try {
				var5.field_177899_b = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "coordinateScale",
						var5.field_177899_b);
				var5.field_177900_c = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "heightScale",
						var5.field_177900_c);
				var5.field_177898_e = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "lowerLimitScale",
						var5.field_177898_e);
				var5.field_177896_d = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "upperLimitScale",
						var5.field_177896_d);
				var5.field_177893_f = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleX",
						var5.field_177893_f);
				var5.field_177894_g = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleZ",
						var5.field_177894_g);
				var5.field_177915_h = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "depthNoiseScaleExponent",
						var5.field_177915_h);
				var5.field_177917_i = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleX",
						var5.field_177917_i);
				var5.field_177911_j = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleY",
						var5.field_177911_j);
				var5.field_177913_k = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "mainNoiseScaleZ",
						var5.field_177913_k);
				var5.field_177907_l = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "baseSize",
						var5.field_177907_l);
				var5.field_177909_m = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "stretchY",
						var5.field_177909_m);
				var5.field_177903_n = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeDepthWeight",
						var5.field_177903_n);
				var5.field_177905_o = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeDepthOffset",
						var5.field_177905_o);
				var5.field_177933_p = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeScaleWeight",
						var5.field_177933_p);
				var5.field_177931_q = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var4, "biomeScaleOffset",
						var5.field_177931_q);
				var5.field_177929_r = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "seaLevel",
						var5.field_177929_r);
				var5.field_177927_s = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useCaves",
						var5.field_177927_s);
				var5.field_177925_t = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useDungeons",
						var5.field_177925_t);
				var5.field_177923_u = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dungeonChance",
						var5.field_177923_u);
				var5.field_177921_v = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useStrongholds",
						var5.field_177921_v);
				var5.field_177919_w = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useVillages",
						var5.field_177919_w);
				var5.field_177944_x = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useMineShafts",
						var5.field_177944_x);
				var5.field_177942_y = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useTemples",
						var5.field_177942_y);
				var5.field_177940_z = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useMonuments",
						var5.field_177940_z);
				var5.field_177870_A = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useRavines",
						var5.field_177870_A);
				var5.field_177871_B = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useWaterLakes",
						var5.field_177871_B);
				var5.field_177872_C = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "waterLakeChance",
						var5.field_177872_C);
				var5.field_177866_D = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useLavaLakes",
						var5.field_177866_D);
				var5.field_177867_E = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lavaLakeChance",
						var5.field_177867_E);
				var5.field_177868_F = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "useLavaOceans",
						var5.field_177868_F);
				var5.field_177869_G = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "fixedBiome",
						var5.field_177869_G);

				if (var5.field_177869_G < 38 && var5.field_177869_G >= -1) {
					if (var5.field_177869_G >= BiomeGenBase.hell.biomeID) {
						var5.field_177869_G += 2;
					}
				} else {
					var5.field_177869_G = -1;
				}

				var5.field_177877_H = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "biomeSize",
						var5.field_177877_H);
				var5.field_177878_I = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "riverSize",
						var5.field_177878_I);
				var5.field_177879_J = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtSize",
						var5.field_177879_J);
				var5.field_177880_K = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtCount",
						var5.field_177880_K);
				var5.field_177873_L = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtMinHeight",
						var5.field_177873_L);
				var5.field_177874_M = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dirtMaxHeight",
						var5.field_177874_M);
				var5.field_177875_N = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelSize",
						var5.field_177875_N);
				var5.field_177876_O = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelCount",
						var5.field_177876_O);
				var5.field_177886_P = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelMinHeight",
						var5.field_177886_P);
				var5.field_177885_Q = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "gravelMaxHeight",
						var5.field_177885_Q);
				var5.field_177888_R = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteSize",
						var5.field_177888_R);
				var5.field_177887_S = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteCount",
						var5.field_177887_S);
				var5.field_177882_T = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteMinHeight",
						var5.field_177882_T);
				var5.field_177881_U = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "graniteMaxHeight",
						var5.field_177881_U);
				var5.field_177884_V = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteSize",
						var5.field_177884_V);
				var5.field_177883_W = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteCount",
						var5.field_177883_W);
				var5.field_177891_X = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteMinHeight",
						var5.field_177891_X);
				var5.field_177890_Y = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "dioriteMaxHeight",
						var5.field_177890_Y);
				var5.field_177892_Z = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteSize",
						var5.field_177892_Z);
				var5.field_177936_aa = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteCount",
						var5.field_177936_aa);
				var5.field_177937_ab = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteMinHeight",
						var5.field_177937_ab);
				var5.field_177934_ac = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "andesiteMaxHeight",
						var5.field_177934_ac);
				var5.field_177935_ad = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalSize",
						var5.field_177935_ad);
				var5.field_177941_ae = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalCount",
						var5.field_177941_ae);
				var5.field_177943_af = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalMinHeight",
						var5.field_177943_af);
				var5.field_177938_ag = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "coalMaxHeight",
						var5.field_177938_ag);
				var5.field_177939_ah = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironSize",
						var5.field_177939_ah);
				var5.field_177922_ai = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironCount",
						var5.field_177922_ai);
				var5.field_177924_aj = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironMinHeight",
						var5.field_177924_aj);
				var5.field_177918_ak = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "ironMaxHeight",
						var5.field_177918_ak);
				var5.field_177920_al = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldSize",
						var5.field_177920_al);
				var5.field_177930_am = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldCount",
						var5.field_177930_am);
				var5.field_177932_an = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldMinHeight",
						var5.field_177932_an);
				var5.field_177926_ao = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "goldMaxHeight",
						var5.field_177926_ao);
				var5.field_177928_ap = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneSize",
						var5.field_177928_ap);
				var5.field_177908_aq = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneCount",
						var5.field_177908_aq);
				var5.field_177906_ar = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneMinHeight",
						var5.field_177906_ar);
				var5.field_177904_as = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "redstoneMaxHeight",
						var5.field_177904_as);
				var5.field_177902_at = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondSize",
						var5.field_177902_at);
				var5.field_177916_au = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondCount",
						var5.field_177916_au);
				var5.field_177914_av = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondMinHeight",
						var5.field_177914_av);
				var5.field_177912_aw = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "diamondMaxHeight",
						var5.field_177912_aw);
				var5.field_177910_ax = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisSize",
						var5.field_177910_ax);
				var5.field_177897_ay = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisCount",
						var5.field_177897_ay);
				var5.field_177895_az = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisCenterHeight",
						var5.field_177895_az);
				var5.field_177889_aA = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var4, "lapisSpread",
						var5.field_177889_aA);
			} catch (final Exception var7) {
			}

			return var5;
		}

		public JsonElement func_177862_a(final ChunkProviderSettings.Factory p_177862_1_, final Type p_177862_2_,
				final JsonSerializationContext p_177862_3_) {
			final JsonObject var4 = new JsonObject();
			var4.addProperty("coordinateScale", p_177862_1_.field_177899_b);
			var4.addProperty("heightScale", p_177862_1_.field_177900_c);
			var4.addProperty("lowerLimitScale", p_177862_1_.field_177898_e);
			var4.addProperty("upperLimitScale", p_177862_1_.field_177896_d);
			var4.addProperty("depthNoiseScaleX", p_177862_1_.field_177893_f);
			var4.addProperty("depthNoiseScaleZ", p_177862_1_.field_177894_g);
			var4.addProperty("depthNoiseScaleExponent", p_177862_1_.field_177915_h);
			var4.addProperty("mainNoiseScaleX", p_177862_1_.field_177917_i);
			var4.addProperty("mainNoiseScaleY", p_177862_1_.field_177911_j);
			var4.addProperty("mainNoiseScaleZ", p_177862_1_.field_177913_k);
			var4.addProperty("baseSize", p_177862_1_.field_177907_l);
			var4.addProperty("stretchY", p_177862_1_.field_177909_m);
			var4.addProperty("biomeDepthWeight", p_177862_1_.field_177903_n);
			var4.addProperty("biomeDepthOffset", p_177862_1_.field_177905_o);
			var4.addProperty("biomeScaleWeight", p_177862_1_.field_177933_p);
			var4.addProperty("biomeScaleOffset", p_177862_1_.field_177931_q);
			var4.addProperty("seaLevel", p_177862_1_.field_177929_r);
			var4.addProperty("useCaves", p_177862_1_.field_177927_s);
			var4.addProperty("useDungeons", p_177862_1_.field_177925_t);
			var4.addProperty("dungeonChance", p_177862_1_.field_177923_u);
			var4.addProperty("useStrongholds", p_177862_1_.field_177921_v);
			var4.addProperty("useVillages", p_177862_1_.field_177919_w);
			var4.addProperty("useMineShafts", p_177862_1_.field_177944_x);
			var4.addProperty("useTemples", p_177862_1_.field_177942_y);
			var4.addProperty("useMonuments", p_177862_1_.field_177940_z);
			var4.addProperty("useRavines", p_177862_1_.field_177870_A);
			var4.addProperty("useWaterLakes", p_177862_1_.field_177871_B);
			var4.addProperty("waterLakeChance", p_177862_1_.field_177872_C);
			var4.addProperty("useLavaLakes", p_177862_1_.field_177866_D);
			var4.addProperty("lavaLakeChance", p_177862_1_.field_177867_E);
			var4.addProperty("useLavaOceans", p_177862_1_.field_177868_F);
			var4.addProperty("fixedBiome", p_177862_1_.field_177869_G);
			var4.addProperty("biomeSize", p_177862_1_.field_177877_H);
			var4.addProperty("riverSize", p_177862_1_.field_177878_I);
			var4.addProperty("dirtSize", p_177862_1_.field_177879_J);
			var4.addProperty("dirtCount", p_177862_1_.field_177880_K);
			var4.addProperty("dirtMinHeight", p_177862_1_.field_177873_L);
			var4.addProperty("dirtMaxHeight", p_177862_1_.field_177874_M);
			var4.addProperty("gravelSize", p_177862_1_.field_177875_N);
			var4.addProperty("gravelCount", p_177862_1_.field_177876_O);
			var4.addProperty("gravelMinHeight", p_177862_1_.field_177886_P);
			var4.addProperty("gravelMaxHeight", p_177862_1_.field_177885_Q);
			var4.addProperty("graniteSize", p_177862_1_.field_177888_R);
			var4.addProperty("graniteCount", p_177862_1_.field_177887_S);
			var4.addProperty("graniteMinHeight", p_177862_1_.field_177882_T);
			var4.addProperty("graniteMaxHeight", p_177862_1_.field_177881_U);
			var4.addProperty("dioriteSize", p_177862_1_.field_177884_V);
			var4.addProperty("dioriteCount", p_177862_1_.field_177883_W);
			var4.addProperty("dioriteMinHeight", p_177862_1_.field_177891_X);
			var4.addProperty("dioriteMaxHeight", p_177862_1_.field_177890_Y);
			var4.addProperty("andesiteSize", p_177862_1_.field_177892_Z);
			var4.addProperty("andesiteCount", p_177862_1_.field_177936_aa);
			var4.addProperty("andesiteMinHeight", p_177862_1_.field_177937_ab);
			var4.addProperty("andesiteMaxHeight", p_177862_1_.field_177934_ac);
			var4.addProperty("coalSize", p_177862_1_.field_177935_ad);
			var4.addProperty("coalCount", p_177862_1_.field_177941_ae);
			var4.addProperty("coalMinHeight", p_177862_1_.field_177943_af);
			var4.addProperty("coalMaxHeight", p_177862_1_.field_177938_ag);
			var4.addProperty("ironSize", p_177862_1_.field_177939_ah);
			var4.addProperty("ironCount", p_177862_1_.field_177922_ai);
			var4.addProperty("ironMinHeight", p_177862_1_.field_177924_aj);
			var4.addProperty("ironMaxHeight", p_177862_1_.field_177918_ak);
			var4.addProperty("goldSize", p_177862_1_.field_177920_al);
			var4.addProperty("goldCount", p_177862_1_.field_177930_am);
			var4.addProperty("goldMinHeight", p_177862_1_.field_177932_an);
			var4.addProperty("goldMaxHeight", p_177862_1_.field_177926_ao);
			var4.addProperty("redstoneSize", p_177862_1_.field_177928_ap);
			var4.addProperty("redstoneCount", p_177862_1_.field_177908_aq);
			var4.addProperty("redstoneMinHeight", p_177862_1_.field_177906_ar);
			var4.addProperty("redstoneMaxHeight", p_177862_1_.field_177904_as);
			var4.addProperty("diamondSize", p_177862_1_.field_177902_at);
			var4.addProperty("diamondCount", p_177862_1_.field_177916_au);
			var4.addProperty("diamondMinHeight", p_177862_1_.field_177914_av);
			var4.addProperty("diamondMaxHeight", p_177862_1_.field_177912_aw);
			var4.addProperty("lapisSize", p_177862_1_.field_177910_ax);
			var4.addProperty("lapisCount", p_177862_1_.field_177897_ay);
			var4.addProperty("lapisCenterHeight", p_177862_1_.field_177895_az);
			var4.addProperty("lapisSpread", p_177862_1_.field_177889_aA);
			return var4;
		}

		@Override
		public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
				final JsonDeserializationContext p_deserialize_3_) {
			return func_177861_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
		}

		@Override
		public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_,
				final JsonSerializationContext p_serialize_3_) {
			return func_177862_a((ChunkProviderSettings.Factory) p_serialize_1_, p_serialize_2_, p_serialize_3_);
		}
	}
}
