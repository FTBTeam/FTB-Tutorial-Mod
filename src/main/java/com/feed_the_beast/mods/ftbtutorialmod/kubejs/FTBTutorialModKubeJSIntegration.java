package com.feed_the_beast.mods.ftbtutorialmod.kubejs;

import dev.latvian.kubejs.documentation.DocumentationEvent;
import dev.latvian.kubejs.player.AttachPlayerDataEvent;
import dev.latvian.kubejs.script.DataType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author LatvianModder
 */
public class FTBTutorialModKubeJSIntegration
{
	@SubscribeEvent
	public static void documentationEvent(DocumentationEvent event)
	{
		event.registerAttachedData(DataType.PLAYER, "ftbtutorialmod", FTBTutorialModPlayerData.class);
	}

	@SubscribeEvent
	public static void attachPlayerData(AttachPlayerDataEvent event)
	{
		event.add("ftbtutorialmod", new FTBTutorialModPlayerData(event.getParent()));
	}
}