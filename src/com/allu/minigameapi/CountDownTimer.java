package com.allu.minigameapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CountDownTimer {
	
	private int time;
	private int taskID;
	
	private final CountDownTimerListener listener;
	private final ArrayList<Player> players;
	private String message;
	
	public CountDownTimer(CountDownTimerListener listener) {
		this.listener = listener;
		players = new ArrayList<>();
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void clearPlayers() {
		players.clear();
	}
	
	public void removePlayer(Player p) {
		players.remove(p);
	}

	public void start(int newTime) {
		start(newTime, "");
	}

	public void start(int newTime, String msg) {
		this.stop();
		time = newTime;
		message = msg;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinigameAPI.plugin, () -> {
        	if(time <= 0) {
        		stop();
        		listener.onCountDownFinish();
				return;
			}
			listener.onCountDownChange(time);
			if (!message.isEmpty()) {
				if ((time % 5 == 0 && time <= 10) || time <= 3) {
					for (Player p : players) {
						p.sendMessage(ChatColor.YELLOW + message + " " + time + " sekuntia");
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, 0f);
						p.sendTitle("" + ChatColor.YELLOW + time, "");
					}
				}
			}
			time -= 1;
		}, 0L, 20L);
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(taskID);
	}

}
