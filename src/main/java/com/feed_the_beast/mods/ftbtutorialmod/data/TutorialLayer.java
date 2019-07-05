package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.google.gson.JsonObject;

/**
 * @author LatvianModder
 */
public abstract class TutorialLayer
{
	public final TutorialPage page;
	public double posX, posY;
	public double width, height;

	public TutorialLayer(TutorialPage p)
	{
		page = p;
		posX = -1D;
		posY = -1D;
		width = p.width;
		height = p.height;
	}

	public void readProperties(JsonObject o)
	{
		if (o.has("x"))
		{
			posX = o.get("x").getAsDouble();
		}

		if (o.has("y"))
		{
			posY = o.get("y").getAsDouble();
		}

		if (o.has("width"))
		{
			width = o.get("width").getAsDouble();
		}

		if (o.has("height"))
		{
			height = o.get("height").getAsDouble();
		}
	}

	public abstract void draw(double x, double y, double w, double h);
}