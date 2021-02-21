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
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;

import java.util.List;

public class Tempmute implements CommandExecutor {

    PlayerData data;
    Main p = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("tempmute") || label.equalsIgnoreCase("tmute")) {
            if (!(sender instanceof Player) || sender.isOp() || sender.hasPermission(Utils.tempmutePerm())) {
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

                    if (args[2].startsWith("@")) {
                        try {
                            MemorySection ms = (MemorySection) p.getConfig().getConfigurationSection("predefined_reasons").get(args[2].replace("@", "").trim());
                            ms.getName();
                            sender.sendMessage(ms.getName());
                            List<String> text = ms.getStringList("text");
                            for (int i = 0; i < text.size(); i++) {
                                if (i > 0) {
                                    reason = reason + " " + text.get(i).replace("@" + ms.getName(), "");
                                } else {
                                    reason = reason + text.get(i).replace("@" + ms.getName(), "");
                                }
                                reason = reason.replace("@" + ms.getName(), "");
                            }
                        } catch (NullPointerException e) {
                            sender.sendMessage("Unable to find that layout.");
                            return true;
                        }
                    }

                    reason = reason.replace(args[1], "");


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
                                if (all.isOp() || Utils.hasPerm(all)) {
                                    all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempmute.success_silent").replace("%player%", op.getName()).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time))));
                                    reason = reason.replace("-s", "");
                                }
                            }
                        } else if (reason.contains("-p") || !reason.contains("-s")) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempmute.success_public").replace("%player%", op.getName()).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time))));
                                reason = reason.replace("-p", "");
                            }
                        }
                        data.setTempmuted(true, sender.getName(), reason.trim(), time);
                        data.saveData(data.conf, data.f);
                        return true;
                    }

                    if (target.hasPermission("punishmentplus.exempt")) {
                        sender.sendMessage(ChatColor.RED + "You cannot punish this player.");
                        return true;
                    }
                    // TAKE ACTION HERE
                    data = new PlayerData(target.getUniqueId());

                    // MESSAGES
                    if (reason.contains("-s")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (all.isOp() || Utils.hasPerm(all)) {
                                all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempmute.success_silent").replace("%player%", target.getName()).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time))));
                                reason = reason.replace("-s", "");
                            }
                        }
                    } else if (reason.contains("-p") || !reason.contains("-s")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempmute.success_public").replace("%player%", target.getName()).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time))));
                            reason = reason.replace("-p", "");
                        }
                    }
                    data.setTempmuted(true, sender.getName(), reason.trim(), time);
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.tempmute.target_message").replace("%reason%", reason.trim())).replace("%sender%", sender.getName()).replace("%time%", Utils.formatTime((int) time)));

                    return true;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.invalid_syntax").replace("%command%", "/tempmute <player> <time> <reason> <modifiers>")));
                return true;
            }
        }
        Utils.noPerms(sender);
        return true;
    }
}
