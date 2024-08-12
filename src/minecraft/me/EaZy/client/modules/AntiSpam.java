package me.EaZy.client.modules;

import java.util.List;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.ChatInputEvent;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiMainMenu;

public class AntiSpam extends Module {

public static AntiSpam mod;


public static final int EaZy = 93;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public AntiSpam() {
		super(new String(
                        new byte[] { 0b1000001, 0b1101110, 0b1110100, 0b1101001, 0b1010011, 0b1110000, 0b1100001, 0b1101101 }),
				0, new String(new byte[] { 0b1001110, 0b1101111, 0b1010011, 0b1110000, 0b1100001, 0b1101101 }),
				Category.OTHER,
                                new String(new byte[] { 0b1000010, 0b1101100, 0b1101111, 0b1100011, 0b1101011, 0b1110011, 0b100000,
                                    0b1110010, 0b1100101, 0b1110000, 0b1100101, 0b1100001, 0b1110100, 0b1101001, 0b1101110,
                                    0b1100111 }) + "\n"
						+ new String(new byte[] { 0b1101101, 0b1100101, 0b1110011, 0b1110011, 0b1100001, 0b1100111,
                                                    0b1100101, 0b1110011, 0b101110 }));
		mod = this;
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "KontraSpam";
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

		super.onUpdate();
	}

	public EventTarget onReceivedMessage(final ChatInputEvent event) {
		final List<ChatLine> chatLines = event.getChatLines();
		new Thread(() -> {
                    try {
                        Thread.sleep(50);
                        if (chatLines.size() > 1) {
                            int i = chatLines.size() - 1;
                            while (i >= 1) {
                                int i2 = i - 1;
                                while (i2 >= 0) {
                                    if (chatLines.size() > i && chatLines.get(i).getChatComponent().getUnformattedText()
                                            .startsWith(chatLines.get(i2).getChatComponent().getUnformattedText())) {
                                        if (chatLines.get(i).getChatComponent().getUnformattedText().endsWith("]")
                                                && chatLines.get(i).getChatComponent().getUnformattedText()
                                                        .contains(" [x")) {
                                            final int numberIndex1 = chatLines.get(i).getChatComponent()
                                                    .getUnformattedText().lastIndexOf(" [x") + 3;
                                            final int numberIndex2 = chatLines.get(i).getChatComponent()
                                                    .getUnformattedText().length() - 1;
                                            final int number = Integer.valueOf(chatLines.get(i).getChatComponent()
                                                    .getUnformattedText().substring(numberIndex1, numberIndex2));
                                            chatLines.get(i2).getChatComponent().appendText(" [x" + (number + 1) + "]");
                                        } else {
                                            chatLines.get(i2).getChatComponent().appendText(" [x2]");
                                        }
                                        chatLines.remove(i);
                                    }
                                    --i2;
                                }
                                --i;
                            }
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }, new String(
                        new byte[] { 0b1000001, 0b1101110, 0b1110100, 0b1101001, 0b1010011, 0b1110000, 0b1100001, 0b1101101 })).start();
		return null;
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		super.onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
}
