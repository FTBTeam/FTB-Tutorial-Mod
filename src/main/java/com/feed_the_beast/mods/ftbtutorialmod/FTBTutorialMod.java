package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.FTBLib;
import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftblib.lib.io.DataReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

@Mod(
		modid = FTBTutorialMod.MOD_ID,
		name = FTBTutorialMod.MOD_NAME,
		version = FTBTutorialMod.VERSION,
		acceptableRemoteVersions = "*",
		dependencies = FTBLib.THIS_DEP
)
public class FTBTutorialMod
{
	public static final String MOD_ID = "ftbtutorialmod";
	public static final String MOD_NAME = "FTB Tutorial Mod";
	public static final String VERSION = "0.0.0.ftbtutorialmod";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static final HashMap<ResourceLocation, Tutorial> tutorials = new HashMap<>();
	public static List<Tutorial> visibleTutorials = null;

	@Mod.EventHandler
	public void onPostInit(FMLPostInitializationEvent event)
	{
		if (Minecraft.getMinecraft().getResourceManager() instanceof SimpleReloadableResourceManager)
		{
			((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(resourceManager -> {
				tutorials.clear();
				visibleTutorials = null;
			});
		}

		MessageOpenTutorial.init();
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandOpenTutorial());
	}

	public static Tutorial get(ResourceLocation id)
	{
		Tutorial tutorial = tutorials.get(id);

		if (tutorial == null)
		{
			tutorial = new Tutorial();

			try
			{
				JsonObject t = DataReader.get(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(id.getNamespace(), "tutorials/" + id.getPath() + "/index.json"))).json().getAsJsonObject();

				if (t.has("title"))
				{
					tutorial.title = t.get("title").getAsString();
				}

				if (t.has("icon"))
				{
					tutorial.icon = Icon.getIcon(t.get("icon"));
				}

				for (JsonElement e : t.get("pages").getAsJsonArray())
				{
					JsonObject p = e.getAsJsonObject();
					TutorialPage page = new TutorialPage(tutorial);

					if (p.has("description"))
					{
						page.description = p.get("description").getAsString();
					}

					for (JsonElement l : p.get("layers").getAsJsonArray())
					{
						String layer = l.getAsString();

						if (layer.indexOf(':') == -1)
						{
							page.layers.add(new ResourceLocation(id.getNamespace(), "tutorials/" + id.getPath() + "/" + layer));
						}
						else
						{
							page.layers.add(new ResourceLocation(layer));
						}
					}

					tutorial.pages.add(page);
				}

			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				tutorial.pages.clear();
			}

			tutorials.put(id, tutorial);
		}

		return tutorial;
	}
}