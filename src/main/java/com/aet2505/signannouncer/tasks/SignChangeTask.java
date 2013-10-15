package com.aet2505.signannouncer.tasks;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

import com.aet2505.signannouncer.Main;
import com.aet2505.signannouncer.sign.AnnouncerSign;
import com.aet2505.signannouncer.sign.SignMessage;

public class SignChangeTask extends BukkitRunnable
{

	public void run()
	{
		Bukkit.getLogger().info("Updating Signs");
		for (AnnouncerSign aSign : Main.signs)
		{
			int newIndex = aSign.getCurrentIndex() + 1;
			
			if (!Main.messageMap.containsKey(aSign.getSet()))
			{
				Bukkit.getLogger().severe("That Message set does not exist. Please check your config" + aSign.getSet());
			}
			
			if (newIndex == Main.messageMap.get(aSign.getSet()).size())	
			{
				newIndex = 0;
			}	
			
			SignMessage message = Main.messageMap.get(aSign.getSet()).get(newIndex);
			
			aSign.setCurrentIndex(newIndex);
			
			Block block = aSign.getBlock();
			if (block.getState() instanceof Sign) 
			{
				Sign sign = (Sign) block.getState();
				sign.setLine(0, message.getLine1());
				sign.setLine(1, message.getLine2());
				sign.setLine(2, message.getLine3());
				sign.setLine(3, message.getLine4());
				sign.update();
			}
			else
			{
				Bukkit.getLogger().severe("A Sign is missing at " + block.getX() + ", " + block.getY() + ", " + block.getZ());
			}
		}
	}

}
