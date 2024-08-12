package me.EaZy.client.alts;

import me.EaZy.client.main.Client;
import me.EaZy.client.utils.MojangUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.net.Proxy;
import java.util.UUID;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

public class LoginManager {

	public static final int EaZy = 32;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	public static String login(final String email, final String password) {
		Client.mcLeaks = false;
		final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(
				Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
		auth.setUsername(email);
		auth.setPassword(password);
		try {
			auth.logIn();
			Minecraft.session = new Session(auth.getSelectedProfile().getName(),
					auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
			return "";
		} catch (final AuthenticationUnavailableException e) {
			return "§4§lCannot contact authentication server!";
		} catch (final AuthenticationException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid username or password.")
					|| e.getMessage().toLowerCase().contains("account migrated")) {
				return "§4§lWrong password!";
			}
			return "§4§lCannot contact authentication server!";
		} catch (final NullPointerException e) {
			return "§4§lWrong password!";
		}
	}

	public static void changeCrackedName(final String newName) {
		Client.mcLeaks = false;
		final UUID uuid = MojangUtils.getUUID(newName);

		Minecraft.session = new Session(newName, uuid == null ? "" : uuid.toString(),
				uuid == null ? "" : uuid.toString().replace("-", ""), "mojang");
	}
}
