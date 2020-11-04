package com.allu.minigameapi.player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleHandler {

    PacketPlayOutTitle lengthPacket;

    public TitleHandler() {
        lengthPacket = new PacketPlayOutTitle(0, 30, 20);
    }

    public void sendTitle(Player p, String title, ChatColor color, boolean isBold) {
        sendTitlePacket(p, title, color, PacketPlayOutTitle.EnumTitleAction.TITLE, isBold);
    }

    public void sendSubTitle(Player p, String title, ChatColor color, boolean isBold) {
        sendTitlePacket(p, title, color, PacketPlayOutTitle.EnumTitleAction.SUBTITLE, isBold);
    }

    private void sendTitlePacket(Player p, String title, ChatColor color, PacketPlayOutTitle.EnumTitleAction type, boolean isBold) {
        IChatBaseComponent chatTitle =
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\",\"bold\":" + isBold + ",\"color\":\"" + color.name().toLowerCase() + "\"}");

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(type, chatTitle));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(lengthPacket);
    }
}
