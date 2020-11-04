package com.allu.minigameapi.player;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class SidebarHandler {
	
	private String emptyRow = " ";
	private String header = "";
	
	private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	private Objective sidebar_obj = board.registerNewObjective("obj", "dummy");
	private ArrayList<String> sidebar_rows;
	
	public SidebarHandler(String title, ArrayList<String> rows) {
		header = title;
		this.sidebar_rows = rows;
	}
	
	public Scoreboard getBoard() {
		return board;
	}
	
	public void updateSidebar(ArrayList<String> rows) {
		for(String row : this.sidebar_rows) {
			sidebar_obj.getScoreboard().resetScores(row);
		}
		this.sidebar_rows = rows;
		createSidebar();
	}
	
	private void createSidebar() {
		int rowNumber = sidebar_rows.size();
		sidebar_obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		sidebar_obj.setDisplayName(header);
		for(String row : sidebar_rows) {
			if(row.isEmpty()) {
				row = emptyRow;
				emptyRow = emptyRow + " ";
			}
			sidebar_obj.getScore(row).setScore(rowNumber);
			rowNumber--;
		}
		emptyRow = " ";
	}

}
