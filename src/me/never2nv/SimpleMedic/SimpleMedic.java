package me.never2nv.SimpleMedic;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class SimpleMedic extends JavaPlugin{
    public static Economy econ = null;
	public final Logger logger = Logger.getLogger("Minecraft)");
	public static SimpleMedic plugin;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info("Congrats! U iz ubber smart! " + pdffile.getName() + " has been disabled!");
	}
	
	@Override
	public void onEnable() {
        
        if (!setupEconomy() ) {
            System.out.println("[Medic] has been disabled because vault is not found");
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info("Well would ya look at that? " + pdffile.getName() + " Version " + pdffile.getVersion() + " has been enabled!");
                getConfig().options().copyDefaults(true);
		saveConfig();
		
        }
    }
	
	public static boolean setupEconomy() {
	    Economy econ;
        //The actual code to search for an economy to use
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		boolean econenabled = plugin.getConfig().getBoolean("econ-enabled");
		int cost = plugin.getConfig().getInt("econ-cost");
		Player player = (Player) sender;
		
		if(commandLabel.equalsIgnoreCase("extme") || commandLabel.equalsIgnoreCase("extinguishme")){
			if(args.length == 0){
				if(player.hasPermission("simplemedic.ext")|| player.hasPermission("simplemedic.*")) {
					player.sendMessage(ChatColor.DARK_RED + "[MEDIC] You have been extinguished!");	
					player.setFireTicks(0);
				} else if(args.length == 1){
					if(player.hasPermission("simplemedic.ext.others")|| player.hasPermission("simplemedic.*")) {
				        Player target = player.getServer().getPlayer(args[0]); 
				        target.setFireTicks(0);
						player.sendMessage(ChatColor.DARK_RED + "[MEDIC] " + target.getName() + " has been extinguished!");
				        target.sendMessage(ChatColor.DARK_RED + "[MEDIC] You have been extinguished!");
					} else {
						player.sendMessage(ChatColor.DARK_RED + "[MEDIC] Error: Incorrect usage.");
					}
				}
			}
		}
		if(commandLabel.equalsIgnoreCase("medic") || commandLabel.equalsIgnoreCase("med")){
            Player targetPlayer = player.getServer().getPlayer(args[0]);

			
			if(args.length == 0){
				if(player.hasPermission("simplemedic.heal")|| player.hasPermission("simplemedic.*")) {
					if(econenabled) {
	                EconomyResponse r = econ.withdrawPlayer(player.getName(), cost);
		                if(r.transactionSuccess()) {
		                    //If the transaction succeeds it heals them else it tells them the error
		                    //healing player
		                    player.setHealth(20);
		                    player.setFireTicks(0);
		                    player.chat("/me " + ChatColor.DARK_RED + "just got healed by a " + ChatColor.GREEN + "Medic!");
		                    player.sendMessage(ChatColor.GREEN + "[MEDIC] " + ChatColor.WHITE + "You've Been Healed!");
		                    
		                } else {
		                    player.sendMessage(String.format("An error occured: %s", r.errorMessage));
		                }
					} else {
			            player.setHealth(20);
		                player.setFireTicks(0);
		                player.chat("/me " + ChatColor.DARK_RED + "just got healed by a " + ChatColor.GREEN + "Medic!");
		                player.sendMessage(ChatColor.GREEN + "[MEDIC] " + ChatColor.WHITE + "You've Been Healed!");	
					}
				}
			} else if(args.length == 1){
                if(player.hasPermission("simplemedic.heal.others") || player.hasPermission("simplemedic.*") ) {
	               	if(econenabled) {
	               		EconomyResponse r = econ.withdrawPlayer(player.getName(), cost);
			            if(r.transactionSuccess()) {
			            	//See above :D
			                if(player.getServer().getPlayer(args[0]) !=null){
			                	targetPlayer.setHealth(20);
			                    targetPlayer.setFireTicks(0);
			                    targetPlayer.getBedSpawnLocation();
			                    player.chat("/me " + ChatColor.DARK_RED + "just got healed by a " + ChatColor.GREEN + "Medic!");
			                    player.sendMessage(ChatColor.GREEN + "[MEDIC] " + ChatColor.WHITE + "You've Been Healed!");
			                    return true;
			                } else {
			                	// checks to see if player is online, else displays this message!
			                    player.sendMessage(ChatColor.RED + "PLAYER IS NOT ONLINE!");
			                    return true;
			                } 
			            }  else {
			              	player.sendMessage(String.format("An error occured: %s", r.errorMessage));
			            }
			        } else {
			          	targetPlayer.setHealth(20);
			            targetPlayer.setFireTicks(0);
			            targetPlayer.getBedSpawnLocation();
			            player.chat("/me " + ChatColor.DARK_RED + "just got healed by a " + ChatColor.GREEN + "Medic!");
			            player.sendMessage(ChatColor.GREEN + "[MEDIC] " + ChatColor.WHITE + "You've Been Healed!");	
			        }
                }
            }
			return false;
		}
		return false;
	}
}

