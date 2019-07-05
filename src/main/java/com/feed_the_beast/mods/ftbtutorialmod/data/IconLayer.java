package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.ftblib.lib.icon.Icon;

/**
 * @author LatvianModder
 */
public class IconLayer extends TutorialLayer
{
	public Icon icon;

	public IconLayer(TutorialPage p, Icon i)
	{
		super(p);
		icon = i;
	}

	@Override
	public void draw(double x, double y, double w, double h)
	{
		icon.draw((int) x, (int) y, (int) w, (int) h);
	}
}