package de.chestsort.delta203.spigot.listeners;

import de.chestsort.delta203.spigot.ChestSort;
import de.chestsort.delta203.spigot.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;

public class Interact implements Listener {

  public static HashMap<Player, Location> chestLocations = new HashMap<>();

  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    if (e.getHand() != EquipmentSlot.HAND) return;
    if (e.getAction() != Action.LEFT_CLICK_BLOCK) return;
    Player p = e.getPlayer();
    if (!p.isSneaking()) return;
    if (!p.hasPermission(Objects.requireNonNull(ChestSort.configYml.get().getString("permission"))))
      return;
    if (ChestSort.configYml.get().getStringList("disabledWorlds").contains(p.getWorld().getName()))
      return;

    try {
      if (Objects.requireNonNull(e.getClickedBlock()).getType() == Material.CHEST) {
        e.setCancelled(true);
        Location loc = e.getClickedBlock().getLocation();
        chestLocations.put(p, loc);
        // open chest sort gui
        int guiSize = ChestSort.configYml.get().getInt("gui.size");
        String guiTitle = ChestSort.configYml.get().getString("gui.title");
        String guiGlass = ChestSort.configYml.get().getString("gui.glass");

        Inventory inv = Bukkit.createInventory(p, guiSize, Objects.requireNonNull(guiTitle));
        // glass
        if (!Objects.requireNonNull(guiGlass).equalsIgnoreCase("NONE"))
          for (int i = 0; i < guiSize; i++)
            inv.setItem(
                i, new ItemBuilder(new ItemStack(Material.valueOf(guiGlass)), " ", null).getItem());
        // insert items
        inv.setItem(
            ChestSort.configYml.get().getInt("gui.items.chest.slot") - 1,
            new ItemBuilder(
                    new ItemStack(
                        Material.valueOf(
                            ChestSort.configYml.get().getString("gui.items.chest.material"))),
                    ChestSort.configYml.get().getString("gui.items.chest.name"),
                    Objects.requireNonNull(
                            ChestSort.configYml.get().getString("gui.items.chest.lore"))
                        .replace("%x%", String.valueOf(loc.getBlockX()))
                        .replace("%y%", String.valueOf(loc.getBlockY()))
                        .replace("%z%", String.valueOf(loc.getBlockZ())))
                .getItem());
        inv.setItem(
            ChestSort.configYml.get().getInt("gui.items.sort_A_Z.slot") - 1,
            new ItemBuilder(
                    new ItemStack(
                        Material.valueOf(
                            ChestSort.configYml.get().getString("gui.items.sort_A_Z.material"))),
                    ChestSort.configYml.get().getString("gui.items.sort_A_Z.name"),
                    Objects.requireNonNull(
                        ChestSort.configYml.get().getString("gui.items.sort_A_Z.lore")))
                .getItem());
        inv.setItem(
            ChestSort.configYml.get().getInt("gui.items.sort_amount.slot") - 1,
            new ItemBuilder(
                    new ItemStack(
                        Material.valueOf(
                            ChestSort.configYml.get().getString("gui.items.sort_amount.material"))),
                    ChestSort.configYml.get().getString("gui.items.sort_amount.name"),
                    Objects.requireNonNull(
                        ChestSort.configYml.get().getString("gui.items.sort_amount.lore")))
                .getItem());
        // play sound
        if (!Objects.requireNonNull(ChestSort.configYml.get().getString("sounds.openGui"))
            .equalsIgnoreCase("NONE"))
          p.playSound(
              p.getLocation(),
              Sound.valueOf(ChestSort.configYml.get().getString("sounds.openGui")),
              1,
              1);
        // open inventory
        p.openInventory(inv);
      }
    } catch (Exception ignored) {
    }
  }
}
