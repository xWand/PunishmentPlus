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

public class Mute implements CommandExecutor {

    Main p = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("mute")) {
            if (!(sender instanceof Player) || sender.isOp() || sender.hasPermission(Utils.mutePerm())) {
                if (args.length > 0) {
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


                        if (reason.contains("-s")) {
                            reason = reason.replace("-s", "");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (all.hasPermission("punishmentplus.alert")) {
                                    all.sendMessage(ChatColor.GREEN + op.getName() + " was" + ChatColor.YELLOW + " silently " + ChatColor.GREEN + "permanently " + "muted by " + ChatColor.RED + sender.getName() + ChatColor.GREEN + ".");
                                }
                            }
                        }else if (reason.contains("-p") || !reason.contains("-s")) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(ChatColor.translateAlternateColorCodes('&',p.getConfig().getString("messages.mute.success_public").replace("%player%", op.getName()).replace("%sender%", sender.getName()).replace("%time%", "")));
                                reason = reason.replace("-p", "");
                            }
                        }
                        data.setMuted(true, sender.getName(), reason.trim());
                        return true;
                    }


                    if (reason.contains("-s")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (all.isOp()) {
                                all.sendMessage(ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("messages.mute.success_silent").replace("%player%", target.getName()).replace("%sender%", sender.getName()).replace("%time%", "")));
                                reason = reason.replace("-s", "");
                            }
                        }
                    } else if (reason.contains("-p") || !reason.contains("-s")) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&',p.getConfig().getString("messages.mute.success_public").replace("%player%", target.getName()).replace("%sender%", sender.getName()).replace("%time%", "")));
                            reason = reason.replace("-p", "");
                        }
                    }

                    PlayerData data = new PlayerData(target.getUniqueId());
                    data.setMuted(true, sender.getName(), reason.trim());
                    target.sendMessage(ChatColor.RED + "You have been permanently muted.\nReason: " + data.getMuteReason());
                    return true;
                }
            }
            Utils.noPerms(sender);
            return true;
        }
        return true;
    }
}
