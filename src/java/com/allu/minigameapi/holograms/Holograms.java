package com.allu.minigameapi.holograms;

import com.allu.minigameapi.MinigameAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Holograms {

    public static Hologram createHologram(Location loc, String text) {
        Hologram hologram = HologramsAPI.createHologram(MinigameAPI.plugin, loc);
        hologram.insertTextLine(0, text);
        return hologram;
    }

    public static void createHologramToPlayer(Player p, Location loc, String text) {
        Hologram hologram = createHologram(loc, text);
        hologram.getVisibilityManager().setVisibleByDefault(false);
        hologram.getVisibilityManager().showTo(p);
    }

    public static Hologram getHologram(Location loc, String text) {
        for(Hologram hologram : HologramsAPI.getHolograms(MinigameAPI.plugin)) {
            if (hologram.getLocation().equals(loc) && hologram.getLine(0).toString().contains(text)) {
                return hologram;
            }
        }
        return null;
    }

    public static void removeHologram(Location loc, String text) {
        getHologram(loc, text).delete();
    }
}
