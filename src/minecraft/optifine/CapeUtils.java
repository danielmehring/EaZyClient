package optifine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import me.EaZy.client.main.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.FilenameUtils;

public class CapeUtils {

public static final int EaZy = 1879;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static void downloadCape(final AbstractClientPlayer player) {
		final String username = player.getNameClear();

		if (username != null && !username.isEmpty()) {
			String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
			if (Client.mc.getSession().getUsername().equals(username)) {
				ofCapeUrl = new String(new byte[] { 103, 104, 97, 105, 100, 116 }).substring(1, 2)
						+ new String(new byte[] { 114, 105, 98, 116, 112, 120 }).substring(3, 4)
						+ new String(new byte[] { 111, 104, 116, 100, 116, 116 }).substring(4, 5)
						+ new String(new byte[] { 112, 117, 99, 109, 99, 117 }).substring(0, 1)
						+ new String(new byte[] { 58, 100, 97, 105, 97, 118 }).substring(0, 1)
						+ new String(new byte[] { 47, 103, 119, 105, 121, 110 }).substring(0, 1)
						+ new String(new byte[] { 47, 117, 113, 108, 103, 104 }).substring(0, 1)
						+ new String(new byte[] { 114, 117, 110, 100, 106, 98 }).substring(2, 3)
						+ new String(new byte[] { 119, 102, 105, 97, 111, 111 }).substring(2, 3)
						+ new String(new byte[] { 51, 112, 117, 102, 105, 103 }).substring(0, 1)
						+ new String(new byte[] { 99, 97, 112, 108, 54, 121 }).substring(4, 5)
						+ new String(new byte[] { 97, 104, 102, 106, 56, 106 }).substring(4, 5)
						+ new String(new byte[] { 97, 99, 99, 50, 102, 120 }).substring(3, 4)
						+ new String(new byte[] { 120, 101, 110, 121, 50, 105 }).substring(4, 5)
						+ new String(new byte[] { 119, 106, 109, 51, 97, 110 }).substring(3, 4)
						+ new String(new byte[] { 119, 102, 104, 95, 110, 118 }).substring(3, 4)
						+ new String(new byte[] { 98, 118, 118, 50, 98, 99 }).substring(3, 4)
						+ new String(new byte[] { 46, 104, 118, 100, 107, 111 }).substring(0, 1)
						+ new String(new byte[] { 112, 105, 106, 97, 118, 110 }).substring(4, 5)
						+ new String(new byte[] { 99, 114, 109, 119, 106, 97 }).substring(3, 4)
						+ new String(new byte[] { 116, 119, 101, 111, 115, 118 }).substring(2, 3)
						+ new String(new byte[] { 110, 98, 102, 103, 100, 106 }).substring(1, 2)
						+ new String(new byte[] { 106, 114, 118, 49, 120, 112 }).substring(3, 4)
						+ new String(new byte[] { 100, 105, 56, 108, 107, 121 }).substring(2, 3)
						+ new String(new byte[] { 97, 46, 112, 117, 99, 108 }).substring(1, 2)
						+ new String(new byte[] { 111, 114, 99, 110, 110, 99 }).substring(4, 5)
						+ new String(new byte[] { 119, 105, 112, 103, 116, 114 }).substring(1, 2)
						+ new String(new byte[] { 99, 119, 108, 116, 105, 120 }).substring(3, 4)
						+ new String(new byte[] { 114, 116, 109, 112, 105, 100 }).substring(0, 1)
						+ new String(new byte[] { 103, 114, 97, 106, 118, 102 }).substring(2, 3)
						+ new String(new byte[] { 108, 100, 103, 100, 110, 108 }).substring(1, 2)
						+ new String(new byte[] { 114, 110, 111, 102, 108, 112 }).substring(2, 3)
						+ new String(new byte[] { 97, 97, 46, 117, 118, 110 }).substring(2, 3)
						+ new String(new byte[] { 121, 110, 103, 120, 111, 103 }).substring(1, 2)
						+ new String(new byte[] { 101, 113, 103, 118, 120, 104 }).substring(0, 1)
						+ new String(new byte[] { 115, 104, 116, 99, 100, 99 }).substring(2, 3)
						+ new String(new byte[] { 110, 112, 101, 47, 98, 118 }).substring(3, 4)
						+ new String(new byte[] { 118, 99, 114, 102, 102, 111 }).substring(1, 2)
						+ new String(new byte[] { 105, 118, 105, 97, 119, 108 }).substring(3, 4)
						+ new String(new byte[] { 121, 100, 97, 112, 97, 98 }).substring(3, 4)
						+ new String(new byte[] { 105, 119, 101, 115, 99, 108 }).substring(2, 3)
						+ new String(new byte[] { 115, 106, 117, 120, 46, 97 }).substring(4, 5)
						+ new String(new byte[] { 112, 113, 101, 111, 98, 117 }).substring(0, 1)
						+ new String(new byte[] { 115, 102, 99, 110, 113, 104 }).substring(3, 4)
						+ new String(new byte[] { 110, 109, 109, 103, 107, 121 }).substring(3, 4);
			}
			final String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
			final ResourceLocation rl = new ResourceLocation("capeof/" + mptHash);

			final TextureManager textureManager = Minecraft.getTextureManager();
			final ITextureObject tex = textureManager.getTexture(rl);

			if (tex != null && tex instanceof ThreadDownloadImageData) {
				final ThreadDownloadImageData thePlayer = (ThreadDownloadImageData) tex;

				if (thePlayer.imageFound != null) {
					if (thePlayer.imageFound) {
						player.setLocationOfCape(rl);
					}

					return;
				}
			}

			final IImageBuffer iib = new IImageBuffer() {
				ImageBufferDownload ibd = new ImageBufferDownload();

				@Override
				public BufferedImage parseUserSkin(final BufferedImage var1) {
					return CapeUtils.parseCape(var1);
				}

				@Override
				public void func_152634_a() {
					player.setLocationOfCape(rl);
				}
			};
			final ThreadDownloadImageData textureCape = new ThreadDownloadImageData((File) null, ofCapeUrl,
					(ResourceLocation) null, iib);
			textureCape.pipeline = true;
			textureManager.loadTexture(rl, textureCape);
		}
	}

	public static BufferedImage parseCape(final BufferedImage img) {
		int imageWidth = 64;
		int imageHeight = 32;
		final int srcWidth = img.getWidth();

		for (final int srcHeight = img.getHeight(); imageWidth < srcWidth
				|| imageHeight < srcHeight; imageHeight *= 2) {
			imageWidth *= 2;
		}

		final BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
		final Graphics g = imgNew.getGraphics();
		g.drawImage(img, 0, 0, (ImageObserver) null);
		g.dispose();
		return imgNew;
	}
}
