package com.allu.minigameapi.ranking;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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

	public void recreateHolograms(List<RankedPlayer> sortedPlayers) {
		ArrayList<String> rankingList = getTopRankings(sortedPlayers, 10);
		Bukkit.getOnlinePlayers().forEach(player -> {
			createAndSendHologramPacket(player, location, header);
			for (int i = 0; i < rankingList.size(); i++) {
				createAndSendHologramPacket(player, location.clone().add(0, -headerOffY - (i * offY), 0), rankingList.get(i));
			}
		});
	}

	public void createPlayerOwnStatsHologram(Player p, RankedPlayer rp, int placeNumber) {
		createAndSendHologramPacket(p, new Location(location.getWorld(), location.getX(), location.getY() - offY * 2 - (10 * offY), location.getZ()),
				primaryColor + "&l" + placeNumber + ". " + secondaryColor + "&l" + rp.getName() + "&7&l - " + primaryColor + "&l" + rp.getValue());
	}

	private void createAndSendHologramPacket(Player p, Location loc, String text) {
		EntityArmorStand as = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle(),
				loc.getX(), loc.getY(), loc.getZ());

		as.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
		as.setCustomNameVisible(true);
		as.setGravity(false);
		as.setSmall(true);
		as.setInvisible(true);
		PacketPlayOutSpawnEntityLiving entity = new PacketPlayOutSpawnEntityLiving(as);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(entity);
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
