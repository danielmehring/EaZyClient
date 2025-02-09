package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

public enum EnumFaceing {
	DOWN("DOWN", 0, new EnumFaceing.VertexInformation[] {
			new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f,
					EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179181_a, null),
			new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f,
					EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179177_d, null),
			new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c,
					EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179177_d, null),
			new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c,
					EnumFaceing.Constants.field_179178_e, EnumFaceing.Constants.field_179181_a,
					null) }), UP("UP", 1, new EnumFaceing.VertexInformation[] {
							new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f,
									EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179177_d, null),
							new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f,
									EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179181_a, null),
							new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c,
									EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179181_a, null),
							new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c,
									EnumFaceing.Constants.field_179179_b, EnumFaceing.Constants.field_179177_d,
									null) }), NORTH("NORTH", 2, new EnumFaceing.VertexInformation[] {
											new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c,
													EnumFaceing.Constants.field_179179_b,
													EnumFaceing.Constants.field_179177_d, null),
											new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179180_c,
													EnumFaceing.Constants.field_179178_e,
													EnumFaceing.Constants.field_179177_d, null),
											new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f,
													EnumFaceing.Constants.field_179178_e,
													EnumFaceing.Constants.field_179177_d, null),
											new EnumFaceing.VertexInformation(EnumFaceing.Constants.field_179176_f,
													EnumFaceing.Constants.field_179179_b,
													EnumFaceing.Constants.field_179177_d,
													null) }), SOUTH("SOUTH", 3, new EnumFaceing.VertexInformation[] {
															new EnumFaceing.VertexInformation(
																	EnumFaceing.Constants.field_179176_f,
																	EnumFaceing.Constants.field_179179_b,
																	EnumFaceing.Constants.field_179181_a, null),
															new EnumFaceing.VertexInformation(
																	EnumFaceing.Constants.field_179176_f,
																	EnumFaceing.Constants.field_179178_e,
																	EnumFaceing.Constants.field_179181_a, null),
															new EnumFaceing.VertexInformation(
																	EnumFaceing.Constants.field_179180_c,
																	EnumFaceing.Constants.field_179178_e,
																	EnumFaceing.Constants.field_179181_a, null),
															new EnumFaceing.VertexInformation(
																	EnumFaceing.Constants.field_179180_c,
																	EnumFaceing.Constants.field_179179_b,
																	EnumFaceing.Constants.field_179181_a,
																	null) }), WEST("WEST",
																			4,
																			new EnumFaceing.VertexInformation[] {
																					new EnumFaceing.VertexInformation(
																							EnumFaceing.Constants.field_179176_f,
																							EnumFaceing.Constants.field_179179_b,
																							EnumFaceing.Constants.field_179177_d,
																							null),
																					new EnumFaceing.VertexInformation(
																							EnumFaceing.Constants.field_179176_f,
																							EnumFaceing.Constants.field_179178_e,
																							EnumFaceing.Constants.field_179177_d,
																							null),
																					new EnumFaceing.VertexInformation(
																							EnumFaceing.Constants.field_179176_f,
																							EnumFaceing.Constants.field_179178_e,
																							EnumFaceing.Constants.field_179181_a,
																							null),
																					new EnumFaceing.VertexInformation(
																							EnumFaceing.Constants.field_179176_f,
																							EnumFaceing.Constants.field_179179_b,
																							EnumFaceing.Constants.field_179181_a,
																							null) }), EAST("EAST",
																									5,
																									new EnumFaceing.VertexInformation[] {
																											new EnumFaceing.VertexInformation(
																													EnumFaceing.Constants.field_179180_c,
																													EnumFaceing.Constants.field_179179_b,
																													EnumFaceing.Constants.field_179181_a,
																													null),
																											new EnumFaceing.VertexInformation(
																													EnumFaceing.Constants.field_179180_c,
																													EnumFaceing.Constants.field_179178_e,
																													EnumFaceing.Constants.field_179181_a,
																													null),
																											new EnumFaceing.VertexInformation(
																													EnumFaceing.Constants.field_179180_c,
																													EnumFaceing.Constants.field_179178_e,
																													EnumFaceing.Constants.field_179177_d,
																													null),
																											new EnumFaceing.VertexInformation(
																													EnumFaceing.Constants.field_179180_c,
																													EnumFaceing.Constants.field_179179_b,
																													EnumFaceing.Constants.field_179177_d,
																													null) });
	private static final EnumFaceing[] field_179029_g = new EnumFaceing[6];
	private final EnumFaceing.VertexInformation[] field_179035_h;

	public static EnumFaceing func_179027_a(final EnumFacing p_179027_0_) {
		return field_179029_g[p_179027_0_.getIndex()];
	}

	private EnumFaceing(final String p_i46272_1_, final int p_i46272_2_,
			final EnumFaceing.VertexInformation... p_i46272_3_) {
		field_179035_h = p_i46272_3_;
	}

	public EnumFaceing.VertexInformation func_179025_a(final int p_179025_1_) {
		return field_179035_h[p_179025_1_];
	}

	static {
		field_179029_g[EnumFaceing.Constants.field_179178_e] = DOWN;
		field_179029_g[EnumFaceing.Constants.field_179179_b] = UP;
		field_179029_g[EnumFaceing.Constants.field_179177_d] = NORTH;
		field_179029_g[EnumFaceing.Constants.field_179181_a] = SOUTH;
		field_179029_g[EnumFaceing.Constants.field_179176_f] = WEST;
		field_179029_g[EnumFaceing.Constants.field_179180_c] = EAST;
	}

	public static final class Constants {
		public static final int field_179181_a = EnumFacing.SOUTH.getIndex();
		public static final int field_179179_b = EnumFacing.UP.getIndex();
		public static final int field_179180_c = EnumFacing.EAST.getIndex();
		public static final int field_179177_d = EnumFacing.NORTH.getIndex();
		public static final int field_179178_e = EnumFacing.DOWN.getIndex();
		public static final int field_179176_f = EnumFacing.WEST.getIndex();
	}

	public static class VertexInformation {
		public final int field_179184_a;
		public final int field_179182_b;
		public final int field_179183_c;

		private VertexInformation(final int p_i46270_1_, final int p_i46270_2_, final int p_i46270_3_) {
			field_179184_a = p_i46270_1_;
			field_179182_b = p_i46270_2_;
			field_179183_c = p_i46270_3_;
		}

		VertexInformation(final int p_i46271_1_, final int p_i46271_2_, final int p_i46271_3_,
				final Object p_i46271_4_) {
			this(p_i46271_1_, p_i46271_2_, p_i46271_3_);
		}
	}
}
