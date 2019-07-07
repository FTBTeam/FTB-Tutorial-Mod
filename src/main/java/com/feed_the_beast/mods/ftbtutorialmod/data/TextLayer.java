package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.ftblib.lib.gui.Theme;
import com.feed_the_beast.ftblib.lib.icon.Color4I;
import com.feed_the_beast.ftblib.lib.io.Bits;
import com.feed_the_beast.ftblib.lib.util.JsonUtils;
import com.feed_the_beast.ftblib.lib.util.StringJoiner;
import com.feed_the_beast.mods.ftbtutorialmod.GuiTutorial;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class TextLayer extends TutorialLayer
{
	private String combinedText;
	public double textHeight;
	private int flags;
	public Color4I color;
	public double lineSpacing;

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
				ITextComponent component = JsonUtils.deserializeTextComponent(e);
				text.add(component == null ? "" : component.getFormattedText());
			}

			combinedText = StringJoiner.with('\n').join(text);
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
	public void draw(GuiTutorial gui, double x, double y, double w, double h)
	{
		Theme theme = gui.getTheme();
		double fh = theme.getFontHeight();
		double scale = h / fh * textHeight;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x - (Bits.getFlag(flags, Theme.CENTERED) ? -w / 2D : 0D), y, 0D);
		//GlStateManager.scale(scale, scale, 1D);

		for (String s : theme.listFormattedStringToWidth(combinedText, (int) w))
		{
			theme.drawString(s, 0, 0, color, flags);
			GlStateManager.translate(0D, fh + lineSpacing, 0D);
		}

		GlStateManager.popMatrix();
	}
}