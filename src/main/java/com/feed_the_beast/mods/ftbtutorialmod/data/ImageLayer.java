package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

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
	public void draw(GuiTutorial gui, double x, double y, double w, double h)
	{
		image.draw((int) x, (int) y, (int) w, (int) h);
	}
}