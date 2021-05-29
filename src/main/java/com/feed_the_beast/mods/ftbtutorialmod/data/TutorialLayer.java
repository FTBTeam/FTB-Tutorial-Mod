package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

/**
 * @author LatvianModder
 */
public abstract class TutorialLayer
{
	public final TutorialPage page;
	public final int index;
	public double posX, posY;
	public double width, height;

	public TutorialLayer(TutorialPage p)
	{
		page = p;
		index = page.layers.size();
		posX = -1D;
		posY = -1D;
		width = p.width;
		height = p.height;
	}

	public void readProperties(ResourceLocation id, JsonObject o)
	{
		if (o.has("pos"))
		{
			JsonArray a = o.get("pos").getAsJsonArray();

			if (a.size() == 4)
			{
				posX = a.get(0).getAsDouble();
				posY = a.get(1).getAsDouble();
				width = a.get(2).getAsDouble();
				height = a.get(3).getAsDouble();
			}
		}

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

	public abstract void draw(PoseStack matrixStack, GuiTutorial gui, double x, double y, double w, double h);
}