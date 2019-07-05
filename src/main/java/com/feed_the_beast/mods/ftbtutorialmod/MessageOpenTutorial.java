package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftblib.lib.net.MessageToClient;
import com.feed_the_beast.ftblib.lib.net.NetworkWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author LatvianModder
 */
public class MessageOpenTutorial extends MessageToClient
{
	public static NetworkWrapper NET;

	public static void init()
	{
		NET = NetworkWrapper.newWrapper(FTBTutorialMod.MOD_ID);
		NET.register(new MessageOpenTutorial());
	}

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
		return NET;
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
	@SideOnly(Side.CLIENT)
	public void onMessage()
	{
		FTBTutorialMod.openOnClient(tutorial);
	}
}