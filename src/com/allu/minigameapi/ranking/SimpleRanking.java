package com.allu.minigameapi.ranking;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SimpleRanking {

	private ArrayList<RankedPlayer> players;
	private ArrayList<FloatingRankingList> floatingRankingLists;
	
	public SimpleRanking(ArrayList<RankedPlayer> players) {
		this.players = players;
		floatingRankingLists = new ArrayList<FloatingRankingList>();
	}
	
	public void setRanking(Player p, int value) {
		RankedPlayer rp = getRankedPlayer(p.getUniqueId().toString());
		if(rp == null) {
			rp = new RankedPlayer(p.getUniqueId().toString(), p.getName(), value);
			this.players.add(rp);
		}
		else {
			rp.setName(p.getPlayer().getName());
			rp.setValue(value);
		}
	}
	
	public void addFloatingRankingList(Location location, String header, ChatColor primarycolor, ChatColor secondaryColor) {
		FloatingRankingList floatingRankingList = new FloatingRankingList(location, header, primarycolor, secondaryColor);
		floatingRankingList.recreateHolograms(players);
		floatingRankingLists.add(floatingRankingList);
	}
	
	public void updateRankingWithPlayers() {
		for(FloatingRankingList frl : floatingRankingLists) {
			frl.recreateHolograms(players);
		}
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
