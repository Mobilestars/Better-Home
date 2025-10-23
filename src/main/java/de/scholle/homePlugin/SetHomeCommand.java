package de.scholle.homePlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class SetHomeCommand implements CommandExecutor {

    private final HomePlugin plugin;

    public SetHomeCommand(HomePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        String name = (args.length > 0) ? args[0].toLowerCase() : "";

        Set<String> homes = plugin.getHomeNames(player);
        int currentHomes = (homes != null) ? homes.size() : 0;
        int maxHomes = plugin.getMaxHomes(player);

        if (!homes.contains(name) && currentHomes >= maxHomes) {
            player.sendMessage(ChatColor.RED + "You have reached your home limit (" + maxHomes + ").");
            return true;
        }

        Location loc = player.getLocation();
        plugin.setHome(player, name, loc);

        player.sendMessage(ChatColor.GREEN + "Home " + (name.isEmpty() ? "" : "'" + name + "' ") + "set successfully!");
        return true;
    }
}
