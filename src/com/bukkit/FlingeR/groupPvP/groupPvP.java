package com.bukkit.FlingeR.groupPvP;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;

public class groupPvP extends JavaPlugin {
    private static Server Server = null;
    public static HashMap<String, String> pconfig = new HashMap<String, String>();
    public static PermissionHandler Permissions;
    
    private final groupPvPEntityListener entityListener = new groupPvPEntityListener(this);
 
    
    public void onEnable() {
    	
    File yml = new File(getDataFolder()+"/config.yml");
    
    if (!yml.exists()) {
        new File(getDataFolder().toString()).mkdir();
    try {
    yml.createNewFile();
    }
    catch (IOException ex) {
    System.out.println("cannot create file "+yml.getPath());
    }
       }  
    

	pconfig.put("deny-attack", getConfiguration().getString("deny-attack", "- You are not allowed to attack other players."));
	pconfig.put("deny-protected", getConfiguration().getString("deny-protected", "- you can not attack %d, his group is protected."));
	pconfig.put("deny-own-group", getConfiguration().getString("deny-own-group", "- You are not allowed to attack members of your own group."));
     getConfiguration();
     Server = getServer();
     PluginManager pm = getServer().getPluginManager();
        if (!(new File(getDataFolder(), "config.yml")).exists()) {
         defaultConfig();
        }  
        loadConfig();
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
        setupPermissions();
        System.out.println("[groupPvP] Successfully loaded.");

    }
    
    public void onDisable() {
     System.out.println("");
    }
    
    private void loadConfig() {
    }

    private void defaultConfig() {
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
    
