package com.allu.minigameapi.ranking;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FloatingRankingList {

	private ChatColor primaryColor;
	private ChatColor secondaryColor;
	private final Location location;
	private String header;
	
	private double headerOffY = 0.5;
	private double offY = 0.28;
	
	public FloatingRankingList(Location loc, String header, ChatColor primaryColor, ChatColor secondaryColor) {
		this.location = loc;
		this.header = header;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
	}

	public void recreateHolograms(List<RankedPlayer> players) {
		players = players.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		ArrayList<String> rankingList = getTopRankings(players, 10);
		World world = location.getWorld();
		for (Entity e : world.getEntities()) {
			if (belongsToThisList(e)) {
				e.remove();
			}
		}
		createHologram(location, header);
		for (int i = 0; i < rankingList.size(); i++) {
			createHologram(location.clone().add(0, -headerOffY - (i * offY), 0), rankingList.get(i));
		}
	}
	
	private void createHologram(Location loc, String text) {
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setCustomName(text); //Set this to the text you want
		as.setGravity(false); //Make sure it doesn't fall
		as.setCanPickupItems(true); //I'm not sure what happens if you leave this as it is, but you might as well disable it
		as.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
		as.setVisible(false); //Makes the ArmorStand invisible
		as.setSmall(true);
	}

	public void createHologramToPlayer(Player p, String text) {
		EntityArmorStand as = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle(),
				location.getX(), location.getY() - offY * 2 - (10 * offY),location.getZ());
		as.setCustomName(text);
		as.setCustomNameVisible(true);
		as.setGravity(false);
		as.setSmall(true);
		as.setInvisible(true);

		PacketPlayOutSpawnEntityLiving entity = new PacketPlayOutSpawnEntityLiving(as);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(entity);
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
		count = Math.min(rankedPlayers.size(), count);
		
		while (i < count) {
			RankedPlayer rp = rankedPlayers.get(i);
			if (rp.getValue() > 0) {
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
