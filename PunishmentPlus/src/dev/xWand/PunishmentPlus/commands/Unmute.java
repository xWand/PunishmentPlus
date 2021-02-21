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

public class Unmute implements CommandExecutor {

    Main p = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("unmute")) {
            if (!(sender instanceof Player) || sender.isOp() || sender.hasPermission(Utils.unmutePerm())) {
                if (args.length >= 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        if (op == null) {
                            sender.sendMessage(ChatColor.RED + "invalid played.");
                            return true;
                        }
                        PlayerData data = new PlayerData(op.getUniqueId());
                        if (data.isMuted()) {
                            data.setTempmuted(false, "", "", 0);
                            if (args.length > 1) {
                                if (args[1].contains("-s")) {
                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                        if (Utils.hasPerm(all)) {
                                            all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.unmute.success_silent").replace("%player%", op.getName()).replace("%sender%", sender.getName())));

                                        }
                                    }
                                } else {
                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.unmute.success_public").replace("%player%", op.getName()).replace("%sender%", sender.getName())));

                                    }
                                }
                            }
                        }
                    }
                    PlayerData data = new PlayerData(target.getUniqueId());
                    if (data.isMuted()) {
                        data.setTempmuted(false, "", "", 0);
                        if (args.length > 1) {
                            if (args[1].contains("-s")) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (Utils.hasPerm(all)) {
                                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.unmute.success_silent").replace("%player%", target.getName()).replace("%sender%", sender.getName())));
                                    }
                                }
                            } else {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.unmute.success_public").replace("%player%", target.getName()).replace("%sender%", sender.getName())));

                                }
                            }
                        }
                    }
                    return true;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.invalid_syntax").replace("%command%", "/unmute <player> <modifiers>")));
                return true;
            }
            Utils.noPerms(sender);
            return true;
        }
        return true;
    }
}
