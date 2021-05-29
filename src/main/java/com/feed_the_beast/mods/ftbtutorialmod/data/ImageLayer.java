package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.icon.Icon;
import net.minecraft.resources.ResourceLocation;

/**
 * @author LatvianModder
 */
public class ImageLayer extends TutorialLayer
{
	public Icon image;

	public ImageLayer(TutorialPage p)
	{
		super(p);
		image = Icon.EMPTY;
	}

	@Override
	public void readProperties(ResourceLocation id, JsonObject o)
	{
		super.readProperties(id, o);

		if (o.has("image"))
		{
			String layer = o.get("image").getAsString();

			if (layer.indexOf(':') == -1 && !layer.startsWith("#"))
			{
				image = Icon.getIcon(new ResourceLocation(id.getNamespace(), "tutorials/" + id.getPath() + "/" + layer).toString());
			}
			else
			{
				image = Icon.getIcon(layer);
			}
		}
	}

	@Override
	public void draw(PoseStack matrix, GuiTutorial gui, double x, double y, double w, double h)
	{
		image.draw(matrix, (int) x, (int) y, (int) w, (int) h);
	}
}