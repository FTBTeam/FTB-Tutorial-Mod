package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.ftblib.lib.icon.Color4I;
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
	public final List<TutorialLayer> layers;
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