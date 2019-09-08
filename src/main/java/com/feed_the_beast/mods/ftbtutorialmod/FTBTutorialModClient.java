package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.mods.ftbtutorialmod.data.Overlay;
import com.feed_the_beast.mods.ftbtutorialmod.data.Tutorial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LatvianModder
 */
public class FTBTutorialModClient extends FTBTutorialModCommon
{
	public static final Map<String, Overlay> activeOverlays = new LinkedHashMap<>();

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

	@Override
	public void openOverlay(Overlay o)
	{
		activeOverlays.put(o.id, o);
	}

	@Override
	public void closeOverlay(String s)
	{
		if (s.equals("*"))
		{
			activeOverlays.clear();
		}
		else
		{
			activeOverlays.remove(s);
		}
	}
}