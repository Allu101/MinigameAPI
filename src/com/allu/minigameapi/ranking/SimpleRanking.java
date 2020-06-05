package com.allu.minigameapi.ranking;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SimpleRanking {

	private ArrayList<FloatingRankingList> floatingRankingLists;
	private ArrayList<RankedPlayer> players;
	
	public SimpleRanking(ArrayList<RankedPlayer> players) {
		this.players = players;
		floatingRankingLists = new ArrayList<>();
	}
	
	public void setRanking(Player p, int value) {
		RankedPlayer rp = getRankedPlayer(p.getUniqueId().toString());
		if(rp == null) {
			rp = new RankedPlayer(p.getUniqueId().toString(), p.getName(), value);
			players.add(rp);
		} else {
			rp.setName(p.getPlayer().getName());
			rp.setValue(value);
		}
	}
	
	public void addFloatingRankingList(Location location, String header, ChatColor primaryColor, ChatColor secondaryColor) {
		FloatingRankingList floatingRankingList = new FloatingRankingList(location, header, primaryColor, secondaryColor);
		floatingRankingList.recreateHolograms(players);
		floatingRankingLists.add(floatingRankingList);
	}
	
	public void updateRankingWithPlayers(List<RankedPlayer> topPlayers) {
		floatingRankingLists.forEach(frl -> frl.recreateHolograms(topPlayers));
	}
	
	private RankedPlayer getRankedPlayer(String uuid) {
		for(RankedPlayer rp : players) {
			if(rp.getUuid().equals(uuid)) {
				return rp;
			}
		}
		return null;
	}
}
