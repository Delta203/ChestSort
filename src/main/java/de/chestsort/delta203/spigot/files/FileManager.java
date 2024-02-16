package de.chestsort.delta203.spigot.files;

import de.chestsort.delta203.spigot.ChestSort;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class FileManager {

  private final String filename;
  private final File file;
  private FileConfiguration cfg;

  public FileManager(String filename) {
    this.filename = filename;
    file = new File(ChestSort.plugin.getDataFolder(), filename);
  }

  public void create() {
    if (!ChestSort.plugin.getDataFolder().exists()) {
      ChestSort.plugin.getDataFolder().mkdir();
    }
    try {
      if (!file.exists()) {
        Files.copy(Objects.requireNonNull(ChestSort.plugin.getResource(filename)), file.toPath());
      }
      cfg = YamlConfiguration.loadConfiguration(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void load() {
    try {
      cfg.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }
  }

  public void save() {
    try {
      cfg.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Configuration get() {
    return cfg;
  }
}
