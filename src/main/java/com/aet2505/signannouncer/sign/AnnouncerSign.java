package com.aet2505.signannouncer.sign;

import org.bukkit.World;
import org.bukkit.block.Block;

public class AnnouncerSign
{
	Block block;
	int currentIndex;
	
	public AnnouncerSign(Block block, int currentIndex)
	{
		this.block = block;
		this.currentIndex = currentIndex;
	}
	
	public AnnouncerSign(World world, int x, int y, int z, int currentIndex)
	{
		this(world.getBlockAt(x, y, z), currentIndex);
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
}
