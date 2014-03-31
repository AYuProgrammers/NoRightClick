package pl.AYuPro.NoRightClick;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class NoRightClick extends JavaPlugin implements Listener {
	
	Plugin bllim = this;
	public static List<Integer> ilist;
	String arg;
	WorldGuardPlugin wgPlugin = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
	
	public void onEnable(){
		bllim.getConfig().options().copyDefaults(true);
		bllim.saveDefaultConfig();
		ilist = this.getConfig().getIntegerList("itemid");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("norclist")) {
			if (!(sender.isOp())){
				sender.sendMessage(ChatColor.RED + "У Вас не достаточно прав.");
				return false;
			} else {
				sender.sendMessage(ilist.toString());
			}
		}
		return true;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerClickEvent(PlayerInteractEvent event) {
		 if((event.getAction() == Action.RIGHT_CLICK_BLOCK)){
			if (!wgPlugin.canBuild(event.getPlayer(), event.getClickedBlock())){
				if (ilist.contains(event.getPlayer().getItemInHand().getTypeId())){
					event.setCancelled(true);
				}
			}
		 }
	}
}