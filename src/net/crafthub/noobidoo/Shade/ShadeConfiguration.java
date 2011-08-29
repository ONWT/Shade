package net.crafthub.noobidoo.Shade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

/**
 * Crafthub Shade for Bukkit
 * @author Carfthub
 * 
 * Copyright 2011 AllGamer, LLC.
 * See LICENSE for licensing information.
 */

public class ShadeConfiguration 
{

	private Shade plugin;
	private File folder;
	private final Logger log = Logger.getLogger("Minecraft");
	private String logPrefix;

	public ShadeConfiguration(File folder, Shade instance) 
	{
		this.folder = folder;
		this.plugin = instance;
		this.logPrefix = Shade.logPrefix;
	}

	public void setupConfigs() 
	{
		File config = new File(this.folder, "config.yml");
		if (!config.exists()) 
		{
			try 
				{
					log.info(logPrefix + " - Creating config directory... ");
					log.info(logPrefix + " - Creating config files... ");
					config.createNewFile();
					FileWriter fstream = new FileWriter(config);
					BufferedWriter out = new BufferedWriter(fstream);

					out.write("#groupPvP config file\n");
					out.write("#anti attack:\n");
					out.write("#  attacking:\n");
					out.write("#  - cant_attack_group");
					out.write("#anti target:\n");
					out.write("#  - untargetable_group:\n");
					out.close();
					fstream.close();
					log.info(logPrefix + " Make sure to edit your config file!");
				
				}
			catch (IOException ex) 
				{
					log.severe(logPrefix + " Error creating default Configuration File");
					log.severe(logPrefix + " " + ex);
					this.plugin.getServer().getPluginManager().disablePlugin((Plugin) plugin);
				}
		}
		
	}
}

