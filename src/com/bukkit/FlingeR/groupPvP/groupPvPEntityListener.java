package com.bukkit.FlingeR.groupPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
public class groupPvPEntityListener extends EntityListener {
	@SuppressWarnings("unused")
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
				String DG=groupPvP.Permissions.getPrimaryGroup(world,defender.getName());
				String AG=groupPvP.Permissions.getPrimaryGroup(world,attacker.getName());
				if (groupPvP.config.getStringList(AG, null).contains(DG)) {
					event.setCancelled(true);
					String Attacker=null;
					Attacker = groupPvP.config.getString("deny-attack", "- You are not allowed to attack other players for group %g.").replace("%d", defender.getName());
					Attacker = Attacker.replace("%g", DG);
					attacker.sendMessage(Attacker);
				}
			}
		}
	}
}