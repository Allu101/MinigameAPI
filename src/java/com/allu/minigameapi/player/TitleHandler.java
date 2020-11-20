package com.allu.minigameapi.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleHandler {

    public TitleHandler() {
    }

    public void sendSubTitle(Player p, String title) {
        new Title("", ChatColor.translateAlternateColorCodes('&', title), 0, 30, 20).send(p);
    }

    public void sendTitle(Player p, String title) {
        new Title(ChatColor.translateAlternateColorCodes('&', title), "", 0, 30, 20).send(p);
    }

    public void sendTitleAndSubTitle(Player p, String title, String subtitle) {
        new Title(ChatColor.translateAlternateColorCodes('&', title), subtitle, 0, 30, 20).send(p);
    }

}
