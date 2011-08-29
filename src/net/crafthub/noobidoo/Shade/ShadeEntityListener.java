package net.crafthub.noobidoo.Shade;

/**
 * Crafthub Shade for Bukkit
 * @author Carfthub
 * 
 * Copyright 2011 AllGamer, LLC.
 * See LICENSE for licensing information.
 */

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.util.config.ConfigurationNode;
public class ShadeEntityListener extends EntityListener {
	@SuppressWarnings("unused")
	private final Shade plugin;
	private final ConfigurationNode antiAttackNode;
	private final List<String> antiTargetList;
	
	public ShadeEntityListener(Shade instance) {
		plugin = instance;
		antiAttackNode = Shade.config.getNode("anti attack");
		antiTargetList = Shade.config.getStringList("anti target", null);
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
		String DG=Shade.Permissions.getPrimaryGroup(world,defender.getName());
		String AG=Shade.Permissions.getPrimaryGroup(world,attacker.getName());
		if (antiAttackNode.getStringList(AG, Collections.<String>emptyList()).contains(DG)) {
			event.setCancelled(true);
			String noAttackMessage = Shade.config.getString("deny-attack", "- You are not allowed to attack other players for group %g.").replace("%d", defender.getName());
			noAttackMessage = noAttackMessage.replace("%g", DG);
			attacker.sendMessage(noAttackMessage);
		}
	}
	
	public void onEntityTarget(EntityTargetEvent event) {
		if(!(event.getTarget() instanceof Player))
			return;
		
		Player targeted = (Player)event.getTarget();

		String world = targeted.getWorld().getName();
		String targetedGroup = Shade.Permissions.getPrimaryGroup(world, targeted.getName());
		if (antiTargetList.contains(targetedGroup))
			event.setCancelled(true);
	}
}