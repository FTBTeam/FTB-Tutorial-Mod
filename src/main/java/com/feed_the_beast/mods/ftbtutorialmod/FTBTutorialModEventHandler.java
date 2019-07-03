package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.events.client.CustomClickEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = FTBTutorialMod.MOD_ID, value = Side.CLIENT)
public class FTBTutorialModEventHandler
{
	@SubscribeEvent
	public static void onCustomClick(CustomClickEvent event)
	{
		if (event.getID().getNamespace().equals(FTBTutorialMod.MOD_ID))
		{
			if (event.getID().getPath().equals("list"))
			{
				new GuiListTutorials().openGui();
			}
			else if (event.getID().getPath().startsWith("open:"))
			{
				new GuiTutorial(FTBTutorialMod.get(new ResourceLocation(event.getID().getPath().substring(5)))).openGui();
			}

			event.setCanceled(true);
		}
	}
}