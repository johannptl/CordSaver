package com.pagchake.position.commanda;

import com.pagchake.position.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                File file = new File("plugins//CordsConfig//" + sender.getName() + ".yml");
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
				player.sendMessage("§6--=={ All Cords }==--");
				player.sendMessage(" ");
				for(String current : yamlConfiguration.getKeys(true)) {
                    if(yamlConfiguration.getList(current).get(3).equals("world_nether")) {
                        player.sendMessage("§8- §e" + current + " §8| §4" + yamlConfiguration.getList(current).get(0) + "§8, §4" + yamlConfiguration.getList(current).get(1) + "§8, §4" + yamlConfiguration.getList(current).get(2));
                    } else if (yamlConfiguration.getList(current).get(3).equals("world_the_end")) {
                        player.sendMessage("§8- §e" + current + " §8| §5" + yamlConfiguration.getList(current).get(0) + "§8, §5" + yamlConfiguration.getList(current).get(1) + "§8, §5" + yamlConfiguration.getList(current).get(2));
                    } else {
                        player.sendMessage("§8- §e" + current + " §8| §2" + yamlConfiguration.getList(current).get(0) + "§8, §2" + yamlConfiguration.getList(current).get(1) + "§8, §2" + yamlConfiguration.getList(current).get(2));
                    }
				}
                sender.sendMessage(" ");
            } else if (args.length == 2) {
                File file = new File("plugins//CordsConfig//" + sender.getName() + ".yml");
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

                switch (args[0].toLowerCase()) {
                    case "save": {
                        List<String> cords = new ArrayList<>();
                        cords.add(player.getLocation().getBlockX() + "");
                        cords.add(player.getLocation().getBlockY() + "");
                        cords.add(player.getLocation().getBlockZ() + "");
                        cords.add(player.getLocation().getWorld().getName());
                        yamlConfiguration.set(args[1], cords);
                        try {
                            if(yamlConfiguration.getList(args[1]).get(3).equals("world_nether")) {
                                player.sendMessage("Cords saved as §e" + args[1] + "§f at X:§e " + player.getLocation().getBlockX() + " §fY: §e" + player.getLocation().getBlockY() + " §fZ: §e" + player.getLocation().getBlockZ() + "§f in the dimension §4Nether");
                            } else if (yamlConfiguration.getList(args[1]).get(3).equals("world_the_end")) {
                                player.sendMessage("Cords saved as §e" + args[1] + "§f at X:§e " + player.getLocation().getBlockX() + " §fY: §e" + player.getLocation().getBlockY() + " §fZ: §e" + player.getLocation().getBlockZ() + "§f in the dimension §5The End");
                            } else {
                                player.sendMessage("Cords saved as §e" + args[1] + "§f at X:§e " + player.getLocation().getBlockX() + " §fY: §e" + player.getLocation().getBlockY() + " §fZ: §e" + player.getLocation().getBlockZ() + "§f in the dimension §2Overworld");
                            }
                            yamlConfiguration.save(file);
                        } catch (IOException e) {
                            player.sendMessage("§7An error occurred while saving the cords.");
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "show": {
                        buildbar(player, args[1],  YamlConfiguration.loadConfiguration(file));
                        break;
                    }
                    case "get": {
                        if(yamlConfiguration.getList(args[1]).get(3).equals("world_nether")) {
                            player.sendMessage("X: §e" + yamlConfiguration.getList(args[1]).get(0) + "§f Y: §e" + yamlConfiguration.getList(args[1]).get(1) + " §fZ: §e" + yamlConfiguration.getList(args[1]).get(2) + "§f Dimension: §4Nether");
                        } else if (yamlConfiguration.getList(args[1]).get(3).equals("world_the_end")) {
                            player.sendMessage("X: §e" + yamlConfiguration.getList(args[1]).get(0) + "§f Y: §e" + yamlConfiguration.getList(args[1]).get(1) + " §fZ: §e" + yamlConfiguration.getList(args[1]).get(2) + "§f Dimension: §5The End");
                        } else {
                            player.sendMessage("X: §e" + yamlConfiguration.getList(args[1]).get(0) + "§f Y: §e" + yamlConfiguration.getList(args[1]).get(1) + " §fZ: §e" + yamlConfiguration.getList(args[1]).get(2) + "§f Dimension: §2Overworld");
                        }
                        break;
                    }
                    case "delete": {
                        yamlConfiguration.set(args[1], null);
                        try {
                            yamlConfiguration.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        player.sendMessage("§7Cords deleted.");
                    }
                }
            } else if (args.length == 1){
                switch (args[0].toLowerCase()) {
                    case "help": {
                        sendUsage(player);
                        break;
                    }
                    case "clear": {
                        removeBar(player);
                        break;
                    }
                }
            } else if (args.length == 3) {
                File file = new File("plugins//CordsConfig//" + sender.getName() + ".yml");
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
                switch (args[0].toLowerCase()) {
                    case "rename": {
                        yamlConfiguration.set(args[2], yamlConfiguration.getList(args[1]));
                        yamlConfiguration.set(args[1], null);
                        try {
                            yamlConfiguration.save(file);
                            player.sendMessage("§7Cords renamed.");
                        } catch (IOException e) {
                            player.sendMessage("§7An error occurred while renameing the cords.");
                            e.printStackTrace();
                        }
                    }
                }
            } else if (args.length == 6) {
                File file = new File("plugins//CordsConfig//" + sender.getName() + ".yml");
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
                switch (args[0].toLowerCase()) {
                    case "add": {
                        List<String> cords = new ArrayList<>();
                        cords.add(args[2] + "");
                        cords.add(args[3] + "");
                        cords.add(args[4] + "");
                        if (args[5].equals("o")){
                            cords.add("world");
                        } else if (args[5].equals("n")) {
                            cords.add("world_nether");
                        } else if (args[5].equals("e")) {
                            cords.add("world_the_end");
                        } else {
                            player.sendMessage("§7An error occurred while saving the cords.");
                            sender.sendMessage("§b/cords add <Name> <X> <Y> <Z> <World (o for Overwolrd, n for Nether, e for The End)>");
                        }
                        yamlConfiguration.set(args[1], cords);
                        try {
                            yamlConfiguration.save(file);
                            if(args[5].equals("n")) {
                                player.sendMessage("Cords saved as §e" + args[1] + "§f at X:§e " + args[2] + " §fY: §e" + args[3] + " §fZ: §e" + args[4] + "§f in the dimension §4Nether");
                            } else if (args[5].equals("e")) {
                                player.sendMessage("Cords saved as §e" + args[1] + "§f at X:§e " + args[2] + " §fY: §e" + args[3] + " §fZ: §e" + args[4] + "§f in the dimension §5The End");
                            } else {
                                player.sendMessage("Cords saved as §e" + args[1] + "§f at X:§e " + args[2] + " §fY: §e" + args[3] + " §fZ: §e" + args[4] + "§f in the dimension §2Overworld");
                            }
                        } catch (IOException e) {
                            player.sendMessage("§7An error occurred while saving the cords.");
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                sendUsage(player);
            }
        }
        return false;
    }

    public void buildbar(Player player, String text, YamlConfiguration yamlConfiguration) {
        BossBar bar;
        NamespacedKey key = new NamespacedKey(Main.getPlugin(), player.getName());
        Bukkit.getBossBar(key).removePlayer(player);

		if(yamlConfiguration.getList(text).get(3).equals("world_nether")) {
        bar = Bukkit.createBossBar(key,"X: §e" + yamlConfiguration.getList(text).get(0) + " §fY: §e" + yamlConfiguration.getList(text).get(1) + " §fZ: §e" + yamlConfiguration.getList(text).get(2), BarColor.RED, BarStyle.SOLID);
       	} else if (yamlConfiguration.getList(text).get(3).equals("world_the_end")) {
        bar = Bukkit.createBossBar(key, "X: §e" + yamlConfiguration.getList(text).get(0) + " §fY: §e" + yamlConfiguration.getList(text).get(1) + " §fZ: §e" + yamlConfiguration.getList(text).get(2), BarColor.PURPLE, BarStyle.SOLID);
        } else {
        bar = Bukkit.createBossBar(key,"X: §e" + yamlConfiguration.getList(text).get(0) + " §fY: §e" + yamlConfiguration.getList(text).get(1) + " §fZ: §e" + yamlConfiguration.getList(text).get(2), BarColor.GREEN, BarStyle.SOLID);
		}
		bar.addPlayer(player);
		bar.setProgress(1);
		bar.setVisible(true);

    }

	public void removeBar(Player player) {

        NamespacedKey key = new NamespacedKey(Main.getPlugin(), player.getName());
		Bukkit.getBossBar(key).removePlayer(player);

	}

    public void sendUsage(Player sender) {
        sender.sendMessage("§6--=={ Usage }==--");
        sender.sendMessage(" ");
        sender.sendMessage("§b/cords §8| §7Lists all saved cords");
		sender.sendMessage("§b/cords help §8| §7Shows this help Message");
		sender.sendMessage("§b/cords save <Name> §8| §7Saves your current position as <Name>");
		sender.sendMessage("§b/cords get <Name> §8| §7Writes the saved position of <Name> in your chat");
		sender.sendMessage("§b/cords show <Name> §8| §7Shows the position of <Name> in your bossbar");
		sender.sendMessage("§b/cords clear §8| §7Lets the bossbar disappear");
        sender.sendMessage("§b/cords rename <Name> <New Name> §8| §7Rename Cords");
		sender.sendMessage("§b/cords delete <Name> §8| §7Deletes the position of <Name>");
        sender.sendMessage("§b/cords add <Name> <X> <Y> <Z> <World (o for Overwolrd, n for Nether, e for The End)> §8| §7Add Custom Cords");
		sender.sendMessage(" ");
    }
}
