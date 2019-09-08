package com.feed_the_beast.mods.ftbtutorialmod.kubejs;

import dev.latvian.kubejs.player.AttachPlayerDataEvent;
import dev.latvian.kubejs.script.BindingsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author LatvianModder
 */
public class FTBTutorialModKubeJSIngegration
{
	@SubscribeEvent
	public static void bindingsEvent(BindingsEvent event)
	{
		event.add("ftbtutorialmod", new FTBTutorialModWrapper());
	}

	@SubscribeEvent
	public static void attachPlayerData(AttachPlayerDataEvent event)
	{
		event.add("ftbtutorialmod", new FTBTutorialModPlayerData(event.getParent()));
	}
}