package de.chestsort.delta203.spigot.utils;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemBuilder {

  private final ItemStack item;
  private final String name;
  private final String lore;

  public ItemBuilder(ItemStack item, String name, String lore) {
    this.item = item;
    this.name = name;
    this.lore = lore;
  }

  public ItemStack getItem() {
    ItemMeta meta = item.getItemMeta();
    assert meta != null;
    meta.setDisplayName(name);
    if (lore != null) {
      String[] la = lore.split("%s%");
      ArrayList<String> a = new ArrayList<>(Arrays.asList(la));
      meta.setLore(a);
    }
    meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
    item.setItemMeta(meta);
    return item;
  }
}
