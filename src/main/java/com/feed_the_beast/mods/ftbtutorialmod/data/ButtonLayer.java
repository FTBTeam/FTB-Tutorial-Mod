package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialModClient;
import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class ButtonLayer extends TutorialLayer
{
	public final List<String> hover;
	public Color4I color;
	public String click;

	public ButtonLayer(TutorialPage p)
	{
		super(p);
		color = Color4I.RED;
		click = "";
		hover = new ArrayList<>();
	}

	@Override
	public void readProperties(ResourceLocation id, JsonObject o)
	{
		super.readProperties(id, o);

		if (o.has("color"))
		{
			color = Color4I.fromJson(o.get("color"));
		}

		if (o.has("click"))
		{
			click = o.get("click").getAsString();
		}

		if (o.has("hover"))
		{
			for (JsonElement e : o.get("hover").getAsJsonArray())
			{
				hover.add(FTBTutorialModClient.parse(e.getAsString()).getString());
			}
		}
	}

	@Override
	public void draw(PoseStack pose, GuiTutorial gui, double x, double y, double w, double h)
	{
		int bx = (int) x;
		int by = (int) y;
		int bw = (int) w;
		int bh = (int) h;

		Color4I c = color.withAlpha((int) ((Math.sin(System.currentTimeMillis() * 0.003D) + 1D) / 2D * (color.alphai() - 100)) + 100);
		GuiHelper.drawHollowRect(pose, bx, by, bw, bh, c, false);

		if (gui.isMouseOver(bx, by, bw, bh))
		{
			Color4I.WHITE.withAlpha(50).draw(pose, bx + 1, by + 1, bw - 2, bh - 2);
		}
	}
}