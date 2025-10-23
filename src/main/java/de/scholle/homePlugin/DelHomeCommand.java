package de.scholle.homePlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {

    private final HomePlugin plugin;

    public DelHomeCommand(HomePlugin plugin) {
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

        if (plugin.getHome(player, name) == null) {
            player.sendMessage(ChatColor.RED + "That home doesn't exist!");
            return true;
        }

        plugin.deleteHome(player, name);
        player.sendMessage(ChatColor.YELLOW + "Deleted home " + (name.isEmpty() ? "" : "'" + name + "'") + ".");
        return true;
    }
}
