package net.minecraft.client.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;

public class NetHandlerLoginClient implements INetHandlerLoginClient {

	public static final int EaZy = 629;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger logger = LogManager.getLogger();
	private final Minecraft mc;
	private final GuiScreen prevScreen;
	private final NetworkManager networkManager;
	private GameProfile field_175091_e;

	public NetHandlerLoginClient(final NetworkManager p_i45059_1_, final Minecraft mcIn, final GuiScreen p_i45059_3_) {
		networkManager = p_i45059_1_;
		mc = mcIn;
		prevScreen = p_i45059_3_;
	}

	@Override
	public void handleEncryptionRequest(S01PacketEncryptionRequest packetIn) {
		final SecretKey secretkey = CryptManager.createNewSharedKey();
		String s = packetIn.getServerId();
		PublicKey publickey = packetIn.getPublicKey();
		String s1 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);
		if (Client.mcLeaks) {
			JsonObject obj = new JsonObject();
			obj.addProperty("session", mc.getSession().token);
			obj.addProperty("mcname", mc.getSession().username);
			obj.addProperty("serverhash", s1);
			obj.addProperty("server", mc.getCurrentServerData().serverIP);

			try {
				HttpURLConnection con = (HttpURLConnection) new URL("http://auth.mcleaks.net/v1/joinserver")
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				reader.readLine();
				reader.close();

			} catch (Exception ex) {

			}
		} else {
			try {
				this.getSessionService().joinServer(mc.getSession().getProfile(), mc.getSession().getToken(), s1);
			} catch (AuthenticationUnavailableException var7) {
				networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo",
						new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable",
								new Object[0]) }));
				return;
			} catch (InvalidCredentialsException var8) {
				networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] {
						new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0]) }));
				return;
			} catch (AuthenticationException var9) {
				networkManager.closeChannel(
						new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { var9.getMessage() }));
				return;
			}
		}

		this.networkManager.sendPacket(new C01PacketEncryptionResponse(secretkey, publickey, packetIn.getVerifyToken()),
				new GenericFutureListener<Future<? super Void>>() {
					public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
						NetHandlerLoginClient.this.networkManager.enableEncryption(secretkey);
					}
				}, new GenericFutureListener[0]);
	}

	private MinecraftSessionService getSessionService() {
		return mc.getSessionService();
	}

	@Override
	public void handleLoginSuccess(final S02PacketLoginSuccess packetIn) {
		field_175091_e = packetIn.func_179730_a();
		networkManager.setConnectionState(EnumConnectionState.PLAY);
		networkManager.setNetHandler(new NetHandlerPlayClient(mc, prevScreen, networkManager, field_175091_e));
	}

	/**
	 * Invoked when disconnecting, the parameter is a ChatComponent describing
	 * the reason for termination
	 */
	@Override
	public void onDisconnect(final IChatComponent reason) {
		mc.displayGuiScreen(new GuiDisconnected(prevScreen, "connect.failed", reason));
	}

	@Override
	public void handleDisconnect(final S00PacketDisconnect packetIn) {
		networkManager.closeChannel(packetIn.func_149603_c());
	}

	@Override
	public void func_180464_a(final S03PacketEnableCompression p_180464_1_) {
		if (!networkManager.isLocalChannel()) {
			networkManager.setCompressionTreshold(p_180464_1_.func_179731_a());
		}
	}
}
