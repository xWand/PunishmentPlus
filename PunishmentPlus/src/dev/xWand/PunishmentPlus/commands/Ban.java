package dev.xWand.PunishmentPlus.commands;

import dev.xWand.PunishmentPlus.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ban implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("ban")) {
            if (!(sender instanceof Player) || sender.isOp()) {
                if (args.length == 0) {
                    return true;
                }
                String reason = "";
                if (args.length > 1) {
                    for (int i = 1; i <args.length; i++) {
                        reason = reason + args[i] + " ";
                    }
                }
                // Online player
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    // Target is not online or doesn't exist
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    if (op == null) {
                        // Target does not exist at all
                        return true;
                    }
                    // File Action (save data)
                    PlayerData data = new PlayerData(op.getUniqueId());
                    data.setBanned(true, sender.getName(), reason);
                    data.saveData(data.conf, data.f);


                    // Messages
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.isOp()) {
                            all.sendMessage(ChatColor.GREEN + op.getName() + " was banned by " + ChatColor.RED + sender.getName() + ChatColor.GREEN + ".");
                        }
                    }
                    return true;
                }
                // File Action (save data)
                PlayerData data = new PlayerData(target.getUniqueId());
                data.setBanned(true, sender.getName(), reason);
                data.saveData(data.conf, data.f);


                // Messages
                target.kickPlayer(ChatColor.RED + "Your account has been permanently suspended from this server.");
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.isOp()) {
                        all.sendMessage(ChatColor.GREEN + target.getName() + " was banned by " + ChatColor.RED + sender.getName() + ChatColor.GREEN + ".");
                    }
                }
            }
        }
        return true;
    }
}
