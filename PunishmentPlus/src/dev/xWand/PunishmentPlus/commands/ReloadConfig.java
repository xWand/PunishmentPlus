package dev.xWand.PunishmentPlus.commands;


import dev.xWand.PunishmentPlus.Main;
import dev.xWand.PunishmentPlus.utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadConfig implements CommandExecutor {

    Main p = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("Pmpreload")) {
            if (!(sender instanceof Player) || sender.isOp()) {
                p.reloadConfig();
            }
            Utils.noPerms(sender);
            return true;
        }
        return true;
    }
}
