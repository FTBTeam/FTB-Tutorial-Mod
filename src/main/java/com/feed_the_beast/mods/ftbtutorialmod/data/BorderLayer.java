package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import net.minecraft.resources.ResourceLocation;

/**
 * @author LatvianModder
 */
public class BorderLayer extends TutorialLayer
{
	public Color4I color;
	public int size;
	public boolean pulse;

	public BorderLayer(TutorialPage p)
	{
		super(p);
		color = Color4I.RED;
		size = 1;
		pulse = false;
	}

	@Override
	public void readProperties(ResourceLocation id, JsonObject o)
	{
		super.readProperties(id, o);

		if (o.has("color"))
		{
			color = Color4I.fromJson(o.get("color"));
		}

		if (o.has("size"))
		{
			size = o.get("size").getAsInt();
		}

		if (o.has("pulse"))
		{
			pulse = o.get("pulse").getAsBoolean();
		}
	}

	@Override
	public void draw(PoseStack pose, GuiTutorial gui, double x, double y, double w, double h)
	{
		int bx = (int) x;
		int by = (int) y;
		int bw = (int) w;
		int bh = (int) h;

		Color4I c = color;

		if (pulse)
		{
			c = c.withAlpha((int) ((Math.sin(System.currentTimeMillis() * 0.003D) + 1D) / 2D * (color.alphai() - 30)) + 30);
		}

		for (int i = 0; i < size; i++)
		{
			GuiHelper.drawHollowRect(pose, bx - i, by - i, bw + i * 2, bh + i * 2, c, false);
		}
	}
}