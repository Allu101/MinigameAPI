package com.allu.minigameapi.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class LevelHandler {
	
	Map<Player, Double> playerLevels = new HashMap<>();
	
	public static void addExperience(Player p, int xp) {
		
	}
	
	public static void getPlayerLevel(Player p) {
		
	}
	
	public static void setPlayerLevel(Player player, int level, int xp) {
		player.setLevel(level);
		player.setExp(xp);
	}
}
