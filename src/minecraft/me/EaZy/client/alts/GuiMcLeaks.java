package me.EaZy.client.alts;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.lwjgl.input.Keyboard;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.EaZy.client.main.Client;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiMcLeaks extends GuiScreen {
	GuiScreen parent;

	private GuiTextField token;

	public GuiMcLeaks(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.token = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 40, 200, 20);
		this.token.setMaxStringLength(20);
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 - 40 + 29, "§aUse Token"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 - 40 + 57, "§cExit"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 2) {
			this.mc.displayGuiScreen(parent);
		}
		if (button.id == 1) {
			String input = this.token.getText();
			if (!input.trim().isEmpty()) {
				JsonObject obj = new JsonObject();
				obj.addProperty("token", input);

				HttpURLConnection con = (HttpURLConnection) new URL("http://auth.mcleaks.net/v1/redeem")
						.openConnection();
				con.setConnectTimeout(15000);
				con.setReadTimeout(15000);
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				con.setRequestProperty("Content-Type", "application/json");
				String rawData = obj.toString();
				byte[] paramAsBytes = rawData.getBytes(Charset.forName("UTF-8"));
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Length", String.valueOf(paramAsBytes.length));
				DataOutputStream writer = new DataOutputStream(con.getOutputStream());
				writer.write(paramAsBytes);
				writer.flush();
				writer.close();

				if (con.getResponseCode() == 200) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String response = reader.readLine();
					reader.close();
					JsonParser parser = new JsonParser();
					JsonObject mainObj = (JsonObject) parser.parse(response);

					String token = null;
					String user = null;

					try {
						token = ((JsonObject) mainObj.get("result")).get("session").getAsString();
						user = ((JsonObject) mainObj.get("result")).get("mcname").getAsString();
					} catch (NullPointerException ex) {
						return;
					}

					this.mc.session.token = token;
					this.mc.session.username = user;
					Client.mcLeaks = true;
					this.mc.displayGuiScreen(new GuiMainMenu());
				}
			}
		}
	}

	@Override
	public void updateScreen() {
		this.token.updateCursorCounter();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground(mouseX, mouseY);
		this.token.drawTextBox();
		this.fontRendererObj.drawString("MCLeaks-Token:",
				this.token.xPosition - this.fontRendererObj.getStringWidth("MCLeaks-Token: "), this.token.yPosition + 5,
				0xFFFFFFFF);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.token.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.token.textboxKeyTyped(typedChar, keyCode);
	}
}
