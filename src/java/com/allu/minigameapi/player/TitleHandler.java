package com.allu.minigameapi.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleHandler {

    //PacketPlayOutTitle lengthPacket;

    public TitleHandler() {
        //lengthPacket = new PacketPlayOutTitle(0, 30, 20);
    }

    public void sendTitle(Player p, String title, ChatColor color, boolean isBold) {
        //sendTitlePacket(p, title, color, PacketPlayOutTitle.EnumTitleAction.TITLE, isBold);
        new Title(title, null, 0, 30, 20);
    }

    public void sendSubTitle(Player p, String title, ChatColor color, boolean isBold) {
        //sendTitlePacket(p, title, color, PacketPlayOutTitle.EnumTitleAction.SUBTITLE, isBold);
        new Title(null, title, 0, 30, 20);
    }

    /*private void sendTitlePacket(Player p, String title, ChatColor color, PacketPlayOutTitle.EnumTitleAction type, boolean isBold) {
        IChatBaseComponent chatTitle =
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\",\"bold\":" + isBold + ",\"color\":\"" + color.name().toLowerCase() + "\"}");

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(type, chatTitle));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(lengthPacket);
    }*/



}
