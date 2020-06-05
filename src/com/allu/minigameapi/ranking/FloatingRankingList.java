package com.allu.minigameapi.ranking;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class FloatingRankingList {

	private final ChatColor primaryColor;
	private final ChatColor secondaryColor;
	private final Location location;
	private final String header;
	
	private double headerOffY = 0.5;
	private double offY = 0.28;
	
	public FloatingRankingList(Location loc, String header, ChatColor primaryColor, ChatColor secondaryColor) {
		this.location = loc;
		this.header = header;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
	}
	
	public void recreateHolograms(List<RankedPlayer> players) {
		ArrayList<String> rankingList = getTopRankings(players, 10);
		World world = location.getWorld();
		for (Entity e : world.getEntities()) {
			if (belongsToThisList(e)) {
				e.remove();
			}
		}
		createHologramText(location, header);
		for (int i = 0; i < rankingList.size(); i++) {
			createHologramText(location.clone().add(0, -headerOffY - (i * offY), 0), rankingList.get(i));
		}
	}
	
	private void createHologramText(Location loc, String text) {
		ArmorStand as = (ArmorStand)loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setGravity(false); //Make sure it doesn't fall
		as.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
		as.setCustomName(text); //Set this to the text you want
		as.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
		as.setVisible(false); //Makes the ArmorStand invisible
	}
	
	private boolean belongsToThisList(Entity e) {
		Location loc = e.getLocation();
		return e.getType().equals(EntityType.ARMOR_STAND) && 
				Math.abs(loc.getX() - location.getX()) < 1 && 
				Math.abs(loc.getZ() - location.getZ()) < 1;
	}
	
	private ArrayList<String> getTopRankings(List<RankedPlayer> rankedPlayers, int count) {
		ArrayList<String> lines = new ArrayList<>();
		int i = 0;
		if (count > rankedPlayers.size()) {
			count = rankedPlayers.size();
		}
		
		while (i < count) {
			RankedPlayer rp = rankedPlayers.get(i);
			if(rp.getValue() > 0) {
				String numberPrefix = "";
				switch (i) {
					case 0: numberPrefix = ChatColor.YELLOW + "❶";
						break;
					case 1: numberPrefix = ChatColor.GRAY + "❷";
						break;
					case 2: numberPrefix = ChatColor.GOLD + "❸";
						break;
					default: numberPrefix = "" + primaryColor + (i+1) + ".";
						break;
				}
				lines.add(numberPrefix + " " + secondaryColor + rp.getName() + ChatColor.GRAY + " - " + primaryColor + rp.getValue());
			}
			i++;
		}
		return lines;
	}
	
}
