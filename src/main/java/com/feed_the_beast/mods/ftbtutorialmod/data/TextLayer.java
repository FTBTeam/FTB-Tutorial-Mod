package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialModClient;
import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.math.Bits;
import dev.ftb.mods.ftblibrary.ui.Theme;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class TextLayer extends TutorialLayer
{
	public double textHeight;
	public Color4I color;
	public double lineSpacing;
	private String combinedText;
	private int flags;

	public TextLayer(TutorialPage p)
	{
		super(p);
		height = 0;
		combinedText = "";
		textHeight = 9D;
		flags = 0;
		color = Color4I.WHITE;
		lineSpacing = 1D;
	}

	@Override
	public void readProperties(ResourceLocation id, JsonObject o)
	{
		super.readProperties(id, o);

		combinedText = "";

		if (o.has("text"))
		{
			List<String> text = new ArrayList<>();

			for (JsonElement e : o.get("text").getAsJsonArray())
			{
				text.add(FTBTutorialModClient.parse(e.getAsString()).getString());
			}

			combinedText = String.join("\n", text);
		}

		if (o.has("text_height"))
		{
			textHeight = o.get("text_height").getAsDouble();
		}

		flags = 0;

		if (o.has("unicode") && o.get("unicode").getAsBoolean())
		{
			flags |= Theme.UNICODE;
		}

		if (!o.has("shadow") || o.get("shadow").getAsBoolean())
		{
			flags |= Theme.SHADOW;
		}

		if (o.has("centered") && o.get("centered").getAsBoolean())
		{
			flags |= Theme.CENTERED;
		}

		if (o.has("color"))
		{
			color = Color4I.fromJson(o.get("color"));
		}

		if (o.has("line_spacing"))
		{
			lineSpacing = o.get("line_spacing").getAsDouble();
		}
	}

	@Override
	public void draw(PoseStack matrixStack, GuiTutorial gui, double x, double y, double w, double h)
	{
		Theme theme = gui.getTheme();
		double fh = theme.getFontHeight();

		matrixStack.pushPose();
		matrixStack.translate(x - (Bits.getFlag(flags, Theme.CENTERED) ? -w / 2D : 0D), y, 0D);

		for (FormattedText s : theme.listFormattedStringToWidth(new TextComponent(combinedText), (int) w))
		{
			theme.drawString(matrixStack, s, 0, 0, color, flags);
			matrixStack.translate(0D, fh + lineSpacing, 0D);
		}

		matrixStack.popPose();
	}
}