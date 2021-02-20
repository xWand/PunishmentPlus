package dev.xWand.PunishmentPlus;

import dev.xWand.PunishmentPlus.commands.*;
import dev.xWand.PunishmentPlus.events.InventoryMove;
import dev.xWand.PunishmentPlus.events.onChat;
import dev.xWand.PunishmentPlus.events.onJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {

    String prefix = "[PunishmentPlus]";

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(prefix + " The best punishment plugin for your server. Thank you for downloading.");
        Bukkit.getLogger().info(prefix + " Loading commands...");

        getCommand("ban").setExecutor(new Ban());
        getCommand("unban").setExecutor(new Unban());
        getCommand("check").setExecutor(new Check());
        getCommand("tempban").setExecutor(new Tempban());
        getCommand("mute").setExecutor(new Mute());
        getCommand("pmpreload").setExecutor(new ReloadConfig());
        getCommand("tempmute").setExecutor(new Tempmute());
        getCommand("unmute").setExecutor(new Unmute());

        Bukkit.getLogger().info(prefix + " Loading files...");
        saveDefaultConfig();

        Bukkit.getLogger().info(prefix + " Loading events...");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryMove(), this);
        pm.registerEvents(new onJoin(), this);
        pm.registerEvents(new onChat(), this);
    }

    @Override
    public void onDisable() {

    }
}
