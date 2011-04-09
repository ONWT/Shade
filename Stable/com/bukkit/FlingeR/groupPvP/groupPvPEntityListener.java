package com.bukkit.FlingeR.groupPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
public class groupPvPEntityListener extends EntityListener {
    private final groupPvP plugin;
    public groupPvPEntityListener(groupPvP instance) {
        plugin = instance;
    }
    
	public void onEntityDamage (EntityDamageEvent event) {
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent entEvent = (EntityDamageByEntityEvent)event;
			if ( (entEvent.getDamager() instanceof Player) && (entEvent.getEntity() instanceof Player) ) {
				Player attacker = (Player)entEvent.getDamager();
				Player defender = (Player)entEvent.getEntity();
				String world = attacker.getWorld().toString();
				world = world.replace("CraftWorld{name=", "");
				world = world.replace("}", "");
	    		if (groupPvP.hasPermissions(attacker, "groupPvP.denyattack")) {
				event.setCancelled(true);
				String Attacker=null;
				String DG=groupPvP.Permissions.getGroup(world,defender.getName());
	    		Attacker = groupPvP.pconfig.get("deny-attack").replace("%d", defender.getName());
	    		Attacker = Attacker.replace("%g", DG);
				attacker.sendMessage(Attacker);
	    		}
				else{
		    		if(groupPvP.hasPermissions(defender, "groupPvP.protected")){
						event.setCancelled(true);
						String Attacker=null;
						String DG=groupPvP.Permissions.getGroup(world,defender.getName());
			    		Attacker = groupPvP.pconfig.get("deny-protected").replace("%d", defender.getName());
			    		Attacker = Attacker.replace("%g", DG);
						attacker.sendMessage(Attacker);
		    		}
						else
						{
							String AG=groupPvP.Permissions.getGroup(world,attacker.getName());
							String DG=groupPvP.Permissions.getGroup(world,defender.getName());
				    		if(groupPvP.hasPermissions(attacker, "groupPvP.blockgroup")&&AG.equals(DG)){
								event.setCancelled(true);
								String Attacker=null;
					    		Attacker = groupPvP.pconfig.get("deny-own-group").replace("%d", defender.getName());
					    		Attacker = Attacker.replace("%g", AG.trim());								
								attacker.sendMessage(Attacker);
						}
		    		}
		    		}	    		
				}
			}
		}
	}