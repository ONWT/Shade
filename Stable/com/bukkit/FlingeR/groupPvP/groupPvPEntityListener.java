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
	    		if (groupPvP.hasPermissions(attacker, "groupPvP.denyattack")) {
				event.setCancelled(true);
				String Attacker=null;
	    		Attacker = groupPvP.pconfig.get("deny-attack").replace("%d", defender.getName());
	    		Attacker = Attacker.replace("%g", groupPvP.Permissions.getGroup(defender.getName()));
				attacker.sendMessage(Attacker);
	    		}
				else{
		    		if(groupPvP.hasPermissions(defender, "groupPvP.protected")){
						event.setCancelled(true);
						String Attacker=null;
			    		Attacker = groupPvP.pconfig.get("deny-protected").replace("%d", defender.getName());
			    		Attacker = Attacker.replace("%g", groupPvP.Permissions.getGroup(defender.getName()));
						attacker.sendMessage(Attacker);
		    		}
						else
						{
							String AG=groupPvP.Permissions.getGroup(attacker.getName());
							String DG=groupPvP.Permissions.getGroup(defender.getName());
				    		if(groupPvP.hasPermissions(attacker, "groupPvP.blockgroup")&&AG.equals(DG)){
								event.setCancelled(true);
								String Attacker=null;
					    		Attacker = groupPvP.pconfig.get("deny-own-group").replace("%d", defender.getName());
					    		Attacker = Attacker.replace("%g", AG);								
								attacker.sendMessage(Attacker);
						}
		    		}
		    		}	    		
				}
			}
		}
	}