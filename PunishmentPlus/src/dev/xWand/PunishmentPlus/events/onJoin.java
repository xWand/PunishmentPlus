package dev.xWand.PunishmentPlus.events;

import dev.xWand.PunishmentPlus.playerdata.PlayerData;
import dev.xWand.PunishmentPlus.utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class onJoin implements Listener {

    @EventHandler
    public void joinevent(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        PlayerData data = new PlayerData(player.getUniqueId());

        if (data.isTempbanned() && data.getExpires() < (System.currentTimeMillis()/1000) && data.getExpires() != 0) {
            return;
        }
        if (data.isTempbanned()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&cYou are temporarily banned from this server.\n\nReason: " +
                    data.getReason()) + "\nExpires: " + Utils.formatTime((int) ((int) data.getExpires() - (System.currentTimeMillis()/1000))));
        }
        if (data.isBanned() && data.getExpires() == 0) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&cYou are permanently banned from this server.\n\nReason: " +
                    data.getReason()));
        }
    }
}
