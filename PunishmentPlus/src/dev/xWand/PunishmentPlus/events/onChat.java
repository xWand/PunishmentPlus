package dev.xWand.PunishmentPlus.events;

import dev.xWand.PunishmentPlus.playerdata.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onChat implements Listener {

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData data = new PlayerData(player.getUniqueId());

        if (data.isMuted()) {
            if (data.getMuteExpires() == 0) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are permanently muted for: " +ChatColor.YELLOW+ data.getMuteReason());
            }
        }
    }
}
