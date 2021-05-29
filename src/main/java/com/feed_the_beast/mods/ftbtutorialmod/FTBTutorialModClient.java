package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.mods.ftbtutorialmod.data.Tutorial;
import dev.ftb.mods.ftblibrary.ui.CustomClickEvent;
import dev.ftb.mods.ftblibrary.util.TextComponentParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;

import java.util.function.Function;

/**
 * @author LatvianModder
 */
public class FTBTutorialModClient
{
	public static final Function<String, Component> DEFAULT_STRING_TO_COMPONENT = FTBTutorialModClient::stringToComponent;

	public static void setup()
	{
		CustomClickEvent.EVENT.register(FTBTutorialModClientEventHandler::onCustomClick);
		if (Minecraft.getInstance().getResourceManager() instanceof SimpleReloadableResourceManager)
		{
			((SimpleReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(new FTBTutorialModReloadListener());
		}
	}

	// From Quests
	public static Component parse(String s) {
		Component c = TextComponentParser.parse(s, DEFAULT_STRING_TO_COMPONENT);

		if (c == TextComponent.EMPTY) {
			return c;
		}

		while (c.getContents().isEmpty() && c.getStyle().equals(Style.EMPTY) && c.getSiblings().size() == 1) {
			c = c.getSiblings().get(0);
		}

		return c;
	}

	private static Component stringToComponent(String s) {
		if (s.isEmpty()) {
			return TextComponent.EMPTY;
		}

		return parse(I18n.get(s));
	}

	public static void openList()
	{
		new GuiListTutorials().openGui();
	}

	public static void open(ResourceLocation id)
	{
		new GuiTutorial(Tutorial.get(id)).openGui();
	}
}