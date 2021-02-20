package dev.xWand.PunishmentPlus.commands;

import dev.xWand.PunishmentPlus.Main;
import dev.xWand.PunishmentPlus.playerdata.PlayerData;
import dev.xWand.PunishmentPlus.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Check implements CommandExecutor {

    Main p = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("check")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            if (args.length ==0) {
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                if (op == null) {
                    player.sendMessage(ChatColor.RED + "That player is invalid.");
                    return true;
                }
                PlayerData data = new PlayerData(op.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Searching for recent punishment for " + ChatColor.YELLOW + op.getName() + ChatColor.GREEN + " ...");
                if (data.exists()) {
                    openInventory(player, op);
                    player.sendMessage(ChatColor.GREEN + "Successfully found recent punishment.");
                    return true;
                } else {
                    player.sendMessage(ChatColor.YELLOW + op.getName() + ChatColor.RED + " does not have any recent punishments.");
                    return true;
                }
            }
            PlayerData data = new PlayerData(target.getUniqueId());
            if (data.getReason() == null || data.getReason().equalsIgnoreCase("null")) {
                player.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.RED + " does not have any recent punishments.");
                return true;
            }
            player.sendMessage(ChatColor.GREEN + "Searching for recent punishment for " + ChatColor.YELLOW + target.getName() + ChatColor.GREEN + " ...");
            if (data.exists()) {
                openInventory(player, target);
                player.sendMessage(ChatColor.GREEN + "Successfully found recent punishment.");
            } else if (data.getReason() == null || data.getReason().equalsIgnoreCase("null")){
                player.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.RED + " does not have any recent punishments.");
                return true;
            }
        }
        return true;
    }

    public void openInventory(Player player, Player target) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + "'s latest ban"));
        ItemStack item = new ItemStack(Material.WOOL, 1, (byte) 14);
        ItemMeta meta = item.getItemMeta();
        PlayerData data = new PlayerData(target.getUniqueId());
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lBan"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&eActive: &7" + data.isBanned()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&eReason: &7" + data.getReason().trim()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&eExecutor: &7" + data.getExecutor()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&eWhen: &7" + new Utils().millisToDate(data.getMillis()*1000)));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(13, item);
        player.openInventory(inv);
    }

    public void openInventory(Player player, OfflinePlayer target) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + "'s latest ban"));
        ItemStack item = new ItemStack(Material.WOOL, 1, (byte) 14);
        ItemMeta meta = item.getItemMeta();
        PlayerData data = new PlayerData(target.getUniqueId());
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lBan"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&eActive: &7" + data.isBanned()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&eReason: &7" + data.getReason()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&eExecutor: &7" + data.getExecutor()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&eWhen: &7" + new Utils().millisToDate(data.getMillis()*1000)));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(13, item);
        player.openInventory(inv);
    }
}
