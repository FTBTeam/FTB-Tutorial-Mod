package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.ftblib.lib.icon.Color4I;
import com.feed_the_beast.ftblib.lib.util.JsonUtils;
import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

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
				ITextComponent component = JsonUtils.deserializeTextComponent(e);
				text.add(component == null ? "" : component.getFormattedText());
			}
		}
	}

	@Override
	public void draw(GuiTutorial gui, double x, double y, double w, double h)
	{
		if (gui.getMouseX() >= x && gui.getMouseY() >= y && gui.getMouseX() < x + w && gui.getMouseY() < y + h)
		{
			Color4I.WHITE.withAlpha(100).draw((int) x, (int) y, (int) w, (int) h);
		}
	}
}