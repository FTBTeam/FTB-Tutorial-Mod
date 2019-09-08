package com.feed_the_beast.mods.ftbtutorialmod.net;

import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftblib.lib.net.MessageToClient;
import com.feed_the_beast.ftblib.lib.net.NetworkWrapper;
import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;
import com.feed_the_beast.mods.ftbtutorialmod.data.Overlay;

/**
 * @author LatvianModder
 */
public class MessageOpenOverlay extends MessageToClient
{
	private Overlay overlay;

	public MessageOpenOverlay()
	{
	}

	public MessageOpenOverlay(Overlay o)
	{
		overlay = o;
	}

	@Override
	public NetworkWrapper getWrapper()
	{
		return FTBTutorialModNetHandler.NET;
	}

	@Override
	public void writeData(DataOut data)
	{
		data.writeString(overlay.id);
		overlay.writeData(data);
	}

	@Override
	public void readData(DataIn data)
	{
		overlay = new Overlay(data.readString());
		overlay.readData(data);
	}

	@Override
	public void onMessage()
	{
		FTBTutorialMod.PROXY.openOverlay(overlay);
	}
}