package com.feed_the_beast.mods.ftbtutorialmod;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class TutorialPage
{
	public final Tutorial tutorial;
	public String description;
	public final List<ResourceLocation> layers;

	public TutorialPage(Tutorial t)
	{
		tutorial = t;
		description = "";
		layers = new ArrayList<>();
	}
}