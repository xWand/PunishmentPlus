package dev.xWand.PunishmentPlus.commands;

import dev.xWand.PunishmentPlus.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Unban implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("unban")) {
            if (!(sender instanceof Player) || sender.isOp()) {
                if (args.length >= 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "That player does not exist");
                        return true;
                    }
                    PlayerData data = new PlayerData(target.getUniqueId());
                    if (data.isBanned()) {
                        data.setBanned(false);
                        data.saveData(data.conf, data.f);
                        sender.sendMessage(ChatColor.GREEN + target.getName() + " has been unbanned by " + ChatColor.RED + sender.getName());
                    }
                }
            }
        }
        return true;
    }
}
