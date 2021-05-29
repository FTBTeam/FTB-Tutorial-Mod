package com.feed_the_beast.mods.ftbtutorialmod.data;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialModClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.io.InputStream;
import java.io.InputStreamReader;
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
	public final List<TutorialPage> pages;
	public String title;
	public Icon icon;
	public ResourceLocation background;
	public Color4I border;
	public String postAction;

	public Tutorial()
	{
		title = "";
		icon = ItemIcon.getItemIcon(Items.BOOK);
		pages = new ArrayList<>();
		background = null;
		border = Icon.EMPTY;
		postAction = "";
	}

	public static Tutorial get(ResourceLocation id)
	{
		Tutorial tutorial = tutorials.get(id);

		if (tutorial == null)
		{
			tutorial = new Tutorial();

			try
			{
				JsonParser parser = new JsonParser();
				InputStream inputStream = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(id.getNamespace(), "tutorials/" + id.getPath() + "/index.json")).getInputStream();

				JsonObject t = parser.parse(new InputStreamReader(inputStream)).getAsJsonObject();
//				JsonObject t = DataReader.get(Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(id.getNamespace(), "tutorials/" + id.getPath() + "/index.json"))).json().getAsJsonObject();

				if (t.has("title"))
				{
					tutorial.title = FTBTutorialModClient.parse(t.get("title").getAsString()).getString();
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
						page.description = FTBTutorialModClient.parse(p.get("description").getAsString()).getString();
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
						if (!lo.isJsonObject())
						{
							continue;
						}

						JsonObject o = lo.getAsJsonObject();

						String type = o.has("type") ? o.get("type").getAsString() : "";

						if (type.isEmpty())
						{
							continue;
						}

						TutorialLayer l;

						switch (type)
						{
							case "image":
								l = new ImageLayer(page);
								break;
							case "border":
								l = new BorderLayer(page);
								break;
							case "text":
								l = new TextLayer(page);
								break;
							case "hover_text":
								l = new HoverTextLayer(page);
								break;
							case "button":
								l = new ButtonLayer(page);
								break;
							default:
								continue;
						}

						l.readProperties(id, o);
						page.layers.add(l);
					}

					tutorial.pages.add(page);
				}

				if (t.has("post_action"))
				{
					tutorial.postAction = t.get("post_action").getAsString();
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