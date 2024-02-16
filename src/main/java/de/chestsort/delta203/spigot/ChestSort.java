package de.chestsort.delta203.spigot;

import de.chestsort.delta203.spigot.files.FileManager;
import de.chestsort.delta203.spigot.listeners.Interact;
import de.chestsort.delta203.spigot.listeners.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestSort extends JavaPlugin {

  public static ChestSort plugin;
  public static String prefix = "§f[§eChestSort§f] §7";
  public static FileManager configYml;

  @Override
  public void onEnable() {
    plugin = this;
    configYml = new FileManager("config.yml");
    configYml.create();
    configYml.load();
    Bukkit.getPluginManager().registerEvents(new Interact(), this);
    Bukkit.getPluginManager().registerEvents(new Inventory(), this);
    Bukkit.getConsoleSender().sendMessage(prefix + "§aPlugin successfully loaded.");
  }
}
