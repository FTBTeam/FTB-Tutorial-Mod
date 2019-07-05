package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.lib.gui.GuiHelper;
import com.feed_the_beast.ftblib.lib.gui.Panel;
import com.feed_the_beast.ftblib.lib.gui.SimpleTextButton;
import com.feed_the_beast.ftblib.lib.gui.misc.GuiButtonListBase;
import com.feed_the_beast.ftblib.lib.io.DataReader;
import com.feed_the_beast.ftblib.lib.util.misc.MouseButton;
import com.feed_the_beast.mods.ftbtutorialmod.data.Tutorial;
import com.google.gson.JsonElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * @author LatvianModder
 */
public class GuiListTutorials extends GuiButtonListBase
{
	public GuiListTutorials()
	{
		setTitle(I18n.format("sidebar_button.ftbtutorialmod.tutorials"));
		setHasSearchBox(true);
	}

	@Override
	public void addButtons(Panel panel)
	{
		if (Tutorial.visibleTutorials == null)
		{
			Tutorial.visibleTutorials = new ArrayList<>();
			IResourceManager manager = Minecraft.getMinecraft().getResourceManager();

			for (String namespace : manager.getResourceDomains())
			{
				try
				{
					for (JsonElement element : DataReader.get(manager.getResource(new ResourceLocation(namespace, "tutorials/visible.json"))).json().getAsJsonArray())
					{
						Tutorial.visibleTutorials.add(Tutorial.get(new ResourceLocation(namespace, element.getAsString())));
					}
				}
				catch (Exception ex)
				{
				}
			}
		}

		for (Tutorial tutorial : Tutorial.visibleTutorials)
		{
			panel.add(new SimpleTextButton(panel, tutorial.title, tutorial.icon)
			{
				@Override
				public void onClicked(MouseButton button)
				{
					GuiHelper.playClickSound();
					new GuiTutorial(tutorial).openGui();
				}
			});
		}
	}
}