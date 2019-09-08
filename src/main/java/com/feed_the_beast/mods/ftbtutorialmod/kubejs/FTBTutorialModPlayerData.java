package com.feed_the_beast.mods.ftbtutorialmod.kubejs;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;
import com.feed_the_beast.mods.ftbtutorialmod.data.Overlay;
import com.feed_the_beast.mods.ftbtutorialmod.net.MessageOpenTutorial;
import dev.latvian.kubejs.player.PlayerDataJS;
import dev.latvian.kubejs.util.ID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author LatvianModder
 */
public class FTBTutorialModPlayerData
{
	private final PlayerDataJS playerData;

	public FTBTutorialModPlayerData(PlayerDataJS p)
	{
		playerData = p;
	}

	public void openOverlay(Overlay o)
	{
		o.open(playerData.getPlayerEntity());
	}

	public void closeOverlay(Overlay o)
	{
		o.close(playerData.getPlayerEntity());
	}

	public void closeOverlay(String id)
	{
		closeOverlay(new Overlay(id));
	}

	public void closeAllOverlays()
	{
		closeOverlay("*");
	}

	public void openTutorial(Object id)
	{
		EntityPlayer player = playerData.getPlayerEntity();

		if (player instanceof EntityPlayerMP)
		{
			new MessageOpenTutorial(ID.of(id).mc()).sendTo((EntityPlayerMP) player);
		}
		else
		{
			FTBTutorialMod.PROXY.open(ID.of(id).mc());
		}
	}
}