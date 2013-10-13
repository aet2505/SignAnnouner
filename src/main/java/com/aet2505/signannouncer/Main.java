package com.aet2505.signannouncer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.aet2505.signannouncer.files.SignDataFile;
import com.aet2505.signannouncer.listeners.SignListener;
import com.aet2505.signannouncer.sign.AnnouncerSign;
import com.aet2505.signannouncer.sign.SignMessage;
import com.aet2505.signannouncer.tasks.SignChangeTask;

public class Main extends JavaPlugin
{
	public static Main plugin;
	
	public static int currentIndex = 0;
	
	public static int timeBetweenChanges;
	public static List<SignMessage> messages = new ArrayList<SignMessage>();
	public static List<AnnouncerSign> signs = new ArrayList<AnnouncerSign>();
	
	private SignDataFile signData;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		
		signData = new SignDataFile(this);
		loadSignData();
		
		saveDefaultConfig();
		loadConfig();
		
		registerTasks();
		registerListeners();
	}
	
	private void loadSignData()
	{
		getSignDataFile().getFile().options().copyDefaults(true);
		getSignDataFile().saveDefaultFile();
		getLogger().info("[Sign Announcer] signData.yml loaded!");
		
		FileConfiguration file = getSignDataFile().getFile();
		
		if(!file.isConfigurationSection("signs"))
		{
			return;
		}
		
		for (String sec : file.getConfigurationSection("signs").getKeys(false))
		{
			int x, y, z;
			World world;
			int index;
			
			x = file.getInt("signs." + sec + ".x");
			y = file.getInt("signs." + sec + ".y");
			z = file.getInt("signs." + sec + ".z");
			
			world = Bukkit.getWorld(file.getString("signs." + sec + ".world"));
			
			index = file.getInt("signs." + sec + ".index");
			
			signs.add(new AnnouncerSign(world, x, y, z, index));
		}
		
	}

	private SignDataFile getSignDataFile()
	{
		return signData;
	}

	private void loadConfig()
	{
		timeBetweenChanges = getConfig().getInt("time-between-change");
		
		for (String sec : getConfig().getConfigurationSection("messages").getKeys(false))
		{
			String line1, line2, line3, line4, interactMessage;
			
			line1 = getConfig().getString("messages." + sec + ".line-1");
			line2 = getConfig().getString("messages." + sec + ".line-2");
			line3 = getConfig().getString("messages." + sec + ".line-3");
			line4 = getConfig().getString("messages." + sec + ".line-4");
			
			interactMessage = getConfig().getString("messages." + sec + ".interact-message");
			
			messages.add(new SignMessage(line1, line2, line3, line4, interactMessage));
		}
	}

	private void registerListeners()
	{
		getServer().getPluginManager().registerEvents(new SignListener(this), plugin);
		
	}

	private void registerTasks()
	{
		getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new SignChangeTask(), timeBetweenChanges*20L, timeBetweenChanges*20L);
		
	}

	@Override
	public void onDisable()
	{
		saveSignData();
	}

	private void saveSignData()
	{
		int i = 0;
		FileConfiguration file = getSignDataFile().getFile();
		for (AnnouncerSign sign : signs)
		{
			file.set("signs." + i + ".world", sign.getBlock().getWorld().getName());
			file.set("signs." + i + ".x", sign.getBlock().getX());
			file.set("signs." + i + ".y", sign.getBlock().getY());
			file.set("signs." + i + ".z", sign.getBlock().getZ());
			
			file.set("signs." + i + ".index", sign.getCurrentIndex());
			i ++;
		}
		
		getSignDataFile().saveFile();
		getLogger().info("[Sign Announcer] Sign Data Saved!");
	}
}
