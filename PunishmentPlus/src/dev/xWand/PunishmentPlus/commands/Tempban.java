package dev.xWand.PunishmentPlus.commands;

import dev.xWand.PunishmentPlus.Main;
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
    Main p = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("tempban") || label.equalsIgnoreCase("tban")) {
            if (!(sender instanceof Player) || sender.isOp() || sender.hasPermission(Utils.tempbanPerm())) {
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


                        // MESSAGES

                        if (reason.contains("-s")) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (all.isOp()) {
                                    all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempban.success_silent").replace("%player%", op.getName()).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time))));
                                    reason = reason.replace("-s", "");
                                }
                            }
                        } else if (reason.contains("-p") || !reason.contains("-s")) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempban.success_public").replace("%player%", op.getName()).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time))));
                                reason = reason.replace("-p", "");
                            }
                        }
                        data.setTempbanned(true, sender.getName(), reason, time);
                        data.saveData(data.conf, data.f);
                        return true;
                    }
                    // TAKE ACTION HERE
                    data = new PlayerData(target.getUniqueId());
                    target.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYour account has been temporarily suspended from this server.\n\nExpires: " + Utils.formatTime((int) time)));
                    data.setTempbanned(true, sender.getName(), reason.trim(), time);


                    // MESSAGES
                    if (reason.contains("-s")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (all.isOp()) {
                                all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempban.success_silent").replace("%player%", target.getName()).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time))));
                                reason = reason.replace("-s", "");
                            }
                        }
                    } else if (reason.contains("-p") || !reason.contains("-s")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempban.success_public").replace("%player%", target.getName()).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time))));
                            reason = reason.replace("-p", "");
                        }
                    }
                }
            }
            Utils.noPerms(sender);
            return true;
        }
        return true;
    }
}
