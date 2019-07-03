package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftblib.lib.icon.ItemIcon;
import net.minecraft.init.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class Tutorial
{
	public String title = "";
	public Icon icon = ItemIcon.getItemIcon(Items.BOOK);
	public final List<TutorialPage> pages;

	public Tutorial()
	{
		pages = new ArrayList<>();
	}
}