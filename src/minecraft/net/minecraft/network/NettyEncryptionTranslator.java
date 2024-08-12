package net.minecraft.network;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class NettyEncryptionTranslator {

public static final int EaZy = 1370;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Cipher cipher;
	private byte[] field_150505_b = new byte[0];
	private byte[] field_150506_c = new byte[0];
	// private static final String __OBFID = "http://https://fuckuskid00001237";

	protected NettyEncryptionTranslator(final Cipher cipherIn) {
		cipher = cipherIn;
	}

	private byte[] func_150502_a(final ByteBuf p_150502_1_) {
		final int var2 = p_150502_1_.readableBytes();

		if (field_150505_b.length < var2) {
			field_150505_b = new byte[var2];
		}

		p_150502_1_.readBytes(field_150505_b, 0, var2);
		return field_150505_b;
	}

	protected ByteBuf decipher(final ChannelHandlerContext ctx, final ByteBuf buffer) throws ShortBufferException {
		final int var3 = buffer.readableBytes();
		final byte[] var4 = func_150502_a(buffer);
		final ByteBuf var5 = ctx.alloc().heapBuffer(cipher.getOutputSize(var3));
		var5.writerIndex(cipher.update(var4, 0, var3, var5.array(), var5.arrayOffset()));
		return var5;
	}

	protected void cipher(final ByteBuf p_150504_1_, final ByteBuf p_150504_2_) throws ShortBufferException {
		final int var3 = p_150504_1_.readableBytes();
		final byte[] var4 = func_150502_a(p_150504_1_);
		final int var5 = cipher.getOutputSize(var3);

		if (field_150506_c.length < var5) {
			field_150506_c = new byte[var5];
		}

		p_150504_2_.writeBytes(field_150506_c, 0, cipher.update(var4, 0, var3, field_150506_c));
	}
}
