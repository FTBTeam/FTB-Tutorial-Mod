package com.feed_the_beast.mods.ftbtutorialmod.net;

import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftblib.lib.net.MessageToClient;
import com.feed_the_beast.ftblib.lib.net.NetworkWrapper;
import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;

/**
 * @author LatvianModder
 */
public class MessageCloseOverlay extends MessageToClient
{
	private String overlay;

	public MessageCloseOverlay()
	{
	}

	public MessageCloseOverlay(String id)
	{
		overlay = id;
	}

	@Override
	public NetworkWrapper getWrapper()
	{
		return FTBTutorialModNetHandler.NET;
	}

	@Override
	public void writeData(DataOut data)
	{
		data.writeString(overlay);
	}

	@Override
	public void readData(DataIn data)
	{
		overlay = data.readString();
	}

	@Override
	public void onMessage()
	{
		FTBTutorialMod.PROXY.closeOverlay(overlay);
	}
}