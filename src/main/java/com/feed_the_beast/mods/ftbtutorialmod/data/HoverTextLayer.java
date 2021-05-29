package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialModClient;
import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class HoverTextLayer extends TutorialLayer
{
	public final List<String> text;

	public HoverTextLayer(TutorialPage p)
	{
		super(p);
		text = new ArrayList<>();
	}

	@Override
	public void readProperties(ResourceLocation id, JsonObject o)
	{
		super.readProperties(id, o);

		if (o.has("text"))
		{
			for (JsonElement e : o.get("text").getAsJsonArray())
			{
				text.add(FTBTutorialModClient.parse(e.getAsString()).getString());
			}
		}
	}

	@Override
	public void draw(PoseStack pose, GuiTutorial gui, double x, double y, double w, double h)
	{
		if (gui.getMouseX() >= x && gui.getMouseY() >= y && gui.getMouseX() < x + w && gui.getMouseY() < y + h)
		{
			Color4I.WHITE.withAlpha(100).draw(pose, (int) x, (int) y, (int) w, (int) h);
		}
	}
}