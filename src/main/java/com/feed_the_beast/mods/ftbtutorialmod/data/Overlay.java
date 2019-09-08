package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.ftblib.lib.icon.Color4I;
import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;
import com.feed_the_beast.mods.ftbtutorialmod.net.MessageCloseOverlay;
import com.feed_the_beast.mods.ftbtutorialmod.net.MessageOpenOverlay;
import com.google.gson.JsonPrimitive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class Overlay
{
	public final String id;
	public final List<ITextComponent> text;
	public Color4I color;

	public Overlay(String i)
	{
		id = i;
		text = new ArrayList<>();
		color = Color4I.rgb(16, 16, 16);
	}

	public void writeData(DataOut data)
	{
		data.writeCollection(text, DataOut.TEXT_COMPONENT);

		if (text.isEmpty())
		{
			return;
		}

		data.writeInt(color.rgba());
	}

	public void readData(DataIn data)
	{
		data.readCollection(text, DataIn.TEXT_COMPONENT);

		if (text.isEmpty())
		{
			return;
		}

		color = Color4I.rgba(data.readInt());
	}

	public Overlay color(String col)
	{
		color = Color4I.fromJson(new JsonPrimitive(col));
		return this;
	}

	public void open(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			new MessageOpenOverlay(this).sendTo((EntityPlayerMP) player);
		}
		else
		{
			FTBTutorialMod.PROXY.openOverlay(this);
		}
	}

	public void close(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			new MessageCloseOverlay(id).sendTo((EntityPlayerMP) player);
		}
		else
		{
			FTBTutorialMod.PROXY.closeOverlay(id);
		}
	}
}