package de.chestsort.delta203.spigot.utils;

import de.chestsort.delta203.spigot.SortType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SortAPI {

  /**
   * Sort specific inventory based on {@link SortType}.
   *
   * @param type the type how to be sorted
   * @param inventory the inventory to be sorted
   */
  public static void sortInventory(SortType type, Inventory inventory) {
    // store items in list
    HashMap<String, ArrayList<ItemStack>> itemMap = new HashMap<>();
    for (ItemStack content : inventory.getContents()) {
      try {
        if (content == null) continue;
        // add amount of items before content to compare amounts
        String key =
            type == SortType.AMOUNT ? content.getAmount() + content.toString() : content.toString();
        ArrayList<ItemStack> items;
        if (!itemMap.containsKey(key)) items = new ArrayList<>();
        else items = itemMap.get(key);
        items.add(content);
        itemMap.put(key, items);
      } catch (Exception ignored) {
      }
    }
    // sort items
    ArrayList<String> sortedItems = new ArrayList<>(itemMap.keySet());
    if (type == SortType.A_Z) {
      Collections.sort(sortedItems);
    } else {
      sortedItems.sort(
          new Comparator<>() {
            // first compare numbers, then compare alphabet
            @Override
            public int compare(String s1, String s2) {
              String[] part1 = splitString(s1);
              String[] part2 = splitString(s2);
              int numComparison =
                  Integer.compare(Integer.parseInt(part2[1]), Integer.parseInt(part1[1]));
              if (numComparison != 0) return numComparison;
              return part1[0].compareTo(part2[0]);
            }

            private String[] splitString(String s) {
              String[] parts = new String[2];
              StringBuilder numbers = new StringBuilder();
              StringBuilder letters = new StringBuilder();
              for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) numbers.append(c);
                else letters.append(c);
              }
              parts[0] = letters.toString();
              parts[1] = numbers.toString();
              return parts;
            }
          });
    }
    // insert chest items
    inventory.clear();
    for (String key : sortedItems)
      for (ItemStack content : itemMap.get(key)) inventory.addItem(content);
  }
}
