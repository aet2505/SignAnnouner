package com.aet2505.signannouncer.sign;

import org.bukkit.World;
import org.bukkit.block.Block;

public class AnnouncerSign
{
	Block block;
	int currentIndex;
	String set;
	
	public AnnouncerSign(Block block, int currentIndex, String set)
	{
		this.block = block;
		this.currentIndex = currentIndex;
		this.set = set;
	}
	
	public AnnouncerSign(World world, int x, int y, int z, int currentIndex, String set)
	{
		this(world.getBlockAt(x, y, z), currentIndex, set);
	}

	public Block getBlock()
	{
		return block;
	}

	public int getCurrentIndex()
	{
		return currentIndex;
	}

	public void setCurrentIndex(int newIndex)
	{
		currentIndex = newIndex;
	}
	
	public String getSet()
	{
		return set;
	}
}
