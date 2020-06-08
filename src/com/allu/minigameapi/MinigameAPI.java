package com.allu.minigameapi;

import org.bukkit.plugin.java.JavaPlugin;

public class MinigameAPI extends JavaPlugin {
	
	public static MinigameAPI plugin;

	@Override
    public void onEnable() {
		plugin = this;
	}

	@Override
    public void onDisable() {
	}

}
