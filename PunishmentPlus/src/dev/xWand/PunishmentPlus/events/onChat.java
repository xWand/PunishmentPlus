package dev.xWand.PunishmentPlus.events;

import dev.xWand.PunishmentPlus.Main;
import dev.xWand.PunishmentPlus.playerdata.PlayerData;
import dev.xWand.PunishmentPlus.utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class onChat implements Listener {

    Main p = Main.getPlugin(Main.class);

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData data = new PlayerData(player.getUniqueId());
        if (data.isMuted() && data.getMuteExpires() < (System.currentTimeMillis()/1000) && data.getMuteExpires() != 0) {
            return;
        }
        if (data.isMuted() && !(data.getMuteExpires() == 0)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempmute.sender_message_fail").replace("%reason%", data.getMuteReason()).replace("%sender%", data.getMuteExecutor()).replace("%time%", Utils.formatTime((int) ((int) (data.getMuteExpires()-System.currentTimeMillis()/1000))))));
            return;
        }
        if (data.isMuted() && data.getMuteExpires() == 0) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.mute.sender_message_fail").replace("%reason%", data.getMuteReason()).replace("%sender%", data.getMuteExecutor()).replace("%time%", Utils.formatTime((int) data.getMuteExpires()))));
        }
    }
}
