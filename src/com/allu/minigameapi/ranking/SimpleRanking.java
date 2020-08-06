package com.allu.minigameapi.ranking;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleRanking {

	private Map<World, FloatingRankingList> floatingRankingLists;
	private Map<String, RankedPlayer> players = new HashMap<>();
	private ChatColor primaryColor;
	private ChatColor secondaryColor;

	public SimpleRanking(ArrayList<RankedPlayer> players) {
		players.forEach(rp -> this.players.put(rp.getUuid(), rp));
		floatingRankingLists = new HashMap<>();
	}

	public void setRanking(Player p, int value) {
		RankedPlayer rp = players.get(p.getUniqueId().toString());
		if (rp == null) {
			rp = new RankedPlayer(p.getUniqueId().toString(), p.getName(), value);
			players.put(p.getUniqueId().toString(), rp);
		} else {
			rp.setName(p.getPlayer().getName());
			rp.setValue(value);
		}
	}
	
	public void addFloatingRankingList(Location location, String header, ChatColor primaryColor, ChatColor secondaryColor) {
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		List<RankedPlayer> rPlayers = new ArrayList<>(players.values());
		FloatingRankingList floatingRankingList = new FloatingRankingList(location, header, primaryColor, secondaryColor);
		floatingRankingList.recreateHolograms(rPlayers);
		floatingRankingLists.put(location.getWorld(), floatingRankingList);
	}
	
	public void updateRankingWithPlayers(List<RankedPlayer> topPlayers) {
		floatingRankingLists.values().forEach(frl -> {
			frl.recreateHolograms(topPlayers);
		});
	}

	public void showPlayerOwnHologram(Player p) {
		RankedPlayer rp = players.get(p.getUniqueId().toString());
		if (rp != null) {
			int placeNumber = new ArrayList<>(players.values()).stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).indexOf(rp) + 1;
			if (placeNumber > 10) {
				createPlayerHologram(p, rp, placeNumber);
			}
		}
	}

	private void createPlayerHologram(Player p, RankedPlayer rp, int placeNumber) {
		String text = "" + primaryColor + "&l" + placeNumber
				+ ". " + secondaryColor + "&l" + rp.getName() + ChatColor.GRAY + "&l" + " - " + primaryColor + "&l" + rp.getValue();
		floatingRankingLists.get(p.getLocation().getWorld()).createHologramToPlayer(p, ChatColor.translateAlternateColorCodes('&', text));
	}

}
