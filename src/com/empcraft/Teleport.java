package com.empcraft;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Teleport extends JavaPlugin implements Listener {
	public static int counter = 0;
	
	final Map<String, Object> teleporting = new HashMap<String, Object>();
	@EventHandler
	void PlayerTeleportEvent(PlayerTeleportEvent event){ 
		Player player = event.getPlayer();
		// if random TP is enabled.
	}
	
	TimerTask mytask = new TimerTask () {
		@Override
	    public void run () {
			counter++;
		}
	};
	
	public String getmsg(String key) {
		File yamlFile = new File(getDataFolder(), getConfig().getString("language").toLowerCase()+".yml"); 
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(yamlFile);
		try {
			return colorise(YamlConfiguration.loadConfiguration(yamlFile).getString(key));
		}
		catch (Exception e){
			return "";
		}
	}
    public String colorise(String mystring) {
    	String[] codes = {"&1","&2","&3","&4","&5","&6","&7","&8","&9","&0","&a","&b","&c","&d","&e","&f","&r","&l","&m","&n","&o","&k"};
    	for (String code:codes) {
    		mystring = mystring.replace(code, "§"+code.charAt(1));
    	}
    	return mystring;
    }
    public boolean checkperm(Player player,String perm) {
    	boolean hasperm = false;
    	String[] nodes = perm.split("\\.");
    	
    	String n2 = "";
    	if (player.hasPermission(perm)) {
    		hasperm = true;
    	}
    	else if (player.isOp()==true) {
    		hasperm = true;
    	}
    	else {
    		for(int i = 0; i < nodes.length-1; i++) {
    			n2+=nodes[i]+".";
            	if (player.hasPermission(n2+"*")) {
            		hasperm = true;
            	}
    		}
    	}
		return hasperm;
    }
    public void msg(Player player,String mystring) {
    	if (player==null) {
    		getServer().getConsoleSender().sendMessage(colorise(mystring));
    	}
    	else if (player instanceof Player==false) {
    		getServer().getConsoleSender().sendMessage(colorise(mystring));
    	}
    	else {
    		player.sendMessage(colorise(mystring));
    	}

    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
    	String line = "";
    	for (String i:args) {
    		line+=i+" ";
    	}
    	if (cmd.getName().equalsIgnoreCase("random")) {
    		Random random = new Random();
    		int locx = random.nextInt(30000000);
			int locy = random.nextInt(30000000);
    		Location myloc = new Location(player.getWorld(),locx,64,locy);
    		Chunk chunk = myloc.getChunk();
    		chunk.load(true);
    		Location myloc2 = new Location(player.getWorld(),locx+16,64,locy); myloc2.getChunk().load(true); player.getWorld().refreshChunk(myloc2.getChunk().getX(),myloc2.getChunk().getZ());
    		Location myloc3 = new Location(player.getWorld(),locx-16,64,locy); myloc3.getChunk().load(true); player.getWorld().refreshChunk(myloc3.getChunk().getX(),myloc3.getChunk().getZ());
    		Location myloc4 = new Location(player.getWorld(),locx,64,locy+16); myloc4.getChunk().load(true); player.getWorld().refreshChunk(myloc4.getChunk().getX(),myloc4.getChunk().getZ());
    		Location myloc5 = new Location(player.getWorld(),locx,64,locy-16); myloc5.getChunk().load(true); player.getWorld().refreshChunk(myloc5.getChunk().getX(),myloc5.getChunk().getZ());
    		Location myloc6 = new Location(player.getWorld(),locx+16,64,locy+16); myloc6.getChunk().load(true); player.getWorld().refreshChunk(myloc6.getChunk().getX(),myloc6.getChunk().getZ());
    		Location myloc7 = new Location(player.getWorld(),locx-16,64,locy-16); myloc7.getChunk().load(true); player.getWorld().refreshChunk(myloc7.getChunk().getX(),myloc7.getChunk().getZ());
    		Location myloc8 = new Location(player.getWorld(),locx-16,64,locy+16); myloc8.getChunk().load(true); player.getWorld().refreshChunk(myloc8.getChunk().getX(),myloc8.getChunk().getZ());
    		Location myloc9 = new Location(player.getWorld(),locx+16,64,locy-16); myloc9.getChunk().load(true); player.getWorld().refreshChunk(myloc9.getChunk().getX(),myloc9.getChunk().getZ());
    		player.getWorld().loadChunk(chunk);
    		 player.getWorld().refreshChunk(myloc.getChunk().getX(),myloc.getChunk().getZ());
    		Location loc = new Location(player.getWorld(),locx,player.getWorld().getHighestBlockAt(myloc).getY()+2,locy);
    		while(!chunk.isLoaded()){
    			chunk.load();
			}
    		msg(player,""+loc.getX()+", "+loc.getZ());
    		player.teleport(loc);
    		player.teleport(loc);
    	}
    	return true;
	}
	
	@Override
    public void onEnable(){
		saveResource("english.yml", true);
        getConfig().options().copyDefaults(true);
        final Map<String, Object> options = new HashMap<String, Object>();
        getConfig().set("version", "0.0.1");
        options.put("language","english");
	}
	
}
