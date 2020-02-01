package com.allu.minigameapi.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PrefixHandler {

	/**
	 * @param player
	 * @param prefix
	 */
	public static void setChatPrefix(Player player, String prefix) {
		player.setDisplayName(prefix + ChatColor.GRAY + " " + player.getDisplayName());
	}
	
}
