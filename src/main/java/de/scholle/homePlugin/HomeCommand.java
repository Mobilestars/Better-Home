package de.scholle.homePlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    private final HomePlugin plugin;

    public HomeCommand(HomePlugin plugin) {
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

        Location home = plugin.getHome(player, name);
        if (home == null) {
            player.sendMessage(ChatColor.RED + "That home does not exist!");
            return true;
        }

        if (!plugin.isSafe(home)) {
            plugin.sendActionBar(player, "Area is unsafe!");
            return true;
        }

        player.teleport(home);
        player.sendMessage(ChatColor.GREEN + "Teleported to your home " + (name.isEmpty() ? "" : "'" + name + "'") + "!");
        return true;
    }
}
