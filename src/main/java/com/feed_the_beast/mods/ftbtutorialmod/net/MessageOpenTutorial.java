package com.feed_the_beast.mods.ftbtutorialmod.net;

import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftblib.lib.net.MessageToClient;
import com.feed_the_beast.ftblib.lib.net.NetworkWrapper;
import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;
import net.minecraft.util.ResourceLocation;

/**
 * @author LatvianModder
 */
public class MessageOpenTutorial extends MessageToClient
{
	private ResourceLocation tutorial;

	public MessageOpenTutorial()
	{
	}

	public MessageOpenTutorial(ResourceLocation id)
	{
		tutorial = id;
	}

	@Override
	public NetworkWrapper getWrapper()
	{
		return FTBTutorialModNetHandler.NET;
	}

	@Override
	public void writeData(DataOut data)
	{
		data.writeResourceLocation(tutorial);
	}

	@Override
	public void readData(DataIn data)
	{
		tutorial = data.readResourceLocation();
	}

	@Override
	public void onMessage()
	{
		FTBTutorialMod.INSTANCE.openOnClient(tutorial);
	}
}