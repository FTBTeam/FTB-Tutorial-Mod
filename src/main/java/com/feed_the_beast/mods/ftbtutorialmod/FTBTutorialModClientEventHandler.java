package com.feed_the_beast.mods.ftbtutorialmod;

import dev.ftb.mods.ftblibrary.ui.CustomClickEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;

/**
 * @author LatvianModder
 */
public class FTBTutorialModClientEventHandler
{
	public static InteractionResult onCustomClick(CustomClickEvent event)
	{
		if (event.getId().getNamespace().equals(FTBTutorialMod.MOD_ID))
		{
			if (event.getId().getPath().equals("list"))
			{
				FTBTutorialModClient.openList();
			}
			else if (event.getId().getPath().startsWith("open:"))
			{
				FTBTutorialModClient.open(new ResourceLocation(event.getId().getPath().substring(5)));
			}

			return InteractionResult.SUCCESS;
//			event.setCanceled(true);
		}

		return InteractionResult.PASS;
	}
}