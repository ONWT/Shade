package com.bukkit.FlingeR.groupPvP;
import java.util.Collections;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.config.ConfigurationNode;
public class groupPvPEntityListener extends EntityListener {
	@SuppressWarnings("unused")
	private final groupPvP plugin;
	private final ConfigurationNode antiAttackNode;
	
	public groupPvPEntityListener(groupPvP instance) {
		plugin = instance;
		antiAttackNode = groupPvP.config.getNode("anti attack");
	}

	public void onEntityDamage (EntityDamageEvent event) {		
		if (!(event instanceof EntityDamageByEntityEvent))
			return;
		
		EntityDamageByEntityEvent entEvent = (EntityDamageByEntityEvent)event;
		if ( !(entEvent.getDamager() instanceof Player) || !(entEvent.getEntity() instanceof Player) )
			return;
		
		Player attacker = (Player)entEvent.getDamager();
		Player defender = (Player)entEvent.getEntity();
		String world = attacker.getWorld().getName();
		String DG=groupPvP.Permissions.getPrimaryGroup(world,defender.getName());
		String AG=groupPvP.Permissions.getPrimaryGroup(world,attacker.getName());
		if (antiAttackNode.getStringList(AG, Collections.<String>emptyList()).contains(DG)) {
			event.setCancelled(true);
			String noAttackMessage = groupPvP.config.getString("deny-attack", "- You are not allowed to attack other players for group %g.").replace("%d", defender.getName());
			noAttackMessage = noAttackMessage.replace("%g", DG);
			attacker.sendMessage(noAttackMessage);
		}
	}
}