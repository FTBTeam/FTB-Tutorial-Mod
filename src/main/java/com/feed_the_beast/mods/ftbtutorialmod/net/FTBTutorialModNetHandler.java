package com.feed_the_beast.mods.ftbtutorialmod.net;

import com.feed_the_beast.ftblib.lib.net.NetworkWrapper;
import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;

/**
 * @author LatvianModder
 */
public class FTBTutorialModNetHandler
{
	public static NetworkWrapper NET;

	public static void init()
	{
		NET = NetworkWrapper.newWrapper(FTBTutorialMod.MOD_ID);
		NET.register(new MessageOpenTutorial());
	}
}