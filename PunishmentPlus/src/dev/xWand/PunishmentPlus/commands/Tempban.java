package dev.xWand.PunishmentPlus.commands;

import dev.xWand.PunishmentPlus.playerdata.PlayerData;
import dev.xWand.PunishmentPlus.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tempban implements CommandExecutor {

    PlayerData data;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("tempban") || label.equalsIgnoreCase("tban")) {
            if (!(sender instanceof Player) || sender.isOp()) {
                if (args.length >= 3) {
                    String format = args[1].substring((int) args[1].chars().count() - 1, (int) args[1].chars().count());
                    int duration = Integer.parseInt(args[1].substring(0, (int) args[1].chars().count() - 1));
                    long time = 0;

                    switch (format) {
                        case "s":
                            time = duration;
                            break;

                        case "m":
                            time = duration * 60;
                            break;

                        case "h":
                            time = duration * 60 * 60;
                            break;

                        case "d":
                            time = duration * 60 * 60 * 24;
                            break;

                        case "w":
                            time = duration * 60 * 60 * 24 * 7;
                            break;

                        case "y":
                            time = duration * 60 * 60 * 24 * 365;
                            break;

                        default:
                            sender.sendMessage(ChatColor.RED + "That is an invalid time type (s, m, h, d, w, y)");
                            return true;
                    }

                    String reason = "";
                     for (int i = 2; i < args.length; i++) {
                         reason = reason + args[i] + " ";
                     }



                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        if (op == null) {
                            sender.sendMessage(ChatColor.RED + "That player is invalid.");
                            return true;
                        }

                        // TAKE ACTION HERE
                        data = new PlayerData(op.getUniqueId());
                        data.setTempbanned(true, sender.getName(), reason, time);
                        data.saveData(data.conf, data.f);


                        // MESSAGES

                        for (Player all : Bukkit.getOnlinePlayers()) {
                           if (all.isOp()) {
                                all.sendMessage(ChatColor.GREEN + op.getName() + " was temporarily banned by " + ChatColor.RED + sender.getName() + ChatColor.GREEN + " for " + ChatColor.YELLOW + Utils.formatTime((int) ((int) data.getExpires() - (System.currentTimeMillis()/1000)))+".");
                            }
                        }

                        return true;
                    }
                    // TAKE ACTION HERE
                    data = new PlayerData(target.getUniqueId());
                    target.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYour account has been temporarily suspended from this server.\n\nExpires: " + Utils.formatTime((int) time)));
                    data.setTempbanned(true, sender.getName(), reason.trim(), time);


                    // MESSAGES
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.isOp()) {
                            all.sendMessage(ChatColor.GREEN + target.getName() + " was temporarily banned by " + ChatColor.RED + sender.getName() + ChatColor.GREEN + " for " + ChatColor.YELLOW + Utils.formatTime((int) ((int) data.getExpires() - (System.currentTimeMillis()/1000)))+".");
                        }
                    }
                }
            }
        }
        return true;
    }
}
