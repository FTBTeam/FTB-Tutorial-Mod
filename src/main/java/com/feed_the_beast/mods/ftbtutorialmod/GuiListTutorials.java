package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.mods.ftbtutorialmod.data.Tutorial;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.ButtonListBaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author LatvianModder
 */
public class GuiListTutorials extends ButtonListBaseScreen
{
	public GuiListTutorials()
	{
		setTitle(new TranslatableComponent("sidebar_button.ftbtutorialmod.tutorials"));
		setHasSearchBox(true);
	}

	@Override
	public void addButtons(Panel panel)
	{
		if (Tutorial.visibleTutorials == null)
		{
			Tutorial.visibleTutorials = new ArrayList<>();
			ResourceManager manager = Minecraft.getInstance().getResourceManager();

			for (String namespace : manager.getNamespaces())
			{
				try
				{
					JsonParser parser = new JsonParser();
					InputStream inputStream = manager.getResource(new ResourceLocation(namespace, "tutorials/visible.json")).getInputStream();

					for (JsonElement element : parser.parse(new InputStreamReader(inputStream)).getAsJsonArray())
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
			panel.add(new SimpleTextButton(panel, new TextComponent(tutorial.title), tutorial.icon)
			{
				@Override
				public void onClicked(MouseButton button)
				{
					GuiHelper.playSound(SoundEvents.UI_BUTTON_CLICK, 1);
					new GuiTutorial(tutorial).openGui();
				}
			});
		}
	}
}