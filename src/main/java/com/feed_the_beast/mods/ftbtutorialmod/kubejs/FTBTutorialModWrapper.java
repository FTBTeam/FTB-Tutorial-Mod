package com.feed_the_beast.mods.ftbtutorialmod.kubejs;

import com.feed_the_beast.mods.ftbtutorialmod.data.Overlay;
import dev.latvian.kubejs.text.Text;

/**
 * @author LatvianModder
 */
public class FTBTutorialModWrapper
{
	public Overlay overlay(String id, Object[] text)
	{
		Overlay o = new Overlay(id);

		for (Object o1 : text)
		{
			o.text.add(Text.of(o1).component());
		}

		return o;
	}
}