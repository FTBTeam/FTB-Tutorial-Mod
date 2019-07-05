package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.ftblib.lib.gui.GuiHelper;
import com.feed_the_beast.ftblib.lib.icon.Color4I;
import com.google.gson.JsonObject;

/**
 * @author LatvianModder
 */
public class BorderLayer extends TutorialLayer
{
	public Color4I color;
	public int size;

	public BorderLayer(TutorialPage p)
	{
		super(p);
		color = Color4I.RED;
		size = 1;
	}

	@Override
	public void readProperties(JsonObject o)
	{
		super.readProperties(o);

		if (o.has("color"))
		{
			color = Color4I.fromJson(o.get("color"));
		}

		if (o.has("size"))
		{
			size = o.get("size").getAsInt();
		}
	}

	@Override
	public void draw(double x, double y, double w, double h)
	{
		int bx = (int) x;
		int by = (int) y;
		int bw = (int) w;
		int bh = (int) h;

		for (int i = 0; i < size; i++)
		{
			GuiHelper.drawHollowRect(bx - i, by - i, bw + i * 2, bh + i * 2, color, false);
		}
	}
}