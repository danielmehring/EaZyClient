package me.EaZy.client.utils.holoimage;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;

public class ImageStand {

	public static final int EaZy = 2060;

	private static final char TRANSPARENT_CHAR = ' ';
	private final Color[] colors;
	private String[] lines;
	private int i;

	public ImageStand(final BufferedImage image, final int width, final int height, final char imgChar) {
		this.colors = new Color[] { new Color(0, 0, 0), new Color(0, 0, 170), new Color(0, 170, 0),
				new Color(0, 170, 170), new Color(170, 0, 0), new Color(170, 0, 170), new Color(255, 170, 0),
				new Color(170, 170, 170), new Color(85, 85, 85), new Color(85, 85, 255), new Color(85, 255, 85),
				new Color(85, 255, 255), new Color(255, 85, 85), new Color(255, 85, 255), new Color(255, 255, 85),
				new Color(255, 255, 255) };
		this.i = 0;
		try {
			final ChatColor[][] chatColors = this.toChatColorArray(image, width, height);
			this.lines = this.toImgMessage(chatColors, imgChar);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private ChatColor[][] toChatColorArray(final BufferedImage image, final int width, final int height) {
		final BufferedImage resized = this.resizeImage(image, width, height);
		final ChatColor[][] chatImg = new ChatColor[resized.getWidth()][resized.getHeight()];
		for (int x = 0; x < resized.getWidth(); ++x) {
			for (int y = 0; y < resized.getHeight(); ++y) {
				final int rgb = resized.getRGB(x, y);
				final ChatColor closest = this.getClosestChatColor(new Color(rgb, true));
				chatImg[x][y] = closest;
			}
		}
		return chatImg;
	}

	private String[] toImgMessage(final ChatColor[][] colors, final char imgchar) {
		final String[] lines = new String[colors[0].length];
		for (int y = 0; y < colors[0].length; ++y) {
			String line = "";
			for (int x = 0; x < colors.length; ++x) {
				final ChatColor color = colors[x][y];
				line = String.valueOf(line)
						+ ((color != null) ? (String.valueOf(colors[x][y].toString()) + imgchar) : ' ');
			}
			lines[y] = String.valueOf(line) + ChatColor.RESET;
		}
		return lines;
	}

	private BufferedImage resizeImage(final BufferedImage originalImage, final int width, final int height) {
		final AffineTransform af = new AffineTransform();
		double newWidth = (double) width / originalImage.getWidth();
		double newHeight = (double) height / originalImage.getHeight();

		af.scale(newWidth, newHeight);
		final AffineTransformOp operation = new AffineTransformOp(af, 1);
		return operation.filter(originalImage, null);
	}

	private double getDistance(final Color c1, final Color c2) {
		final double rmean = (c1.getRed() + c2.getRed()) / 2.0;
		final double r = c1.getRed() - c2.getRed();
		final double g = c1.getGreen() - c2.getGreen();
		final int b = c1.getBlue() - c2.getBlue();
		final double weightR = 2.0 + rmean / 256.0;
		final double weightG = 4.0;
		final double weightB = 2.0 + (255.0 - rmean) / 256.0;
		return weightR * r * r + weightG * g * g + weightB * b * b;
	}

	private boolean areIdentical(final Color c1, final Color c2) {
		return Math.abs(c1.getRed() - c2.getRed()) <= 5 && Math.abs(c1.getGreen() - c2.getGreen()) <= 5
				&& Math.abs(c1.getBlue() - c2.getBlue()) <= 5;
	}

	private ChatColor getClosestChatColor(final Color color) {
		if (color.getAlpha() < 128) {
			return null;
		}
		int index = 0;
		double best = -1.0;
		for (int i = 0; i < this.colors.length; ++i) {
			if (this.areIdentical(this.colors[i], color)) {
				return ChatColor.values()[i];
			}
		}
		for (int i = 0; i < this.colors.length; ++i) {
			final double distance = this.getDistance(color, this.colors[i]);
			if (distance < best || best == -1.0) {
				best = distance;
				index = i;
			}
		}
		return ChatColor.values()[index];
	}

	private String center(final String s, final int length) {
		if (s.length() > length) {
			return s.substring(0, length);
		}
		if (s.length() == length) {
			return s;
		}
		final int leftPadding = (length - s.length()) / 2;
		final StringBuilder leftBuilder = new StringBuilder();
		for (int i = 0; i < leftPadding; ++i) {
			leftBuilder.append(" ");
		}
		return String.valueOf(leftBuilder.toString()) + s;
	}

	public String[] getLines() {
		return this.lines;
	}

	public ItemStack[] getArmorStands(final double x, final double y, final double z) {
		final ItemStack[] itemStacks = new ItemStack[this.getLines().length];
		final List<String> lines = Arrays.asList(this.getLines());
		Collections.reverse(lines);
		for (int i = 0; i < this.getLines().length; ++i) {
			final ItemStack item = new ItemStack(Items.armor_stand);
			final NBTTagCompound base = new NBTTagCompound();
			final NBTTagCompound entityTag = new NBTTagCompound();
			final NBTTagList pos = new NBTTagList();
			pos.appendTag(new NBTTagDouble(x));
			pos.appendTag(new NBTTagDouble(y + 0.2 * i));
			pos.appendTag(new NBTTagDouble(z));
			entityTag.setTag("Pos", pos);
			entityTag.setString("CustomName", this.getLines()[i]);
			entityTag.setInteger("CustomNameVisible", 1);
			entityTag.setInteger("Invisible", 1);
			entityTag.setInteger("NoGravity", 1);
			base.setTag("EntityTag", entityTag);
			item.setTagCompound(base);
			item.setStackDisplayName("ImageStand #" + i);
			itemStacks[i] = item;
		}
		return itemStacks;
	}
}
