package com.feed_the_beast.mods.ftbtutorialmod.data;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class TutorialPage
{
	public final Tutorial tutorial;
	public final List<TutorialLayer> layers;
	public String description;
	public ResourceLocation background;
	public Color4I border;
	public double width;
	public double height;

	public TutorialPage(Tutorial t)
	{
		tutorial = t;
		description = "";
		layers = new ArrayList<>();
		background = null;
		border = Color4I.EMPTY;
		width = 256D;
		height = 256D;
	}
}