package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.ftblib.lib.icon.Color4I;
import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftblib.lib.icon.ItemIcon;
import com.feed_the_beast.ftblib.lib.io.DataReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author LatvianModder
 */
public class Tutorial
{
	public static final HashMap<ResourceLocation, Tutorial> tutorials = new HashMap<>();
	public static List<Tutorial> visibleTutorials = null;

	public String title;
	public Icon icon;
	public final List<TutorialPage> pages;
	public ResourceLocation background;
	public Color4I border;

	public Tutorial()
	{
		title = "";
		icon = ItemIcon.getItemIcon(Items.BOOK);
		pages = new ArrayList<>();
		background = null;
		border = Icon.EMPTY;
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

				if (t.has("background"))
				{
					String bg = t.get("background").getAsString();

					if (bg.indexOf(':') == -1)
					{
						tutorial.background = new ResourceLocation(id.getNamespace(), "tutorials/" + id.getPath() + "/" + bg);
					}
					else
					{
						tutorial.background = new ResourceLocation(bg);
					}
				}

				if (t.has("border"))
				{
					tutorial.border = Color4I.fromJson(t.get("border"));
				}

				for (JsonElement e : t.get("pages").getAsJsonArray())
				{
					JsonObject p = e.getAsJsonObject();
					TutorialPage page = new TutorialPage(tutorial);

					if (p.has("description"))
					{
						page.description = p.get("description").getAsString();
					}

					if (p.has("width"))
					{
						page.width = p.get("width").getAsDouble();
					}

					if (p.has("height"))
					{
						page.height = p.get("height").getAsDouble();
					}

					if (p.has("background"))
					{
						String bg = p.get("background").getAsString();

						if (bg.indexOf(':') == -1)
						{
							page.background = new ResourceLocation(id.getNamespace(), "tutorials/" + id.getPath() + "/" + bg);
						}
						else
						{
							page.background = new ResourceLocation(bg);
						}
					}

					if (p.has("border"))
					{
						page.border = Color4I.fromJson(p.get("border"));
					}

					for (JsonElement lo : p.get("layers").getAsJsonArray())
					{
						JsonObject o;

						if (lo.isJsonObject())
						{
							o = lo.getAsJsonObject();
						}
						else
						{
							o = new JsonObject();
							o.add("layer", lo);
						}

						String layer = o.has("layer") ? o.get("layer").getAsString() : "";

						TutorialLayer l = null;

						if (!layer.isEmpty())
						{
							if (layer.equals("border"))
							{
								l = new BorderLayer(page);
							}
							else if (layer.indexOf(':') == -1 && !layer.startsWith("#"))
							{
								l = new IconLayer(page, Icon.getIcon(new ResourceLocation(id.getNamespace(), "tutorials/" + id.getPath() + "/" + layer).toString()));
							}
							else
							{
								l = new IconLayer(page, Icon.getIcon(layer));
							}
						}

						if (l != null)
						{
							l.readProperties(o);
							page.layers.add(l);
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