package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.mods.ftbtutorialmod.data.Tutorial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author LatvianModder
 */
public class FTBTutorialModClient extends FTBTutorialModCommon
{
	@Override
	public void postInit()
	{
		if (Minecraft.getMinecraft().getResourceManager() instanceof SimpleReloadableResourceManager)
		{
			((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new FTBTutorialModReloadListener());
		}
	}

	@Override
	public void open(ResourceLocation id)
	{
		new GuiTutorial(Tutorial.get(id)).openGui();
	}

	@Override
	public void openList()
	{
		new GuiListTutorials().openGui();
	}
}