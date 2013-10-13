package com.aet2505.signannouncer.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.aet2505.signannouncer.Main;

public class SignDataFile
{
	private Main plugin;
	
	private FileConfiguration signData = null;
	   
    private static File signDataFile = null;
    
    public SignDataFile(Main instance)
    {
    	plugin = instance;
    }
    
    public void reloadFile()
    {
    	if (signDataFile == null) {
    		signDataFile = new File(plugin.getDataFolder(), "signdata.yml");
    	}
    	
    	signData = YamlConfiguration.loadConfiguration(signDataFile);
    	
    	InputStream defaultConfigStream = plugin.getResource("signdata.yml");
    	
    	if (defaultConfigStream != null) 
    	{
    		YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
    		
    		signData.setDefaults(defaultConfig);
    	}
    }
    
    public FileConfiguration getFile()
    {
    	if (this.signData == null) {
    		reloadFile();
    	}
    	return this.signData;
    }
    
    public void saveFile()
    {
      try
      {
        getFile().save(signDataFile);
      } catch (IOException e) {
        plugin.getLogger().log(Level.SEVERE, "[Sign Announcer] Could not save sign data!", e);
      }
    }
  
    public void saveDefaultFile()
    {
      if (!signDataFile.exists())
        plugin.saveResource("signdata.yml", false);
    }
}