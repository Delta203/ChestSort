package de.chestsort.delta203.spigot.listeners;

import de.chestsort.delta203.spigot.ChestSort;
import de.chestsort.delta203.spigot.SortType;
import de.chestsort.delta203.spigot.utils.SortAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

public class Inventory implements Listener {

  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getView().getTitle().equalsIgnoreCase(ChestSort.configYml.get().getString("gui.title"))) {
      e.setCancelled(true);
      Player p = (Player) e.getWhoClicked();
      if (!p.hasPermission(
          Objects.requireNonNull(ChestSort.configYml.get().getString("permission")))) {
        playSound(p, Objects.requireNonNull(ChestSort.configYml.get().getString("sounds.fail")));
        e.getView().close();
        return;
      }
      Location loc = Interact.chestLocations.get(p);
      // check if chest is valid
      if (loc == null) {
        playSound(p, Objects.requireNonNull(ChestSort.configYml.get().getString("sounds.fail")));
        e.getView().close();
        return;
      }
      if (loc.getBlock().getType() != Material.CHEST) {
        playSound(p, Objects.requireNonNull(ChestSort.configYml.get().getString("sounds.fail")));
        e.getView().close();
        return;
      }
      try {
        if (Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta())
            .getDisplayName()
            .equalsIgnoreCase(ChestSort.configYml.get().getString("gui.items.sort_A_Z.name"))) {
          Chest chest = (Chest) loc.getBlock().getState();
          InventoryHolder holder = chest.getInventory().getHolder();
          if (holder instanceof DoubleChest doubleChest) { // double chest
            SortAPI.sortInventory(SortType.A_Z, doubleChest.getInventory());
          } else { // normal chest
            SortAPI.sortInventory(SortType.A_Z, chest.getInventory());
          }
          playSound(
              p, Objects.requireNonNull(ChestSort.configYml.get().getString("sounds.success")));
          e.getView().close();
        } else if (Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta())
            .getDisplayName()
            .equalsIgnoreCase(ChestSort.configYml.get().getString("gui.items.sort_amount.name"))) {
          Chest chest = (Chest) loc.getBlock().getState();
          InventoryHolder holder = chest.getInventory().getHolder();
          if (holder instanceof DoubleChest doubleChest) { // double chest
            SortAPI.sortInventory(SortType.AMOUNT, doubleChest.getInventory());
          } else { // normal chest
            SortAPI.sortInventory(SortType.AMOUNT, chest.getInventory());
          }
          playSound(
              p, Objects.requireNonNull(ChestSort.configYml.get().getString("sounds.success")));
          e.getView().close();
        }
      } catch (Exception ignored) {
      }
    }
  }

  private void playSound(Player p, String sound) {
    if (!sound.equalsIgnoreCase("NONE")) p.playSound(p.getLocation(), Sound.valueOf(sound), 1, 1);
  }
}
