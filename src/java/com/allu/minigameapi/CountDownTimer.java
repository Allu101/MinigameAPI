package com.allu.minigameapi;

import com.allu.minigameapi.player.Sounds;
import com.allu.minigameapi.player.TitleHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CountDownTimer {

	private boolean isRunning = false;
	private int time;
	private int taskID;
	
	private final CountDownTimerListener listener;
	private final ArrayList<Player> players;
	private String message;
	private TitleHandler titleHandler = new TitleHandler();
	
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

	public boolean isRunning() {
		return isRunning;
	}

	public void removePlayer(Player p) {
		players.remove(p);
	}

	public void start(int newTime) {
		start(newTime, "");
	}

	/**
	 * %time% - time
	 * @return
	 */
	public void start(int newTime, String msg) {
		this.stop();
		isRunning = true;
		time = newTime;
		message = msg;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinigameAPI.plugin, () -> {
        	if (time <= 0) {
        		stop();
        		listener.onCountDownFinish();
				return;
			}
			listener.onCountDownChange(time);
			if (!message.isEmpty()) {
				if ((time % 5 == 0 && time <= 10) || time <= 3) {
					for (Player p : players) {
						p.sendMessage(ChatColor.YELLOW + message.replaceAll("%time%", String.valueOf(time)));
						try {
							p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1f, 0f);
						} catch (Exception e) {

						}
						titleHandler.sendTitle(p, "§e" + time);
					}
				}
			}
			time -= 1;
		}, 0L, 20L);
	}
	
	public void stop() {
		isRunning = false;
		Bukkit.getScheduler().cancelTask(taskID);
	}

}
