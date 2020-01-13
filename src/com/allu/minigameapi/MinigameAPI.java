package com.allu.minigameapi;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MinigameAPI extends JavaPlugin implements Listener{
	
	public static MinigameAPI plugin;
	
	@Override
    public void onEnable() {
		plugin = this;
	}

	@Override
    public void onDisable() {
	}
	
}
