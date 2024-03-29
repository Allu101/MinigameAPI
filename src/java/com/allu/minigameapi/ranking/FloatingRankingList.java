package com.allu.minigameapi.ranking;

import com.allu.minigameapi.holograms.Holograms;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FloatingRankingList {

	private final Hologram topListHologram;
	private ChatColor primaryColor;
	private ChatColor secondaryColor;
	private final Location location;
	private String header;
	private Location ownStatsLoc;
	
	private double headerOffY = 0.5;
	private double offY = 0.28;
	
	public FloatingRankingList(Location loc, String header, ChatColor primaryColor, ChatColor secondaryColor) {
		this.location = loc;
		this.header = header;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		topListHologram = Holograms.createHologram(location, header);
		ownStatsLoc = new Location(location.getWorld(), location.getX(),
				location.getY() - offY * 2 - (11.4 * offY), location.getZ());
	}

	public void updateTopList(List<RankedPlayer> sortedPlayers) {
		ArrayList<String> rankingList = getTopRankings(sortedPlayers, 10);
		topListHologram.clearLines();
		topListHologram.appendTextLine(header);
		for (int i = 0; i < rankingList.size(); i++) {
			topListHologram.appendTextLine(rankingList.get(i));
		}
	}

	public void createPlayerOwnStatsHologram(Player p, RankedPlayer rp, int placeNumber) {
		Location loc = new Location(location.getWorld(), location.getX(), location.getY() - offY * 2 - (11.5 * offY), location.getZ());
		if (Holograms.getHologram(loc, p.getName()) != null) {
			Holograms.removeHologram(loc, p.getName());
		}
		Holograms.createHologramToPlayer(p, ownStatsLoc, primaryColor + "§l" +
				placeNumber + ". " +secondaryColor + "§l" + rp.getName() + "§7§l - " + primaryColor + "§l" + rp.getValue());
	}

	private ArrayList<String> getTopRankings(List<RankedPlayer> rankedPlayers, int count) {
		ArrayList<String> lines = new ArrayList<>();
		int i = 0;
		count = Math.min(rankedPlayers.size(), count);
		
		while (i < count) {
			RankedPlayer rp = rankedPlayers.get(i);
			if (rp.getValue() > 0) {
				String numberPrefix;
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
