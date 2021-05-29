package com.feed_the_beast.mods.ftbtutorialmod.kubejs;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;
import dev.latvian.kubejs.player.AttachPlayerDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author LatvianModder
 */
public class FTBTutorialModKubeJSIntegration
{
//	@SubscribeEvent
//	public static void documentationEvent(DocumentationEvent event)
//	{
//		event.registerAttachedData(DataType.PLAYER, FTBTutorialMod.MOD_ID, FTBTutorialModPlayerData.class);
//	}

	@SubscribeEvent
	public static void attachPlayerData(AttachPlayerDataEvent event)
	{
		event.add(FTBTutorialMod.MOD_ID, new FTBTutorialModPlayerData(event.getParent()));
	}
}