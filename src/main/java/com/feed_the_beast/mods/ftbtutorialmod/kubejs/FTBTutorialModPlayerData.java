package com.feed_the_beast.mods.ftbtutorialmod.kubejs;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;
import com.feed_the_beast.mods.ftbtutorialmod.data.Overlay;
import com.feed_the_beast.mods.ftbtutorialmod.net.MessageCloseOverlay;
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
		if (o != null)
		{
			o.open(playerData.getPlayerEntity());
		}
		else
		{
			FTBTutorialMod.LOGGER.error("Can't open null overlay!");
		}
	}

	public void closeOverlay(Overlay o)
	{
		if (o != null)
		{
			o.close(playerData.getPlayerEntity());
		}
		else
		{
			FTBTutorialMod.LOGGER.error("Can't close null overlay!");
		}
	}

	public void closeOverlay(String id)
	{
		EntityPlayer player = playerData.getPlayerEntity();

		if (player instanceof EntityPlayerMP)
		{
			new MessageCloseOverlay(id).sendTo((EntityPlayerMP) player);
		}
		else
		{
			FTBTutorialMod.PROXY.closeOverlay(id);
		}
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