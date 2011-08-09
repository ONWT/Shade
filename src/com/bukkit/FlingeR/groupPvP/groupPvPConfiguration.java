package com.bukkit.FlingeR.groupPvP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

/**
 * CraftRepo weathercontrol for Bukkit
 * @author AllGamer
 * 
 * Copyright 2011 AllGamer, LLC.
 * See LICENSE for licensing information.
 */

public class groupPvPConfiguration 
{

	private groupPvP plugin;
	private File folder;
	private final Logger log = Logger.getLogger("Minecraft");
	private String logPrefix;

	public groupPvPConfiguration(File folder, groupPvP instance) 
	{
		this.folder = folder;
		this.plugin = instance;
		this.logPrefix = groupPvP.logPrefix;
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
					out.write("#attaking:\n");
					out.write("#- cant_attack_group");
					out.close();
					fstream.close();
					log.info(logPrefix + " Make sure to edit your config file!");
				
				}
			catch (IOException ex) 
				{
					log.severe(logPrefix + " Error creating default Configuration File");
					log.severe(logPrefix + " " + ex);
					this.plugin.getServer().getPluginManager().disablePlugin((Plugin) this);
				}
		}
		
	}
}

