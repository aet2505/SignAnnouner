package com.aet2505.signannouncer.sign;

import org.bukkit.ChatColor;

public class SignMessage
{
	String line1, line2, line3, line4, interactMessage;
	
	public SignMessage(String line1, String line2, String line3, String line4, String interactMessage)
	{
		this.line1 = ChatColor.translateAlternateColorCodes('&', line1);
		this.line2 = ChatColor.translateAlternateColorCodes('&', line2);
		this.line3 = ChatColor.translateAlternateColorCodes('&', line3);
		this.line4 = ChatColor.translateAlternateColorCodes('&', line4);
		
		this.interactMessage = ChatColor.translateAlternateColorCodes('&', interactMessage);
	}

	public String getLine1()
	{
		return line1;
	}
	
	public String getLine2()
	{
		return line2;
	}
	
	public String getLine3()
	{
		return line3;
	}
	
	public String getLine4()
	{
		return line4;
	}

	public String getInteractMessage()
	{
		return interactMessage;
	}
}
