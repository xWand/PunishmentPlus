package dev.xWand.PunishmentPlus.commands;

import dev.xWand.PunishmentPlus.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mute implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("mute")) {
            if (!(sender instanceof Player) || sender.isOp()) {
                if (args.length >= 0) {
                    String reason = "";

                    if (args.length >= 1) {
                        if (args.length > 1) {
                            for (int i = 1; i <args.length; i++) {
                                reason = reason + args[i] + " ";
                            }
                        }
                    }

                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        if (op==null) {
                            sender.sendMessage(ChatColor.RED + "Invalid player");
                            return true;
                        }
                        PlayerData data = new PlayerData(op.getUniqueId());
                        data.setMuted(true, sender.getName(), reason.trim());

                        sender.sendMessage(ChatColor.GREEN + op.getName() + " was permanently muted by " + ChatColor.RED + sender.getName() + ChatColor.GREEN + ".");
                        return true;
                    }
                    PlayerData data = new PlayerData(target.getUniqueId());
                    data.setMuted(true, sender.getName(), reason.trim());
                    target.sendMessage(ChatColor.RED + "You have been permanently muted.\nReason: " + data.getMuteReason());
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " was permanently muted by " + ChatColor.RED + sender.getName() + ChatColor.GREEN + ".");
                    return true;
                }
            }
        }
        return true;
    }
}
