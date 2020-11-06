package com.allu.minigameapi;

import org.bukkit.Bukkit;
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

	public String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().substring(23);
	}

	public Class<?> getClass(String className) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + getServerVersion() + "." + className);
	}

}
