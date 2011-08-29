package net.crafthub.noobidoo.Shade;

/**
 * Crafthub Shade for Bukkit
 * @author Carfthub
 * 
 * Copyright 2011 AllGamer, LLC.
 * See LICENSE for licensing information.
 */

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

public class Shade extends JavaPlugin {
	private static Server Server = null;
	public static HashMap<String, String> pconfig = new HashMap<String, String>();
	public static PermissionHandler Permissions;
	public static Configuration config;
	private ShadeConfiguration confSetup;
	public static String logPrefix = "[Shade]";

	private final ShadeEntityListener entityListener = new ShadeEntityListener(this);


	public void configInit()
	{
		getDataFolder().mkdirs();
		config = new Configuration(new File(this.getDataFolder(), "config.yml"));
		confSetup = new ShadeConfiguration(this.getDataFolder(), this);
	}
	
	public void onEnable() {
		configInit();
		confSetup.setupConfigs();
		config.load();

		Server = getServer();
		PluginManager pm = getServer().getPluginManager();

		if(Shade.config.getNode("anti attack") != null) // Enable anti attack
			pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
		if(Shade.config.getStringList("anti target", null) != null) // Enable anti target
			pm.registerEvent(Event.Type.ENTITY_TARGET, entityListener, Event.Priority.Normal, this);
		setupPermissions();
		System.out.println("[groupPvP] Successfully loaded.");

	}

	public void onDisable() {
		System.out.println("");
	}

	private void setupPermissions() {
		Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

		if (Permissions == null) {
			if (permissions != null) {
				Permissions = ((Permissions)permissions).getHandler();
			} else {
				System.out.println("Permission system not detected, defaulting to OP");
			}
		}
	}

	public static boolean hasPermissions(Player p, String s) {
		if (Permissions != null) {
			return Permissions.has(p, s);
		} else {
			return p.isOp();
		}
	}

	public static Server getBukkitServer() {
		return Server;
	}


}

