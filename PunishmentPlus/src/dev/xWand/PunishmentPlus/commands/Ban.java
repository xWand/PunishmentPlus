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

public class Ban implements CommandExecutor {

    Main p = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("ban")) {
            if (!(sender instanceof Player) || sender.isOp() || sender.hasPermission(Utils.banPerm())) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.invalid_syntax").replace("%command%", "/ban <player> <reason> <modifiers>")));
                    return true;
                }
                String reason = "";
                if (args.length > 1) {
                    for (int i = 1; i <args.length; i++) {
                        reason = reason + args[i] + " ";
                    }
                }

                if (args[1].startsWith("@")) {
                    try {
                        MemorySection ms = (MemorySection) p.getConfig().getConfigurationSection("predefined_reasons").get(args[1].replace("@", "").trim());
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

                // Online player
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    // Target is not online or doesn't exist
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    if (op == null) {
                        // Target does not exist at all
                        return true;
                    }

                    // Layouts


                    // File Action (save data)



                    // Messages
                    if (reason.contains("-s")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (all.isOp() || Utils.hasPerm(all)) {
                                all.sendMessage(ChatColor.translateAlternateColorCodes('&',p.getConfig().getString("messages.ban.success_silent").replace("%player%", op.getName()).replace("%sender%", sender.getName()).replace("%time%", "")));
                                reason = reason.replace("-s", "");
                            }
                        }
                    } else if (reason.contains("-p") || !reason.contains("-s")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&',p.getConfig().getString("messages.ban.success_public").replace("%player%", op.getName()).replace("%sender%", sender.getName()).replace("%time%", "")));
                            reason = reason.replace("-p", "");
                        }
                    }
                    PlayerData data = new PlayerData(op.getUniqueId());
                    data.setBanned(true, sender.getName(), reason);
                    data.saveData(data.conf, data.f);
                    return true;
                }
                // File Action (save data)

                if (target.hasPermission("punishmentplus.exempt")) {
                    sender.sendMessage(ChatColor.RED + "You cannot punish this player.");
                    return true;
                }

                // Messages
                target.kickPlayer(ChatColor.RED + "Your account has been permanently suspended from this server.");
                if (reason.contains("-s")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.isOp() || Utils.hasPerm(all)) {
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&',p.getConfig().getString("messages.ban.success_silent").replace("%player%", target.getName()).replace("%sender%", sender.getName()).replace("%time%", "")));
                            reason = reason.replace("-s", "");
                        }
                    }
                } else if (reason.contains("-p") || !reason.contains("-s")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&',p.getConfig().getString("messages.ban.success_public").replace("%player%", target.getName()).replace("%sender%", sender.getName()).replace("%time%", "")));
                        reason = reason.replace("-p", "");
                    }
                }
                PlayerData data = new PlayerData(target.getUniqueId());
                data.setBanned(true, sender.getName(), reason);
                data.saveData(data.conf, data.f);
                return true;
            }
            Utils.noPerms(sender);
            return true;
        }
        return true;
    }
}
