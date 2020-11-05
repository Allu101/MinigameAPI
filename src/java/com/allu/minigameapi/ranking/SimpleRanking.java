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
			rp.setName(p.getName());
			rp.setValue(value);
		}
	}
	
	public void addFloatingRankingList(Location location, String header, ChatColor primaryColor, ChatColor secondaryColor) {
		FloatingRankingList frl = new FloatingRankingList(location, header, primaryColor, secondaryColor);
		floatingRankingLists.put(location.getWorld(), frl);
		frl.updateTopList(players.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
	}
	
	public void updateRanking(List<RankedPlayer> sortedPlayers) {
		floatingRankingLists.values().forEach(frl -> frl.updateTopList(sortedPlayers));
	}

	public void removePlayerOwnStatsHologram(String playerName) {
		floatingRankingLists.values().forEach(frl -> frl.removePlayerOwnStatsHologram(playerName));
	}

	public void updatePlayerOwnStatsHologram(Player p) {
		RankedPlayer rp = players.get(p.getUniqueId().toString());
		List<RankedPlayer> sortedPlayers = players.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		if (rp != null) {
			int placeNumber = sortedPlayers.indexOf(rp) + 1;
			if (placeNumber > 10) {
				floatingRankingLists.get(p.getLocation().getWorld()).createPlayerOwnStatsHologram(p, rp, placeNumber);
			}
		}
	}

}
