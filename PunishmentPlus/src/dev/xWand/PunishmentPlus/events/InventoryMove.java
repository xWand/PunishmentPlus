package dev.xWand.PunishmentPlus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class InventoryMove implements Listener {

    @EventHandler
    public void onMove(InventoryClickEvent event) {
        if (event.getInventory().getTitle().contains("latest ban")) {
            event.setCancelled(true);
        }
    }
}
