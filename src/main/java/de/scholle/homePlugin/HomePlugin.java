package de.scholle.homePlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HomePlugin extends JavaPlugin {

    private static HomePlugin instance;
    private File homesFile;
    private FileConfiguration homesConfig;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        createHomesFile();

        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("delhome").setExecutor(new DelHomeCommand(this));

        getLogger().info("HomePlugin enabled!");
    }

    @Override
    public void onDisable() {
        saveHomesFile();
    }

    public static HomePlugin getInstance() {
        return instance;
    }

    private void createHomesFile() {
        homesFile = new File(getDataFolder(), "homes.yml");
        if (!homesFile.exists()) {
            homesFile.getParentFile().mkdirs();
            try {
                homesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        homesConfig = new YamlConfiguration();
        try {
            homesConfig.load(homesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getHomesConfig() {
        return homesConfig;
    }

    public void saveHomesFile() {
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---- Home management ----

    public void setHome(Player player, String name, Location location) {
        String basePath = "homes." + player.getUniqueId();
        if (name == null || name.isEmpty()) name = "default";
        homesConfig.set(basePath + "." + name + ".world", location.getWorld().getName());
        homesConfig.set(basePath + "." + name + ".x", location.getX());
        homesConfig.set(basePath + "." + name + ".y", location.getY());
        homesConfig.set(basePath + "." + name + ".z", location.getZ());
        homesConfig.set(basePath + "." + name + ".yaw", location.getYaw());
        homesConfig.set(basePath + "." + name + ".pitch", location.getPitch());
        saveHomesFile();
    }

    public Location getHome(Player player, String name) {
        if (name == null || name.isEmpty()) name = "default";
        String path = "homes." + player.getUniqueId() + "." + name;
        if (!homesConfig.contains(path)) return null;

        String world = homesConfig.getString(path + ".world");
        double x = homesConfig.getDouble(path + ".x");
        double y = homesConfig.getDouble(path + ".y");
        double z = homesConfig.getDouble(path + ".z");
        float yaw = (float) homesConfig.getDouble(path + ".yaw");
        float pitch = (float) homesConfig.getDouble(path + ".pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public void deleteHome(Player player, String name) {
        if (name == null || name.isEmpty()) name = "default";
        homesConfig.set("homes." + player.getUniqueId() + "." + name, null);
        saveHomesFile();
    }

    public Set<String> getHomeNames(Player player) {
        String path = "homes." + player.getUniqueId();
        if (homesConfig.contains(path)) {
            return homesConfig.getConfigurationSection(path).getKeys(false);
        }
        return null;
    }

    public int getMaxHomes(Player player) {
        FileConfiguration cfg = getConfig();
        int defaultLimit = cfg.getInt("default-home-limit", 1);

        if (cfg.isConfigurationSection("rank-limits")) {
            for (String group : cfg.getConfigurationSection("rank-limits").getKeys(false)) {
                if (player.hasPermission("betterhome.group." + group.toLowerCase())) {
                    return cfg.getInt("rank-limits." + group);
                }
            }
        }

        return defaultLimit;
    }

    public boolean isSafe(Location loc) {
        if (loc == null || loc.getWorld() == null) return false;
        Material feet = loc.getBlock().getType();
        Material head = loc.clone().add(0, 1, 0).getBlock().getType();
        Material ground = loc.clone().add(0, -1, 0).getBlock().getType();
        return ground.isSolid() && !feet.isSolid() && !head.isSolid();
    }

    public void sendActionBar(Player player, String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendActionBar(ChatColor.RED + message);
            }
        }.runTask(this);
    }
}
