package com.aet2505.signannouncer.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aet2505.signannouncer.Main;
import com.aet2505.signannouncer.sign.AnnouncerSign;
import com.aet2505.signannouncer.sign.SignMessage;

public class SignListener implements Listener
{
	private Main plugin;
	
	public SignListener(Main instance)
	{
		plugin = instance;
	}
	
	@EventHandler
	public void handle(SignChangeEvent evt)
	{
		if (!evt.getLine(0).equalsIgnoreCase("[SA]"))
		{
			return;
		}
		
		if (!evt.getPlayer().hasPermission("signannouncer.create"))
		{
			evt.getPlayer().sendMessage(ChatColor.RED + "Im sorry you do not have permission to create SignAnnouncements: You are missing the node signannouncer.create");
			evt.setCancelled(true);
			return;
		}
		
		SignMessage message = Main.messages.get(Main.currentIndex);
		
		evt.setLine(0, message.getLine1());
		evt.setLine(1, message.getLine2());
		evt.setLine(2, message.getLine3());
		evt.setLine(3, message.getLine4());
		
		evt.getPlayer().sendMessage(ChatColor.GREEN + "You have sucessfully created a SignAnnouncement");
		
		Main.signs.add(new AnnouncerSign(evt.getBlock(), Main.currentIndex));
	}
	
	@EventHandler
	public void handle(BlockBreakEvent evt)
	{
		Block block = evt.getBlock();
		if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) 
		{
			if (isSignAnnouncer(block))
			{
				if (!evt.getPlayer().hasPermission("signannouncer.destroy"))
				{
					evt.getPlayer().sendMessage(ChatColor.RED + "Im sorry you do not have permission to destroy SignAnnouncements: You are missing the node signannouncer.destroy");
					evt.setCancelled(true);
					return;
				}
				
				AnnouncerSign aSign = getSignAnnouncer(block);
				Main.signs.remove(aSign);
				evt.getPlayer().sendMessage(ChatColor.GREEN + "You have sucessfully destroyed a SignAnnouncement");
			}
		}
	}
	
	@EventHandler
	public void handle(PlayerInteractEvent evt)
	{
		if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			Block block = evt.getClickedBlock();
			if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) 
			{
				if (isSignAnnouncer(block))
				{
					AnnouncerSign aSign = getSignAnnouncer(block);
					int index = aSign.getCurrentIndex();
					
					evt.getPlayer().sendMessage(Main.messages.get(index).getInteractMessage());
				}
			}
		}
	}

	private boolean isSignAnnouncer(Block block)
	{
		for (AnnouncerSign sign : Main.signs)
		{
			if (sign.getBlock().equals(block))
			{
				return true;
			}
		}
		return false;
	}
	
	private AnnouncerSign getSignAnnouncer(Block block)
	{
		for (AnnouncerSign sign : Main.signs)
		{
			if (sign.getBlock().equals(block))
			{
				return sign;
			}
		}
		return null;
	}
}