package me.never2nv.SimpleMedic;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleMedic extends JavaPlugin{
	public final Logger logger = Logger.getLogger("Minecraft)");
	public static SimpleMedic plugin;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info("Congrats! U iz ubber smart! " + pdffile.getName() + " has been disabled!");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info("Well would ya look at that? " + pdffile.getName() + " Version " + pdffile.getVersion() + " has been enabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		
		if(commandLabel.equalsIgnoreCase("medic") || commandLabel.equalsIgnoreCase("med")){

			if(args.length == 0){
				//healing player
				player.setHealth(20);
				player.setFireTicks(0);
				player.setFoodLevel(20);
				player.chat("/me " + ChatColor.DARK_RED + "just got healed by a " + ChatColor.GREEN + "Medic!");
				player.sendMessage(ChatColor.GREEN + "[MEDIC] " + ChatColor.WHITE + "You've Been Healed!");
			
			}
			
			else if(args.length == 1){
				// Fixed internal errors, as before it didn't check IF player was online.
				if(player.getServer().getPlayer(args[0]) !=null){
				Player targetPlayer = player.getServer().getPlayer(args[0]);
				targetPlayer.setHealth(20);
				targetPlayer.setFireTicks(0);
				targetPlayer.setFoodLevel(20);
				// Holding for future update, may tp healed players to bed as if they woke up after being healed ;)
				// for rpg servers and such!
				// ## targetPlayer.getBedSpawnLocation();
				player.chat("/me " + ChatColor.DARK_RED + "just got healed by a " + ChatColor.GREEN + "Medic!");
				player.sendMessage(ChatColor.GREEN + "[MEDIC] " + ChatColor.WHITE + "You've Been Healed!");
			}
				else{
				// checks to see if player is online, else displays this message!
				player.sendMessage(ChatColor.RED + "PLAYER IS NOT ONLINE!");
			}
				
		return false;
		}
			return false;
		
	}
		return false;
}
	
	
	// Mising quite a few things from the aruguments and statements. Will tend to later. Too tired :p
	
	public boolean onCommand1(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		
		
		if(commandLabel.equalsIgnoreCase("burn") || commandLabel.equalsIgnoreCase("toast")){
			
		if(args.length == 0){
			//burning player
			player.setFireTicks(20);
			player.setFoodLevel(0);
			player.chat("/me " + ChatColor.DARK_RED + "just set themselves on " + ChatColor.RED + "FIRE!");
			player.sendMessage(ChatColor.GREEN + "BUUUUURN! " + ChatColor.WHITE + "You've Gone Up In Flames!");
	
}
		else if(args.length == 1){
			// Fixed internal errors, as before it didn't check IF player was online.
			if(player.getServer().getPlayer(args[0]) !=null){
				
			Player targetPlayer = player.getServer().getPlayer(args[0]);
			targetPlayer.setFireTicks(20);
			targetPlayer.setFoodLevel(0);
			
			
			// Holding for future update, may tp healed players to bed as if they woke up after being healed ;)
			// for rpg servers and such!
			// ## targetPlayer.getBedSpawnLocation();
			player.chat("/me " + ChatColor.RED + "just set " + ChatColor.DARK_RED + targetPlayer + ChatColor.RED + "ON FIRE!");
			player.sendMessage(ChatColor.GREEN + "BUUUUUURN! " + ChatColor.WHITE + "They're not toast!");
		}
			
			else{
				
			// checks to see if player is online, else displays this message!
			player.sendMessage(ChatColor.RED + "PLAYER IS NOT ONLINE!");
		
		
	}
}
	}
		return false;
}
}

